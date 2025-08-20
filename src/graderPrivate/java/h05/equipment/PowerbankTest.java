package h05.equipment;

import com.google.common.util.concurrent.AtomicDouble;
import h05.Utils;
import h05.entity.Miner;
import kotlin.Pair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.match.Matcher;
import org.tudalgo.algoutils.tutor.general.reflections.*;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@TestForSubmission
public class PowerbankTest {

    private static final Supplier<TypeLink> POWERBANK_TYPE_LINK = () ->
        BasicPackageLink.of("h05.equipment").getType(Matcher.of(typeLink -> typeLink.name().equals("Powerbank")));
    private static final Supplier<ConstructorLink> POWERBANK_CONSTRUCTOR_LINK = () ->
        POWERBANK_TYPE_LINK.get().getConstructor(Matcher.of(constructorLink ->
            constructorLink.typeList().equals(List.of(BasicTypeLink.of(double.class)))));
    private static final Supplier<FieldLink> POWERBANK_CAPACITY_FIELD_LINK = () ->
        POWERBANK_TYPE_LINK.get().getField(Matcher.of(fieldLink -> fieldLink.name().equals("capacity")));
    private static final Supplier<MethodLink> POWERBANK_GET_CAPACITY_METHOD_LINK = () ->
        POWERBANK_TYPE_LINK.get().getMethod(Matcher.of(methodLink ->
            methodLink.name().equals("getCapacity") && methodLink.typeList().isEmpty()));
    private static final Supplier<MethodLink> POWERBANK_USE_METHOD_LINK = () ->
        POWERBANK_TYPE_LINK.get().getMethod(Matcher.of(methodLink ->
            methodLink.name().equals("use") && methodLink.typeList().equals(List.of(BasicTypeLink.of(Miner.class)))));

    @Test
    public void testDefinition() {
        TypeLink type = POWERBANK_TYPE_LINK.get();
        assertNotNull(type, emptyContext(), r -> "Could not find class h05.equipment.Powerbank");
        assertTrue((type.modifiers() & Modifier.ABSTRACT) == 0, emptyContext(),
            r -> "Class Powerbank is abstract");
        assertTrue(type.interfaces().contains(BasicTypeLink.of(UsableEquipment.class)), emptyContext(),
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

        List<Method> actualMethods = List.of(type.reflection().getMethods());
        for (Method expectedMethod : UsableEquipment.class.getMethods()) {
            String expectedName = expectedMethod.getName();
            Class<?>[] expectedParameterTypes = expectedMethod.getParameterTypes();
            assertTrue(actualMethods.stream().anyMatch(method -> Utils.methodSignatureEquals(method, expectedName, expectedParameterTypes)),
                emptyContext(),
                r -> "Class Powerbank does not have method %s(%s)"
                    .formatted(expectedName, Arrays.stream(expectedParameterTypes).map(Class::toString).collect(Collectors.joining(", "))));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1, 0, 1})
    public void testConstructor(double capacity) throws Throwable {
        ConstructorLink constructor = POWERBANK_CONSTRUCTOR_LINK.get();
        FieldLink capacityField = POWERBANK_CAPACITY_FIELD_LINK.get();
        Object instance = constructor.invoke(capacity);

        assertEquals(capacity, capacityField.get(instance), contextBuilder().add("Constructor arg 1", capacity).build(),
            r -> "Powerbank's constructor did not set field 'capacity' to the correct value");
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1, 0, 1})
    public void testGetCapacity(double capacity) throws Throwable {
        ConstructorLink constructor = POWERBANK_CONSTRUCTOR_LINK.get();
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
            if (Utils.methodSignatureEquals(invokedMethod, "getDurability")) {
                return batteryDurability.get();
            } else if (Utils.methodSignatureEquals(invokedMethod, "setDurability", double.class)) {
                batteryDurability.set(Math.max(0, Math.min(invocation.getArgument(0, Double.class), 100)));
                return null;
            } else {
                return invocation.callRealMethod();
            }
        };
        Battery batteryMock = Mockito.mock(Battery.class, batteryAnswer);
        Answer<?> minerAnswer = invocation -> {
            Method invokedMethod = invocation.getMethod();
            if (Utils.methodSignatureEquals(invokedMethod, "getBattery")) {
                return batteryMock;
            } else {
                return Mockito.RETURNS_DEFAULTS.answer(invocation);
            }
        };
        Miner minerMock = Mockito.mock(Miner.class, minerAnswer);
        Answer<?> powerbankAnswer = invocation -> {
            Method invokedMethod = invocation.getMethod();
            if (Utils.methodSignatureEquals(invokedMethod, "getCondition")) {
                return condition.get();
            } else if (Utils.methodSignatureEquals(invokedMethod, "getDurability")) {
                return durability.get();
            } else if (Utils.methodSignatureEquals(invokedMethod, "setDurability", double.class)) {
                durability.set(Math.max(0, Math.min(invocation.getArgument(0, Double.class), 100)));
                return null;
            } else if (Utils.methodSignatureEquals(invokedMethod, "reduceDurability", double.class)) {
                durability.set(Math.max(0, Math.min(durability.get() - invocation.getArgument(0, Double.class), 100)));
                return null;
            } else {
                return invocation.callRealMethod();
            }
        };
        Object powerbankMock = Mockito.mock(POWERBANK_TYPE_LINK.get().reflection(),
            Mockito.withSettings().useConstructor(100d).defaultAnswer(powerbankAnswer));

        return new Pair<>(minerMock, powerbankMock);
    }
}
