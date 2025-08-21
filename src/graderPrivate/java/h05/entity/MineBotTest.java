package h05.entity;

import fopbot.Direction;
import fopbot.World;
import h05.Utils;
import h05.base.game.BasicGameSettings;
import h05.base.game.GameSettings;
import h05.equipment.Tool;
import h05.mineable.Mineable;
import kotlin.Triple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Context;

import java.awt.*;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@TestForSubmission
public class MineBotTest {

    private final AtomicReference<Direction> currentDirection = new AtomicReference<>();
    private final Context context = contextBuilder()
        .add("world layout", "world size: 3x3, MineBot at (1, 1), loot at (1, 0) and (1, 2)")
        .add("direction", currentDirection)
        .build();
    private Map<Point, Triple<Mineable, AtomicBoolean, AtomicBoolean>> lootMocks;
    private AtomicReference<Point> getLootAt_argsRef;
    private GameSettings gameSettingsMock;
    private MineBot mineBotMock;

    @BeforeEach
    public void setup() {
        World.setSize(3, 3);
        World.setVisible(false);
        World.setDelay(0);

        lootMocks = Stream.of(new Point(1, 0), new Point(1, 2))
            .map(point -> {
                AtomicBoolean isCalled = new AtomicBoolean();
                AtomicBoolean onMinedReturnValue = new AtomicBoolean();
                return Map.entry(point, new Triple<>(makeLootMock(isCalled, onMinedReturnValue), isCalled, onMinedReturnValue));
            })
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        getLootAt_argsRef = new AtomicReference<>();
        Answer<?> gameSettingsAnswer = invocation -> {
            if (Utils.methodSignatureEquals(invocation.getMethod(), "getLootAt", int.class, int.class)) {
                getLootAt_argsRef.set(new Point(invocation.getArgument(0, Integer.class), invocation.getArgument(1, Integer.class)));
                if (lootMocks.containsKey(getLootAt_argsRef.get())) {
                    return lootMocks.get(getLootAt_argsRef.get()).getFirst();
                } else {
                    return null;
                }
            } else {
                return invocation.callRealMethod();
            }
        };
        gameSettingsMock = Mockito.mock(BasicGameSettings.class, gameSettingsAnswer);


        Answer<?> mineBotAnswer = invocation -> {
            if (Utils.methodSignatureEquals(invocation.getMethod(), "getDirection")) {
                return currentDirection.get();
            } else {
                return invocation.callRealMethod();
            }
        };
        mineBotMock = Mockito.mock(MineBot.class, Mockito.withSettings().useConstructor(1, 1, gameSettingsMock).defaultAnswer(mineBotAnswer));
    }

    @ParameterizedTest
    @EnumSource(Direction.class)
    public void testMine_callsGetLootAt(Direction direction) {
        currentDirection.set(direction);
        call(mineBotMock::mine, context, r -> "An exception occurred while invoking MineBot.mine()");
        Point args = getLootAt_argsRef.get();

        assertNotNull(args, context, r ->
            "MineBot.mine() did not call GameSettings.getLootAt(int, int)");
        assertEquals(1 + direction.getDx(), args.x, context, r ->
            "MineBot.mine() did not call GameSettings.getLootAt(int, int) with the correct arguments (wrong x coordinate)");
        assertEquals(1 + direction.getDy(), args.y, context, r ->
            "MineBot.mine() did not call GameSettings.getLootAt(int, int) with the correct arguments (wrong y coordinate)");
    }

    @ParameterizedTest
    @EnumSource(Direction.class)
    public void testMine_returnsOnNoLoot(Direction direction) {
        if (direction.isVertical()) {
            return;
        }
        currentDirection.set(direction);
        call(mineBotMock::mine, context, r -> "An exception occurred while invoking MineBot.mine()");

        lootMocks.values().forEach(triple -> {
            assertFalse(triple.getSecond().get(), context,
                r -> "There is no loot at (%d, %d), MineBot should have returned"
                    .formatted(1 + direction.getDx(), 1 + direction.getDy()));
        });
        assertEquals(0, mineBotMock.getInventory().size(), context,
            r -> "There is no loot at (%d, %d), MineBot should have returned and not modify the inventory"
                .formatted(1 + direction.getDx(), 1 + direction.getDy()));
    }

    @ParameterizedTest
    @EnumSource(Direction.class)
    public void testMine_callsOnMined(Direction direction) {
        if (direction.isHorizontal()) {
            return;
        }
        currentDirection.set(direction);
        call(mineBotMock::mine, context, r -> "An exception occurred while invoking MineBot.mine()");

        AtomicBoolean onMinedCalled = lootMocks.get(new Point(1 + direction.getDx(), 1 + direction.getDy())).getSecond();
        assertTrue(onMinedCalled.get(), context,
            r -> "There is loot at (%d, %d) but MineBot did not call Mineable.onMined(Tool) on it"
                .formatted(1 + direction.getDx(), 1 + direction.getDy()));
    }

    @ParameterizedTest
    @EnumSource(Direction.class)
    public void testMine_placesInInventory(Direction direction) {
        if (direction.isHorizontal()) {
            return;
        }
        currentDirection.set(direction);
        lootMocks.get(new Point(1 + direction.getDx(), 1 + direction.getDy())).getThird().set(true);
        call(mineBotMock::mine, context, r -> "An exception occurred while invoking MineBot.mine()");

        assertEquals(1, mineBotMock.getInventory().size(), context,
            r -> "There is loot at (%d, %d) but MineBot did not place it in its inventory"
                .formatted(1 + direction.getDx(), 1 + direction.getDy()));
        assertTrue(Arrays.stream(mineBotMock.getInventory().getNames()).filter(Objects::nonNull).findFirst().map("Loot"::equals).orElse(false),
            context,
            r -> "There is loot at (%d, %d) but MineBot did not place it in its inventory"
                .formatted(1 + direction.getDx(), 1 + direction.getDy()));
    }

    private Mineable makeLootMock(AtomicBoolean isCalled, AtomicBoolean onMinedReturnValue) {
        Answer<?> answer = invocation -> {
            Method invokedMethod = invocation.getMethod();
            if (Utils.methodSignatureEquals(invokedMethod, "onMined", Tool.class)) {
                isCalled.set(true);
                return onMinedReturnValue.get();
            } else if (Utils.methodSignatureEquals(invokedMethod, "getName")) {
                return "Loot";
            } else {
                return Mockito.RETURNS_DEFAULTS.answer(invocation);
            }
        };
        return Mockito.mock(Mineable.class, answer);
    }
}
