package h05.entity;

import fopbot.Direction;
import fopbot.World;
import h05.Utils;
import h05.base.entity.Fog;
import h05.base.game.BasicGameSettings;
import h05.base.game.GameSettings;
import h05.equipment.Camera;
import h05.equipment.Tool;
import h05.mineable.Mineable;
import kotlin.Triple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Context;

import java.awt.*;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@TestForSubmission
public class MineBotTest {

    private static final int WORLD_SIZE = 5;
    private static final Point STARTING_POS = new  Point(1, 1);
    private final AtomicReference<Direction> currentDirection = new AtomicReference<>();
    private final Context context = contextBuilder()
        .add("world layout", "world size: %dx%d, MineBot at (%d, %d), loot at (1, 0) and (1, 2)"
            .formatted(WORLD_SIZE, WORLD_SIZE, STARTING_POS.x, STARTING_POS.y))
        .add("direction", currentDirection)
        .build();
    private Map<Point, Triple<Mineable, AtomicBoolean, AtomicBoolean>> lootMocks;
    private AtomicReference<Point> getLootAt_argsRef;
    private GameSettings gameSettingsMock;
    private AtomicBoolean mockGetVision;
    private final AtomicInteger visibilityRange = new AtomicInteger();
    private MineBot mineBotMock;

    @BeforeEach
    public void setup() {
        World.setSize(WORLD_SIZE, WORLD_SIZE);
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
            Method invokedMethod = invocation.getMethod();
            if (Utils.methodSignatureEquals(invokedMethod, "getLootAt", int.class, int.class)) {
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


        mockGetVision = new AtomicBoolean();
        Answer<?> mineBotAnswer = invocation -> {
            Method invokedMethod = invocation.getMethod();
            if (Utils.methodSignatureEquals(invokedMethod, "getDirection")) {
                return currentDirection.get() == null ? Direction.UP : currentDirection.get();
            } else if (Utils.methodSignatureEquals(invokedMethod, "getVision", int.class, int.class)) {
                if (mockGetVision.get()) {
                    return getValidVisionPoints(invocation.getArgument(0, Integer.class), invocation.getArgument(0, Integer.class), visibilityRange.get());
                } else {
                    return invocation.callRealMethod();
                }
            } else {
                return invocation.callRealMethod();
            }
        };
        mineBotMock = Mockito.mock(MineBot.class, Mockito.withSettings()
            .useConstructor(STARTING_POS.x, STARTING_POS.y, gameSettingsMock)
            .defaultAnswer(mineBotAnswer));
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

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5})
    public void testGetVision_correctNumberOfPoints(int visibilityRange) {
        testGetVision(visibilityRange, false);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5})
    public void testGetVision_correctPoints(int visibilityRange) {
        testGetVision(visibilityRange, true);
    }

    private void testGetVision(int visibilityRange, boolean assertPoints) {
        Camera cameraMock = makeCameraMock(visibilityRange);
        mineBotMock.equip(cameraMock);
        Point[] points = callObject(() -> mineBotMock.getVision(STARTING_POS.x, STARTING_POS.y), context,
            r -> "An exception occurred while invoking MineBot.getVision(int, int)");
        Point[] validPoints = getValidVisionPoints(STARTING_POS.x, STARTING_POS.y, visibilityRange);

        assertEquals(validPoints.length, points.length, context,
            r -> "The returned array does not have the correct number of points");
        if (assertPoints) {
            Set<Point> expectedPoints = Set.of(validPoints);
            Set<Point> actualPoints = Set.of(points);
            assertTrue(actualPoints.containsAll(expectedPoints), context,
                r -> "The returned array does not contain all valid points. Missing: " +
                    expectedPoints.stream()
                        .filter(Predicate.not(actualPoints::contains))
                        .map(point -> "(%d, %d)".formatted(point.x, point.y))
                        .collect(Collectors.joining(", ")));
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5})
    public void testUpdateVision_placeFog(int visibilityRange) {
        Set<Point> visiblePointsStart = Set.of(getValidVisionPoints(STARTING_POS.x, STARTING_POS.y, visibilityRange));
        Set<Point> visiblePointsEnd = Set.of(getValidVisionPoints(WORLD_SIZE - 1, WORLD_SIZE - 1, visibilityRange));

        testUpdateVision(visibilityRange, visiblePointsStart, visiblePointsEnd, true);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5})
    public void testUpdateVision_removeFog(int visibilityRange) {
        Set<Point> visiblePointsStart = Set.of(getValidVisionPoints(STARTING_POS.x, STARTING_POS.y, visibilityRange));
        Set<Point> visiblePointsEnd = Set.of(getValidVisionPoints(WORLD_SIZE - 1, WORLD_SIZE - 1, visibilityRange));

        testUpdateVision(visibilityRange, visiblePointsStart, visiblePointsEnd, false);
    }

    private void testUpdateVision(int visibilityRange, Set<Point> visiblePointsStart, Set<Point> visiblePointsEnd, boolean checkPlacement) {
        for (int x = 0; x < WORLD_SIZE; x++) {
            for (int y = 0; y < WORLD_SIZE; y++) {
                Point point = new Point(x, y);
                if (!visiblePointsStart.contains(point)) {
                    gameSettingsMock.placeFog(x, y);
                }
            }
        }

        mockGetVision.set(true);
        this.visibilityRange.set(visibilityRange);
        call(() -> mineBotMock.updateVision(STARTING_POS.x, STARTING_POS.y, WORLD_SIZE - 1, WORLD_SIZE - 1), context,
            r -> "An exception occurred while invoking MineBot.updateVision(int, int, int, int)");
        if (checkPlacement) {
            visiblePointsStart.stream()
                .filter(point -> !visiblePointsEnd.contains(point))
                .forEach(point -> assertTrue(
                    World.getGlobalWorld().getField(point.x, point.y).getEntities().stream().anyMatch(Fog.class::isInstance),
                    context,
                    r -> "MineBot.updateVision(int, int, int, int) did not place fog on field (%d, %d)".formatted(point.x, point.y)));
        } else {
            visiblePointsEnd.forEach(point -> assertTrue(
                World.getGlobalWorld().getField(point.x, point.y).getEntities().stream().noneMatch(Fog.class::isInstance),
                context,
                r -> "MineBot.updateVision(int, int, int, int) did not remove fog on field (%d, %d)".formatted(point.x, point.y)));
        }
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

    private Camera makeCameraMock(int visibilityRange) {
        Answer<?> cameraAnswer = invocation -> {
            Method invokedMethod = invocation.getMethod();
            if (Utils.methodSignatureEquals(invokedMethod, "getName")) {
                return "Camera";
            } else if (Utils.methodSignatureEquals(invokedMethod, "getVisibilityRange")) {
                return visibilityRange;
            } else {
                return Mockito.RETURNS_DEFAULTS.answer(invocation);
            }
        };
        return Mockito.mock(Camera.class, cameraAnswer);
    }

    private Point[] getValidVisionPoints(int xPos, int yPos, int visibilityRange) {
        return IntStream.rangeClosed(0, 2 * visibilityRange)
            .map(x -> x + xPos - visibilityRange)
            .mapToObj(x -> IntStream.rangeClosed(0, 2 * visibilityRange)
                .map(y -> y + yPos - visibilityRange)
                .mapToObj(y -> new Point(x, y)))
            .flatMap(Function.identity())
            .filter(point -> Utils.pointInWorld(WORLD_SIZE, point))
            .toArray(Point[]::new);
    }
}
