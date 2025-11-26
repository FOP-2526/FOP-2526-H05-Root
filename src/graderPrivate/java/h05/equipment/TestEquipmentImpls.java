package h05.equipment;

import h05.Durable;
import h05.Links;
import h05.TestUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.reflections.BasicTypeLink;
import org.tudalgo.algoutils.tutor.general.reflections.TypeLink;

import java.lang.reflect.Method;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@TestForSubmission
public class TestEquipmentImpls {

    private enum TestCase {
        NEW(81, 100, EquipmentCondition.NEW),
        USED(41, 80, EquipmentCondition.USED),
        DAMAGED(1, 40, EquipmentCondition.DAMAGED),
        BROKEN(0, 0, EquipmentCondition.BROKEN);

        private final int min;
        private final int max;
        private final EquipmentCondition condition;

        TestCase(int min, int max, EquipmentCondition condition) {
            this.min = min;
            this.max = max;
            this.condition = condition;
        }
    }

    private static final Map<String, Supplier<TypeLink>> EQUIPMENT_IMPLS = Map.of(
        "h05.equipment.Axe", Links.AXE_TYPE_LINK,
        "h05.equipment.Battery", () -> BasicTypeLink.of(Battery.class),
        "h05.equipment.Camera", () -> BasicTypeLink.of(Camera.class),
        "h05.equipment.Pickaxe", Links.PICKAXE_TYPE_LINK,
        "h05.equipment.Powerbank", Links.POWERBANK_TYPE_LINK,
        "h05.equipment.TelephotoLens", Links.TELEPHOTO_LENS_TYPE_LINK,
        "h05.equipment.WallBreaker", () -> BasicTypeLink.of(WallBreaker.class)
    );

    @Test
    public void testEquipmentImplsMinimum() {
        testEquipmentImpls(results -> results.values()
            .stream()
            .flatMap(map -> map.values().stream())
            .noneMatch(b -> b));
    }

    @Test
    public void testEquipmentImplsAll() {
        testEquipmentImpls(results -> !results.values()
            .stream()
            .flatMap(map -> map.values().stream())
            .allMatch(b -> b));
    }

    private void testEquipmentImpls(Predicate<Map<Class<? extends Equipment>, Map<TestCase, Boolean>>> testCase) {
        Map<Class<? extends Equipment>, Map<TestCase, Boolean>> results = testDurableImpls();
        Map<Class<? extends Durable>, List<TestCase>> classesWithFailedTests = results.entrySet()
            .stream()
            .map(entry -> Map.entry(entry.getKey(),
                entry.getValue().entrySet().stream().filter(Predicate.not(Map.Entry::getValue)).map(Map.Entry::getKey).toList()))
            .filter(entry -> !entry.getValue().isEmpty())
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        if (testCase.test(results)) {
            Context.Builder<?> contextBuilder = contextBuilder();
            classesWithFailedTests.forEach((clazz, testCaseResults) -> {
                contextBuilder.add("failed test cases for %s".formatted(clazz), testCaseResults);
            });
            fail(contextBuilder.build(), r -> "At least one class has not passed all test cases");
        }
    }

    private Map<Class<? extends Equipment>, Map<TestCase, Boolean>> testDurableImpls() {
        Map<Class<? extends Equipment>, Map<TestCase, Boolean>> results = new HashMap<>();

        for (Map.Entry<String, Supplier<TypeLink>> entry : EQUIPMENT_IMPLS.entrySet()) {
            TypeLink typeLink = assertCallNotNull(entry.getValue()::get, emptyContext(),
                r -> "Could not find class " + entry.getKey());
            Class<? extends Equipment> equipmentClass = (Class<? extends Equipment>) typeLink.reflection();
            Map<TestCase, Boolean> testCaseResults = new EnumMap<>(TestCase.class);

            for (TestCase testCase : TestCase.values()) {
                AtomicInteger durability = new AtomicInteger();
                Equipment instance = getEquipmentMock(equipmentClass, durability);
                Boolean result = testGetCondition(instance, testCase, durability);
                testCaseResults.put(testCase, result);
            }

            results.put(equipmentClass, testCaseResults);
        }

        return results;
    }

    private boolean testGetCondition(Equipment instance, TestCase testCase, AtomicInteger durability) {
        try {
            durability.set(testCase.min);
            boolean minCorrect = instance.getCondition() == testCase.condition;
            durability.set(testCase.max);
            boolean maxCorrect = instance.getCondition() == testCase.condition;

            return minCorrect && maxCorrect;
        } catch (Exception e) {
            return false;
        }
    }

    private Equipment getEquipmentMock(Class<? extends Equipment> clazz, AtomicInteger durability) {
        Answer<?> answer = invocationOnMock -> {
            Method invokedMethod = invocationOnMock.getMethod();
            if (TestUtils.methodSignatureEquals(invokedMethod, "getDurability")) {
                return (double) durability.get();
            } else {
                return invocationOnMock.callRealMethod();
            }
        };
        return Mockito.mock(clazz, answer);
    }
}
