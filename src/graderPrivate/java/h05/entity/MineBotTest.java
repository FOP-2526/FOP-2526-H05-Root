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
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Context;

import java.awt.*;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
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
        .add("world layout", "world size: %dx%d, MineBot at (%d, %d), loot at (%d, %d) and (%d, %d)"
            .formatted(WORLD_SIZE, WORLD_SIZE,
                STARTING_POS.x, STARTING_POS.y,
                STARTING_POS.x, STARTING_POS.y - 1,
                STARTING_POS.x, STARTING_POS.y + 1))
        .add("direction", currentDirection)
        .build();

    private enum MockedClass {
        GAME_SETTINGS,
        MINE_BOT
    }

    private Map<Point, Triple<Mineable, AtomicBoolean, AtomicBoolean>> lootMocks;
    private final Map<MockedClass, Map<Predicate<Method>, Function<InvocationOnMock, Object>>> methodBehaviour = new EnumMap<>(MockedClass.class);
    private GameSettings gameSettingsMock;
    private MineBot mineBotMock;

    @BeforeEach
    public void setup() {
        World.setSize(WORLD_SIZE, WORLD_SIZE);
        World.setVisible(false);
        World.setDelay(0);

        methodBehaviour.clear();
        for (MockedClass mockedClass : MockedClass.values()) {
            methodBehaviour.put(mockedClass, new HashMap<>());
        }
        methodBehaviour.get(MockedClass.MINE_BOT)
            .put(method -> Utils.methodSignatureEquals(method, "getDirection"),
                invocation -> currentDirection.get() == null ? Direction.UP : currentDirection.get());

        lootMocks = Stream.of(new Point(STARTING_POS.x, STARTING_POS.y - 1), new Point(STARTING_POS.x, STARTING_POS.y + 1))
            .map(point -> {
                AtomicBoolean isCalled = new AtomicBoolean();
                AtomicBoolean onMinedReturnValue = new AtomicBoolean();
                return Map.entry(point, new Triple<>(makeLootMock(isCalled, onMinedReturnValue), isCalled, onMinedReturnValue));
            })
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        Answer<?> gameSettingsAnswer = invocation -> {
            Method invokedMethod = invocation.getMethod();
            return methodBehaviour.get(MockedClass.GAME_SETTINGS)
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey().test(invokedMethod))
                .findFirst()
                .map(Map.Entry::getValue)
                .orElseGet(() -> invocationOnMock -> {
                    try {
                        return invocationOnMock.callRealMethod();
                    } catch (Throwable e) {
                        throw new RuntimeException(e);
                    }
                })
                .apply(invocation);
        };
        gameSettingsMock = Mockito.mock(BasicGameSettings.class, gameSettingsAnswer);


        Answer<?> mineBotAnswer = invocation -> {
            Method invokedMethod = invocation.getMethod();
            return methodBehaviour.get(MockedClass.MINE_BOT)
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey().test(invokedMethod))
                .findFirst()
                .map(Map.Entry::getValue)
                .orElseGet(() -> invocationOnMock -> {
                    try {
                        return invocationOnMock.callRealMethod();
                    } catch (Throwable e) {
                        throw new RuntimeException(e);
                    }
                })
                .apply(invocation);
        };
        mineBotMock = Mockito.mock(MineBot.class, Mockito.withSettings()
            .useConstructor(STARTING_POS.x, STARTING_POS.y, gameSettingsMock)
            .defaultAnswer(mineBotAnswer));
    }

    @ParameterizedTest
    @EnumSource(Direction.class)
    public void testMine_callsGetLootAt(Direction direction) {
        currentDirection.set(direction);
        AtomicReference<Point> getLootAt_argsRef = testMine_setup(direction);
        call(mineBotMock::mine, context, r -> "An exception occurred while invoking MineBot.mine()");
        Point args = getLootAt_argsRef.get();

        assertNotNull(args, context, r ->
            "MineBot.mine() did not call GameSettings.getLootAt(int, int)");
        assertEquals(STARTING_POS.x + direction.getDx(), args.x, context, r ->
            "MineBot.mine() did not call GameSettings.getLootAt(int, int) with the correct arguments (wrong x coordinate)");
        assertEquals(STARTING_POS.y + direction.getDy(), args.y, context, r ->
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

        lootMocks.values().forEach(triple -> assertFalse(triple.getSecond().get(),
            context,
            r -> "There is no loot at (%d, %d), MineBot should have returned"
                .formatted(STARTING_POS.x + direction.getDx(), STARTING_POS.y + direction.getDy())));
        assertEquals(0, mineBotMock.getInventory().size(), context,
            r -> "There is no loot at (%d, %d), MineBot should have returned and not modify the inventory"
                .formatted(STARTING_POS.x + direction.getDx(), STARTING_POS.y + direction.getDy()));
    }

    @ParameterizedTest
    @EnumSource(Direction.class)
    public void testMine_callsOnMined(Direction direction) {
        if (direction.isHorizontal()) {
            return;
        }
        testMine_setup(direction);
        call(mineBotMock::mine, context, r -> "An exception occurred while invoking MineBot.mine()");

        AtomicBoolean onMinedCalled = lootMocks.get(new Point(STARTING_POS.x + direction.getDx(), STARTING_POS.y + direction.getDy())).getSecond();
        assertTrue(onMinedCalled.get(), context,
            r -> "There is loot at (%d, %d) but MineBot did not call Mineable.onMined(Tool) on it"
                .formatted(STARTING_POS.x + direction.getDx(), STARTING_POS.y + direction.getDy()));
    }

    @ParameterizedTest
    @EnumSource(Direction.class)
    public void testMine_placesInInventory(Direction direction) {
        if (direction.isHorizontal()) {
            return;
        }
        testMine_setup(direction);
        lootMocks.get(new Point(STARTING_POS.x + direction.getDx(), STARTING_POS.y + direction.getDy())).getThird().set(true);
        call(mineBotMock::mine, context, r -> "An exception occurred while invoking MineBot.mine()");

        assertEquals(1, mineBotMock.getInventory().size(), context,
            r -> "There is loot at (%d, %d) but MineBot did not place it in its inventory"
                .formatted(STARTING_POS.x + direction.getDx(), STARTING_POS.y + direction.getDy()));
        assertTrue(Arrays.stream(mineBotMock.getInventory().getNames()).filter(Objects::nonNull).findFirst().map("Loot"::equals).orElse(false),
            context,
            r -> "There is loot at (%d, %d) but MineBot did not place it in its inventory"
                .formatted(STARTING_POS.x + direction.getDx(), STARTING_POS.y + direction.getDy()));
    }

    private AtomicReference<Point> testMine_setup(Direction direction) {
        AtomicReference<Point> getLootAt_argsRef = new AtomicReference<>();
        currentDirection.set(direction);
        methodBehaviour.get(MockedClass.GAME_SETTINGS)
            .put(method -> Utils.methodSignatureEquals(method, "getLootAt", int.class, int.class),
                invocation -> {
                    getLootAt_argsRef.set(new Point(invocation.getArgument(0), invocation.getArgument(1)));
                    if (lootMocks.containsKey(getLootAt_argsRef.get())) {
                        return lootMocks.get(getLootAt_argsRef.get()).getFirst();
                    } else {
                        return null;
                    }
                });
        return getLootAt_argsRef;
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

        methodBehaviour.get(MockedClass.MINE_BOT)
            .put(method -> Utils.methodSignatureEquals(method, "getVision", int.class, int.class),
                invocation -> getValidVisionPoints(invocation.getArgument(0), invocation.getArgument(0), visibilityRange));
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
