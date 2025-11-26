package h05.entity;

import fopbot.World;
import h05.TestUtils;
import h05.base.game.BasicGameSettings;
import h05.base.game.GameSettings;
import h05.equipment.Battery;
import h05.equipment.Camera;
import h05.equipment.EquipmentCondition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Context;

import java.awt.Point;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@TestForSubmission
public class AbstractRepairBotTest {

    private static final int WORLD_SIZE = 5;
    private static final Point STARTING_POS = new  Point(2, 2);
    private final Context context = contextBuilder()
        .add("world layout", "world size: %dx%d, RepairBot at (%d, %d), broken MineBot at (%d, %d)"
            .formatted(WORLD_SIZE, WORLD_SIZE,
                STARTING_POS.x, STARTING_POS.y,
                STARTING_POS.x + 1, STARTING_POS.y + 1))
        .build();

    private enum MockedClass {
        GAME_SETTINGS,
        ABSTRACT_REPAIR_BOT,
        BROKEN_MINE_BOT
    }

    private final Map<MockedClass, Map<Predicate<Method>, Function<InvocationOnMock, Object>>> methodBehaviour = new EnumMap<>(MockedClass.class);
    private GameSettings gameSettingsMock;
    private AbstractRepairBot repairBotMock;
    private MineBot brokenMineBotMock;

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
        methodBehaviour.get(MockedClass.ABSTRACT_REPAIR_BOT)
            .put(method -> TestUtils.methodSignatureEquals(method, "move", Point.class),
                invocation -> null);

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

        Answer<?> repairBotAnswer = invocation -> {
            Method invokedMethod = invocation.getMethod();
            return methodBehaviour.get(MockedClass.ABSTRACT_REPAIR_BOT)
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
        repairBotMock = Mockito.mock(AbstractRepairBot.class, Mockito.withSettings()
            .useConstructor(STARTING_POS.x, STARTING_POS.y, gameSettingsMock, 1)
            .defaultAnswer(repairBotAnswer));

        brokenMineBotMock = makeBrokenMineBotMock();
    }

    @Test
    public void testScan_withBrokenBot() {
        Point point = assertCallNotNull(repairBotMock::scan, context,
            r -> "AbstractRepairBot.scan() returned null even though a broken MineBot is in its radius (1)");
        assertEquals(STARTING_POS.x + 1, point.x, context,
            r -> "AbstractRepairBot.scan() returned an incorrect point (wrong x coordinate)");
        assertEquals(STARTING_POS.y + 1, point.y, context,
            r -> "AbstractRepairBot.scan() returned an incorrect point (wrong y coordinate)");
    }

    @Test
    public void testScan_noBrokenBots() {
        methodBehaviour.get(MockedClass.GAME_SETTINGS)
            .put(method -> TestUtils.methodSignatureEquals(method, "getMinerAt", int.class, int.class),
                invocation -> null);
        Context context = contextBuilder()
            .add("world layout", "world size: %dx%d, RepairBot at (%d, %d), no broken MineBots"
                .formatted(WORLD_SIZE, WORLD_SIZE,
                    STARTING_POS.x, STARTING_POS.y))
            .build();
        assertCallNull(repairBotMock::scan, context,
            r -> "AbstractRepairBot.scan() returned did not return null even though no broken MineBots are in its radius (1)");
    }

    @Test
    public void testRepair_batteryAndCamera() {
        methodBehaviour.get(MockedClass.ABSTRACT_REPAIR_BOT)
            .put(method -> TestUtils.methodSignatureEquals(method, "getX"),
                invocation -> STARTING_POS.x + 1);
        methodBehaviour.get(MockedClass.ABSTRACT_REPAIR_BOT)
            .put(method -> TestUtils.methodSignatureEquals(method, "getY"),
                invocation -> STARTING_POS.y + 1);

        call(() -> repairBotMock.repair(new Point(STARTING_POS.x + 1, STARTING_POS.y + 1)), context,
            r -> "An exception occurred while invoking AbstractRepairBot.repair(Point)");
        Battery newBattery = brokenMineBotMock.getBattery();
        assertTrue(newBattery.getDurability() == 100d && newBattery.getCondition() == EquipmentCondition.NEW, context,
            r -> "AbstractRepairRobot.repair(Point) was called on the broken MineBot but its Battery is still broken");
        Camera newCamera = brokenMineBotMock.getCamera();
        assertTrue(newCamera.getDurability() == 100d && newCamera.getCondition() == EquipmentCondition.NEW, context,
            r -> "AbstractRepairRobot.repair(Point) was called on the broken MineBot but its Camera is still broken");
    }

    @Test
    public void testRepair_otherEquipment() {
        methodBehaviour.get(MockedClass.ABSTRACT_REPAIR_BOT)
            .put(method -> TestUtils.methodSignatureEquals(method, "getX"),
                invocation -> STARTING_POS.x + 1);
        methodBehaviour.get(MockedClass.ABSTRACT_REPAIR_BOT)
            .put(method -> TestUtils.methodSignatureEquals(method, "getY"),
                invocation -> STARTING_POS.y + 1);

        call(() -> repairBotMock.repair(new Point(STARTING_POS.x + 1, STARTING_POS.y + 1)), context,
            r -> "An exception occurred while invoking AbstractRepairBot.repair(Point)");
        Arrays.stream(brokenMineBotMock.getEquipments())
            .filter(equipment -> equipment.getDurability() == 0d || equipment.getCondition() == EquipmentCondition.BROKEN)
            .forEach(equipment -> fail(context,
                r -> "AbstractRepairRobot.repair(Point) was called on the broken MineBot but it still has broken equipment"));
    }

    private MineBot makeBrokenMineBotMock() {
        Function<String, Answer<?>> equipmentAnswerForName = name -> invocation -> {
            Method invokedMethod = invocation.getMethod();
            if (TestUtils.methodSignatureEquals(invokedMethod, "getName")) {
                return name;
            } else if (TestUtils.methodSignatureEquals(invokedMethod, "getDurability")) {
                return 0d;
            } else if (TestUtils.methodSignatureEquals(invokedMethod, "getCondition")) {
                return EquipmentCondition.BROKEN;
            } else {
                return Mockito.RETURNS_DEFAULTS.answer(invocation);
            }
        };
        Answer<?> mineBotAnswer = invocation -> {
            Method invokedMethod = invocation.getMethod();
            return methodBehaviour.get(MockedClass.BROKEN_MINE_BOT)
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
        MineBot brokenMineBotMock = Mockito.mock(MineBot.class, Mockito.withSettings()
            .useConstructor(STARTING_POS.x + 1, STARTING_POS.y + 1, gameSettingsMock)
            .defaultAnswer(mineBotAnswer));
        brokenMineBotMock.equip(Mockito.mock(Battery.class, equipmentAnswerForName.apply("Battery")));
        brokenMineBotMock.equip(Mockito.mock(Camera.class, equipmentAnswerForName.apply("Camera")));
        brokenMineBotMock.equip(Mockito.mock(Camera.class, equipmentAnswerForName.apply("BrokenThingy")));

        return brokenMineBotMock;
    }
}
