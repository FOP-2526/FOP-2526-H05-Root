package h05;

import h05.equipment.*;
import h05.mineable.Rock;
import h05.mineable.Tree;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Context;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@TestForSubmission
public class TestDurableImpls {

    private enum TestCase {
        INITIAL_DURABILITY,
        SET_DURABILITY,
        GET_DURABILITY_AFTER_SET_DURABILITY,
        SET_DURABILITY_BOUNDS,
        REDUCE_DURABILITY,
        GET_DURABILITY_AFTER_REDUCE_DURABILITY
    }

    private static final Map<Class<? extends Durable>, Supplier<? extends Durable>> DURABLE_IMPL_SUPPLIERS = Map.of(
        // FIXME: indirectly link to classes that may not exist (Axe, Pickaxe, Powerbank, TelephotoLens)
        Axe.class, Axe::new,
        Battery.class, Battery::new,
        Camera.class, Camera::new,
        Pickaxe.class, Pickaxe::new,
        Powerbank.class, () -> new Powerbank(0),
        TelephotoLens.class, () -> new TelephotoLens(0),
        WallBreaker.class, WallBreaker::new,
        Rock.class, Rock::new,
        Tree.class, Tree::new
    );

    @Test
    public void testDurableImplsMinimum() {
        testDurableImpls(4);
    }

    @Test
    public void testDurableImplsAll() {
        testDurableImpls(DURABLE_IMPL_SUPPLIERS.size());
    }

    private void testDurableImpls(int minimumCorrect) {
        Map<Class<? extends Durable>, Map<TestCase, Boolean>> results = new HashMap<>();

        for (Map.Entry<Class<? extends Durable>, Supplier<? extends Durable>> entry : DURABLE_IMPL_SUPPLIERS.entrySet()) {
            Map<TestCase, Boolean> testCaseResults = new EnumMap<>(TestCase.class);

            for (TestCase testCase : TestCase.values()) {
                Durable instance = entry.getValue().get();
                Boolean result = switch (testCase) {
                    case INITIAL_DURABILITY -> testGetDurabilityInitial(instance);
                    case SET_DURABILITY -> testSetDurability(instance);
                    case GET_DURABILITY_AFTER_SET_DURABILITY -> testGetDurabilityAfterSetDurability(instance);
                    case SET_DURABILITY_BOUNDS -> testSetDurabilityBounds(instance);
                    case REDUCE_DURABILITY -> testReduceDurability(instance);
                    case GET_DURABILITY_AFTER_REDUCE_DURABILITY -> testGetDurabilityAfterReduceDurability(instance);
                };
                testCaseResults.put(testCase, result);
            }

            results.put(entry.getKey(), testCaseResults);
        }

        Map<Class<? extends Durable>, List<TestCase>> classesWithFailedTests = results.entrySet()
            .stream()
            .map(entry -> Map.entry(entry.getKey(),
                entry.getValue().entrySet().stream().filter(Predicate.not(Map.Entry::getValue)).map(Map.Entry::getKey).toList()))
            .filter(entry -> !entry.getValue().isEmpty())
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        if (classesWithFailedTests.size() > results.size() - minimumCorrect) {
            Context.Builder<?> contextBuilder = contextBuilder();
            classesWithFailedTests.forEach((clazz, testCaseResults) -> {
                contextBuilder.add("failed test cases for %s".formatted(clazz), testCaseResults);
            });
            fail(contextBuilder.build(), r -> "At least one class has not passed all test cases");
        }
    }

    private boolean testGetDurabilityInitial(Durable durableImpl) {
        return durableImpl.getDurability() == 100;
    }

    private boolean testSetDurability(Durable durableImpl) {
        try {
            durableImpl.setDurability(50);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean testGetDurabilityAfterSetDurability(Durable durableImpl) {
        try {
            durableImpl.setDurability(50);
            return durableImpl.getDurability() == 50;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean testSetDurabilityBounds(Durable durableImpl) {
        try {
            durableImpl.setDurability(Integer.MIN_VALUE);
            if (durableImpl.getDurability() == 0) {
                durableImpl.setDurability(Integer.MAX_VALUE);
                return durableImpl.getDurability() == 100;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    private boolean testReduceDurability(Durable durableImpl) {
        try {
            for (int i = 0; i < 15; i++) {
                durableImpl.reduceDurability(i);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean testGetDurabilityAfterReduceDurability(Durable durableImpl) {
        try {
            for (int i = 0, expected = 100; i < 15; i++) {
                durableImpl.reduceDurability(i);
                if (durableImpl.getDurability() != Math.max(0, expected -= i)) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
