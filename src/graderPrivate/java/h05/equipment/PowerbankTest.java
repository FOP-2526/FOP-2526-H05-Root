package h05.equipment;

import com.google.common.util.concurrent.AtomicDouble;
import h05.TestUtils;
import h05.entity.Miner;
import kotlin.Pair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.stubbing.Answer;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.reflections.*;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.concurrent.atomic.AtomicReference;

import static h05.Links.*;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@TestForSubmission
public class PowerbankTest {

    @Test
    public void testDefinition() {
        TypeLink type = POWERBANK_TYPE_LINK.get();
        assertNotNull(type, emptyContext(), r -> "Could not find class h05.equipment.Powerbank");
        assertTrue((type.modifiers() & Modifier.ABSTRACT) == 0, emptyContext(),
            r -> "Class Powerbank is abstract");
        assertTrue(TestUtils.getInterfaces(type.reflection()).contains(UsableEquipment.class), emptyContext(),
            r -> "Class Powerbank does not implement interface UsableEquipment");

        ConstructorLink constructor = POWERBANK_CONSTRUCTOR_LINK.get();
        assertNotNull(constructor, emptyContext(),
            r -> "Class Powerbank does not have a constructor Powerbank(double)");

        FieldLink capacityField = POWERBANK_CAPACITY_FIELD_LINK.get();
        assertNotNull(capacityField, emptyContext(), r -> "Could not find field 'capacity' in class Powerbank");
        assertTrue((capacityField.modifiers() & Modifier.PRIVATE) != 0, emptyContext(),
            r -> "Field 'capacity' in class Powerbank is not private");
        assertEquals(double.class, capacityField.type().reflection(), emptyContext(),
            r -> "Field 'capacity' in class Powerbank does not have type double");

        TestUtils.testPublicMethodsExist(type.name(), UsableEquipment.class.getMethods(), type.reflection().getMethods());
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1, 0, 1})
    public void testConstructor(double capacity) throws Throwable {
        ConstructorLink constructor = assertNotNull(POWERBANK_CONSTRUCTOR_LINK.get(), emptyContext(),
            r -> "Class Powerbank does not have a constructor Powerbank(double)");
        FieldLink capacityField = POWERBANK_CAPACITY_FIELD_LINK.get();
        Object instance = constructor.invoke(capacity);

        assertEquals(capacity, capacityField.get(instance), contextBuilder().add("Constructor arg 1", capacity).build(),
            r -> "Powerbank's constructor did not set field 'capacity' to the correct value");
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1, 0, 1})
    public void testGetCapacity(double capacity) throws Throwable {
        ConstructorLink constructor = assertNotNull(POWERBANK_CONSTRUCTOR_LINK.get(), emptyContext(),
            r -> "Class Powerbank does not have a constructor Powerbank(double)");
        MethodLink getCapacityMethod = POWERBANK_GET_CAPACITY_METHOD_LINK.get();
        Object instance = constructor.invoke(capacity);

        assertEquals(capacity, getCapacityMethod.invoke(instance), contextBuilder().add("Constructor arg 1", capacity).build(),
            r -> "Method getCapacity() in class Powerbank did not return the correct value");
    }

    @ParameterizedTest
    @EnumSource(EquipmentCondition.class)
    public void testUse(EquipmentCondition condition) {
        double initialDurability = switch (condition) {
            case NEW -> 100d;
            case USED -> 80d;
            case DAMAGED -> 40d;
            case BROKEN -> 0d;
        };
        AtomicReference<EquipmentCondition> conditionRef = new AtomicReference<>(condition);
        AtomicDouble durability = new AtomicDouble(initialDurability);
        AtomicDouble batteryDurability = new AtomicDouble(10);
        Pair<Miner, Object> mocks = getMocks(conditionRef, durability, batteryDurability);
        Miner minerMock = mocks.getFirst();
        Object instance = mocks.getSecond();

        call(() -> POWERBANK_USE_METHOD_LINK.get().invoke(instance, minerMock));
        if (condition != EquipmentCondition.BROKEN) {
            assertEquals(100, batteryDurability.intValue(), emptyContext(),
                r -> "Powerbank.use(Miner) did not charge the Miner's battery completely");
            assertEquals(Math.max(0d, initialDurability - 50d), durability.get(), emptyContext(),
                r -> "The Powerbank's durability was not set to the correct value");
        } else {
            assertEquals(10, batteryDurability.intValue(), emptyContext(),
                r -> "A Powerbank with condition = BROKEN cannot recharge a battery");
        }
    }

    private Pair<Miner, Object> getMocks(AtomicReference<EquipmentCondition> condition, AtomicDouble durability, AtomicDouble batteryDurability) {
        Answer<?> batteryAnswer = invocation -> {
            Method invokedMethod = invocation.getMethod();
            if (TestUtils.methodSignatureEquals(invokedMethod, "getDurability")) {
                return batteryDurability.get();
            } else if (TestUtils.methodSignatureEquals(invokedMethod, "setDurability", double.class)) {
                batteryDurability.set(Math.max(0, Math.min(invocation.getArgument(0, Double.class), 100)));
                return null;
            } else {
                return invocation.callRealMethod();
            }
        };
        Battery batteryMock = Mockito.mock(Battery.class, Mockito.withSettings().useConstructor().defaultAnswer(batteryAnswer));
        Answer<?> minerAnswer = invocation -> {
            Method invokedMethod = invocation.getMethod();
            if (TestUtils.methodSignatureEquals(invokedMethod, "getBattery")) {
                return batteryMock;
            } else {
                return Mockito.RETURNS_DEFAULTS.answer(invocation);
            }
        };
        Miner minerMock = Mockito.mock(Miner.class, minerAnswer);
        Answer<?> powerbankAnswer = invocation -> {
            Method invokedMethod = invocation.getMethod();
            if (TestUtils.methodSignatureEquals(invokedMethod, "getCondition")) {
                return condition.get();
            } else if (TestUtils.methodSignatureEquals(invokedMethod, "getDurability")) {
                return durability.get();
            } else if (TestUtils.methodSignatureEquals(invokedMethod, "setDurability", double.class)) {
                durability.set(Math.max(0, Math.min(invocation.getArgument(0, Double.class), 100)));
                return null;
            } else if (TestUtils.methodSignatureEquals(invokedMethod, "reduceDurability", double.class)) {
                durability.set(Math.max(0, Math.min(durability.get() - invocation.getArgument(0, Double.class), 100)));
                return null;
            } else {
                return invocation.callRealMethod();
            }
        };
        Object powerbankMock;
        try {
            powerbankMock = Mockito.mock(POWERBANK_TYPE_LINK.get().reflection(),
                Mockito.withSettings().useConstructor(100d).defaultAnswer(powerbankAnswer));
        } catch (MockitoException e) {
            throw (RuntimeException) e.getCause();
        }

        return new Pair<>(minerMock, powerbankMock);
    }
}
