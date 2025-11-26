package h05.equipment;

import com.google.common.util.concurrent.AtomicDouble;
import h05.TestUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@TestForSubmission
public class BatteryTest {

    @Test
    public void testIncreaseDurability() {
        Map<EquipmentCondition, Double> conditionMapping = Map.of(
            EquipmentCondition.NEW, 90d,
            EquipmentCondition.USED, 60d,
            EquipmentCondition.DAMAGED, 20d,
            EquipmentCondition.BROKEN, 0d
        );
        AtomicReference<EquipmentCondition> condition = new AtomicReference<>();
        AtomicDouble durability = new AtomicDouble();
        Answer<?> answer = invocationOnMock -> {
            Method invokedMethod = invocationOnMock.getMethod();
            if (TestUtils.methodSignatureEquals(invokedMethod, "getDurability")) {
                return durability.get();
            } else if (TestUtils.methodSignatureEquals(invokedMethod, "setDurability", double.class)) {
                durability.set(invocationOnMock.getArgument(0, Double.class));
                return null;
            } else if (TestUtils.methodSignatureEquals(invokedMethod, "getCondition")) {
                return condition.get();
            } else {
                return invocationOnMock.callRealMethod();
            }
        };
        Battery instance = Mockito.mock(Battery.class, answer);

        for (Map.Entry<EquipmentCondition, Double> entry : conditionMapping.entrySet()) {
            condition.set(entry.getKey());
            durability.set(entry.getValue());
            call(() -> instance.increaseDurability(10d));
            if (entry.getKey() ==  EquipmentCondition.BROKEN) {
                assertEquals(0d, durability.get(), emptyContext(),
                    r -> "increaseDurability(double) increased the durability of a broken battery");
            } else {
                assertEquals(entry.getValue() + 10d, durability.get(), emptyContext(),
                    r -> "increaseDurability(double) did not set the durability to the correct value");
            }
        }
    }
}
