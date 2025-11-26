package h05.entity;

import com.google.common.util.concurrent.AtomicDouble;
import fopbot.Direction;
import fopbot.World;
import h05.TestUtils;
import h05.base.entity.Fog;
import h05.base.game.BasicGameSettings;
import h05.base.game.GameSettings;
import h05.equipment.*;
import h05.mineable.Mineable;
import kotlin.Pair;
import kotlin.Triple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
import java.util.List;
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
        methodBehaviour.get(MockedClass.GAME_SETTINGS)
            .put(method -> TestUtils.methodSignatureEquals(method, "update"),
                invocation -> null);

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
        setDirection(direction);
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
        setDirection(direction);
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
        setDirection(direction);
        methodBehaviour.get(MockedClass.GAME_SETTINGS)
            .put(method -> TestUtils.methodSignatureEquals(method, "getLootAt", int.class, int.class),
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

        if (assertPoints) {
            Set<Point> expectedPoints = Set.of(validPoints);
            Set<Point> actualPoints = Set.of(points);
            assertTrue(actualPoints.containsAll(expectedPoints), context,
                r -> "The returned array does not contain all valid points. Missing: " +
                    expectedPoints.stream()
                        .filter(Predicate.not(actualPoints::contains))
                        .map(point -> "(%d, %d)".formatted(point.x, point.y))
                        .collect(Collectors.joining(", ")));
        } else {
            assertEquals(validPoints.length, points.length, context,
                r -> "The returned array does not have the correct number of points");
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
            .put(method -> TestUtils.methodSignatureEquals(method, "getVision", int.class, int.class),
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

    @ParameterizedTest
    @EnumSource(Direction.class)
    public void testMove_callsUpdateVision(Direction direction) {
        setDirection(direction);
        List<Pair<Point, Point>> updateVisionArgs = new ArrayList<>();
        methodBehaviour.get(MockedClass.MINE_BOT)
            .put(method -> TestUtils.methodSignatureEquals(method, "updateVision", int.class, int.class, int.class, int.class),
                invocation -> {
                    Point oldPoint = new Point(invocation.getArgument(0), invocation.getArgument(1));
                    Point newPoint = new Point(invocation.getArgument(2), invocation.getArgument(3));
                    updateVisionArgs.add(new Pair<>(oldPoint, newPoint));

                    return null;
                });
        call(mineBotMock::move, context, r -> "An exception occurred while invoking MineBot.move()");

        assertEquals(1, updateVisionArgs.size(), context,
            r -> "MineBot.move() did not call MineBot.updateVision(int, int, int, int) exactly once");
        Pair<Point, Point> args = updateVisionArgs.getFirst();
        Point oldXY = args.getFirst();
        Point newXY = args.getSecond();
        assertEquals(STARTING_POS.x, oldXY.x, context,
            r -> "MineBot.move() passed the wrong value to the first parameter of MineBot.updateVision(int, int, int, int)");
        assertEquals(STARTING_POS.y, oldXY.y, context,
            r -> "MineBot.move() passed the wrong value to the second parameter of MineBot.updateVision(int, int, int, int)");
        assertEquals(STARTING_POS.x + direction.getDx(), newXY.x, context,
            r -> "MineBot.move() passed the wrong value to the third parameter of MineBot.updateVision(int, int, int, int)");
        assertEquals(STARTING_POS.y + direction.getDy(), newXY.y, context,
            r -> "MineBot.move() passed the wrong value to the fourth parameter of MineBot.updateVision(int, int, int, int)");
    }

    @ParameterizedTest
    @EnumSource(EquipmentCondition.class)
    public void testMove_reducesBattery(EquipmentCondition condition) {
        double initialBatteryDurability = switch (condition) {
            case NEW -> 100d;
            case USED -> 80d;
            case DAMAGED -> 40d;
            case BROKEN -> 0d;
        };
        AtomicDouble batteryDurability = new AtomicDouble(initialBatteryDurability);
        Battery batteryMock = makeBatteryMock(batteryDurability);

        setDirection(Direction.UP);
        methodBehaviour.get(MockedClass.MINE_BOT)
            .put(method -> TestUtils.methodSignatureEquals(method, "updateVision", int.class, int.class, int.class, int.class),
                invocation -> null);
        methodBehaviour.get(MockedClass.MINE_BOT)
            .put(method -> TestUtils.methodSignatureEquals(method, "getNumberOfEquipments"),
                invocation -> (condition.ordinal() + 1) * 10);

        mineBotMock.equip(batteryMock);
        call(mineBotMock::move, context, r -> "An exception occurred while invoking MineBot.move()");
        if (condition != EquipmentCondition.BROKEN) {
            assertEquals(initialBatteryDurability - ((condition.ordinal()) + 1) * 10, batteryDurability.get(), context,
                r -> "MineBot.move() did not reduce the battery's durability by the correct amount");
            assertEquals(STARTING_POS.x + currentDirection.get().getDx(), mineBotMock.getX(), context,
                r -> "MineBot.move() did not move the Robot to the correct x coordinate");
            assertEquals(STARTING_POS.y + currentDirection.get().getDy(), mineBotMock.getY(), context,
                r -> "MineBot.move() did not move the Robot to the correct y coordinate");
        } else {
            assertEquals(0d, batteryDurability.get(), context,
                r -> "MineBot.move() did not reduce the battery's durability by the correct amount");
            assertEquals(STARTING_POS.x, mineBotMock.getX(), context,
                r -> "MineBot.move() moved the Robot despite the battery being broken (incorrect x coordinate)");
            assertEquals(STARTING_POS.y, mineBotMock.getY(), context,
                r -> "MineBot.move() moved the Robot despite the battery being broken (incorrect y coordinate)");
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"Equipment", "Thing", "Tool", "Object"})
    public void testUse_correctEquipment(String equipmentName) {
        AtomicReference<? extends Miner> use_argsRef = new AtomicReference<>();
        Equipment equipmentMock = makeUsableEquipmentMock(equipmentName, use_argsRef);

        mineBotMock.equip(equipmentMock);
        call(() -> mineBotMock.use(0), context, r -> "An exception occurred while invoking MineBot.use(int)");
        assertSame(mineBotMock, use_argsRef.get(), context,
            r -> "MineBot.use(int) did not invoke use(Miner) on the equipment with itself as argument (aka 'this')");
    }

    @Test
    public void testUse_telephotoLens() {
        Equipment equipmentMock = makeUsableEquipmentMock("TelephotoLens", new AtomicReference<>());
        mineBotMock.equip(equipmentMock);

        AtomicReference<Pair<Point, Point>> updateVision_argsRef = new AtomicReference<>();
        methodBehaviour.get(MockedClass.MINE_BOT)
            .put(method -> TestUtils.methodSignatureEquals(method, "updateVision", int.class, int.class, int.class, int.class),
                invocation -> {
                    Point oldXY = new Point(invocation.getArgument(0), invocation.getArgument(1));
                    Point newXY = new Point(invocation.getArgument(2), invocation.getArgument(3));
                    updateVision_argsRef.set(new Pair<>(oldXY, newXY));
                    return null;
                });

        call(() -> mineBotMock.use(0), context, r -> "An exception occurred while invoking MineBot.use(int)");
        Pair<Point, Point> args = assertCallNotNull(updateVision_argsRef::get, context,
            r -> "MineBot.use(int) did not call MineBot.updateVision(int, int, int, int)");
        Point oldXY = args.getFirst();
        Point newXY = args.getSecond();
        assertEquals(STARTING_POS.x, oldXY.x, context,
            r -> "MineBot.use(int) passed the wrong value to the first parameter of MineBot.updateVision(int, int, int, int)");
        assertEquals(STARTING_POS.y, oldXY.y, context,
            r -> "MineBot.use(int) passed the wrong value to the second parameter of MineBot.updateVision(int, int, int, int)");
        assertEquals(STARTING_POS.x, newXY.x, context,
            r -> "MineBot.use(int) passed the wrong value to the third parameter of MineBot.updateVision(int, int, int, int)");
        assertEquals(STARTING_POS.y, newXY.y, context,
            r -> "MineBot.use(int) passed the wrong value to the fourth parameter of MineBot.updateVision(int, int, int, int)");
    }

    private Mineable makeLootMock(AtomicBoolean isCalled, AtomicBoolean onMinedReturnValue) {
        Answer<?> answer = invocation -> {
            Method invokedMethod = invocation.getMethod();
            if (TestUtils.methodSignatureEquals(invokedMethod, "onMined", Tool.class)) {
                isCalled.set(true);
                return onMinedReturnValue.get();
            } else if (TestUtils.methodSignatureEquals(invokedMethod, "getName")) {
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
            if (TestUtils.methodSignatureEquals(invokedMethod, "getName")) {
                return "Camera";
            } else if (TestUtils.methodSignatureEquals(invokedMethod, "getVisibilityRange")) {
                return visibilityRange;
            } else {
                return Mockito.RETURNS_DEFAULTS.answer(invocation);
            }
        };
        return Mockito.mock(Camera.class, cameraAnswer);
    }

    private Battery makeBatteryMock(AtomicDouble batteryDurability) {
        Answer<?> batteryAnswer = invocation -> {
            Method invokedMethod = invocation.getMethod();
            if (TestUtils.methodSignatureEquals(invokedMethod, "getName")) {
                return "Battery";
            } else if (TestUtils.methodSignatureEquals(invokedMethod, "getDurability")) {
                return batteryDurability.get();
            } else if (TestUtils.methodSignatureEquals(invokedMethod, "setDurability", double.class)) {
                batteryDurability.set(Math.max(0d, Math.min(invocation.getArgument(0), 100d)));
                return null;
            } else if (TestUtils.methodSignatureEquals(invokedMethod, "reduceDurability", double.class)) {
                batteryDurability.set(Math.max(0d, batteryDurability.get() - invocation.getArgument(0, Double.class)));
                return null;
            } else if (TestUtils.methodSignatureEquals(invokedMethod, "getCondition")) {
                double durability = batteryDurability.get();
                if (durability > 80) {
                    return EquipmentCondition.NEW;
                } else if (durability > 40) {
                    return EquipmentCondition.USED;
                } else if (durability > 0) {
                    return EquipmentCondition.DAMAGED;
                } else {
                    return EquipmentCondition.BROKEN;
                }
            } else {
                return invocation.callRealMethod();
            }
        };
        return Mockito.mock(Battery.class, batteryAnswer);
    }

    private UsableEquipment makeUsableEquipmentMock(String equipmentName, AtomicReference<? extends Miner> use_argsRef) {
        Answer<?> equipmentAnswer = invocation -> {
            Method invokedMethod = invocation.getMethod();
            if (TestUtils.methodSignatureEquals(invokedMethod, "getName")) {
                return equipmentName;
            } else if (TestUtils.methodSignatureEquals(invokedMethod, "use", Miner.class)) {
                use_argsRef.set(invocation.getArgument(0));
                return null;
            } else if (TestUtils.methodSignatureEquals(invokedMethod, "isUsable")) {
                return true;
            } else if (TestUtils.methodSignatureEquals(invokedMethod, "isTool")) {
                return false;
            } else {
                return Mockito.RETURNS_DEFAULTS.answer(invocation);
            }
        };
        return Mockito.mock(UsableEquipment.class, equipmentAnswer);
    }

    private Point[] getValidVisionPoints(int xPos, int yPos, int visibilityRange) {
        return IntStream.rangeClosed(0, 2 * visibilityRange)
            .map(x -> x + xPos - visibilityRange)
            .mapToObj(x -> IntStream.rangeClosed(0, 2 * visibilityRange)
                .map(y -> y + yPos - visibilityRange)
                .mapToObj(y -> new Point(x, y)))
            .flatMap(Function.identity())
            .filter(point -> TestUtils.pointInWorld(WORLD_SIZE, point))
            .toArray(Point[]::new);
    }

    private void setDirection(Direction direction) {
        currentDirection.set(direction);
        while (mineBotMock.getDirection() != direction) {
            mineBotMock.turnLeft();
        }
    }
}
