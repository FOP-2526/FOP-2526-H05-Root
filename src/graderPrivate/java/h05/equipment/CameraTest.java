package h05.equipment;

import h05.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@TestForSubmission
public class CameraTest {

    @Test
    public void testGetVisibility() {
        Map<EquipmentCondition, Integer> conditionMapping = Map.of(
            EquipmentCondition.NEW, 10,
            EquipmentCondition.USED, 10,
            EquipmentCondition.DAMAGED, 5,
            EquipmentCondition.BROKEN, 0
        );
        AtomicReference<EquipmentCondition> condition = new AtomicReference<>();
        Answer<?> answer = invocationOnMock -> {
            Method invokedMethod = invocationOnMock.getMethod();
            if (TestUtils.methodSignatureEquals(invokedMethod, "getCondition")) {
                return condition.get();
            } else {
                return invocationOnMock.callRealMethod();
            }
        };
        Camera instance = Mockito.mock(Camera.class, Mockito.withSettings().defaultAnswer(answer).useConstructor(10));

        for (Map.Entry<EquipmentCondition, Integer> entry : conditionMapping.entrySet()) {
            condition.set(entry.getKey());
            assertCallEquals(entry.getValue(), instance::getVisibilityRange, emptyContext(),
                r -> "getVisibilityRange() did return the correct value for condition = " + entry.getValue());
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {5, 1, 0, -1})
    public void testSetVisibility(int visibility) {
        Camera instance = new Camera();
        call(() -> instance.setVisibilityRange(visibility));
        assertCallEquals(Math.max(visibility, 1), instance::getVisibilityRange, emptyContext(),
            r -> "setVisibilityRange(int) did not set the visibility to the correct value");
    }
}
