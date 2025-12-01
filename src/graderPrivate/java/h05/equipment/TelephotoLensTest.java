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
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static h05.Links.*;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@TestForSubmission
public class TelephotoLensTest {

    @Test
    public void testDefinition() {
        TypeLink type = TELEPHOTO_LENS_TYPE_LINK.get();
        assertNotNull(type, emptyContext(), r -> "Could not find class h05.equipment.TelephotoLens");
        assertTrue((type.modifiers() & Modifier.ABSTRACT) == 0, emptyContext(),
            r -> "Class TelephotoLens is abstract");
        assertTrue(TestUtils.getInterfaces(type.reflection()).contains(UsableEquipment.class), emptyContext(),
            r -> "Class TelephotoLens does not implement interface UsableEquipment");

        ConstructorLink constructor = TELEPHOTO_LENS_CONSTRUCTOR_LINK.get();
        assertNotNull(constructor, emptyContext(),
            r -> "Class TelephotoLens does not have a constructor TelephotoLens(int)");

        FieldLink rangeEnhancementField = TELEPHOTO_LENS_RANGE_ENHANCEMENT_FIELD_LINK.get();
        assertNotNull(rangeEnhancementField, emptyContext(), r -> "Could not find field 'rangeEnhancement' in class TelephotoLens");
        assertTrue((rangeEnhancementField.modifiers() & Modifier.PRIVATE) != 0, emptyContext(),
            r -> "Field 'rangeEnhancement' in class TelephotoLens is not private");
        assertEquals(int.class, rangeEnhancementField.type().reflection(), emptyContext(),
            r -> "Field 'rangeEnhancement' in class TelephotoLens does not have type int");

        TestUtils.testPublicMethodsExist(type.name(), UsableEquipment.class.getMethods(), type.reflection().getMethods());
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 1})
    public void testConstructor(int rangeEnhancement) throws Throwable {
        ConstructorLink constructor = TELEPHOTO_LENS_CONSTRUCTOR_LINK.get();
        FieldLink rangeEnhancementField = TELEPHOTO_LENS_RANGE_ENHANCEMENT_FIELD_LINK.get();
        Object instance = constructor.invoke(rangeEnhancement);

        assertEquals(rangeEnhancement, rangeEnhancementField.get(instance), contextBuilder().add("Constructor arg 1", rangeEnhancement).build(),
            r -> "TelephotoLens' constructor did not set field 'rangeEnhancement' to the correct value");
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 1})
    public void testGetRangeEnhancement(int rangeEnhancement) throws Throwable {
        ConstructorLink constructor = TELEPHOTO_LENS_CONSTRUCTOR_LINK.get();
        MethodLink getRangeEnhancementMethod = TELEPHOTO_LENS_GET_RANGE_ENHANCEMENT_METHOD_LINK.get();
        Object instance = constructor.invoke(rangeEnhancement);

        assertEquals(rangeEnhancement, getRangeEnhancementMethod.invoke(instance), contextBuilder().add("Constructor arg 1", rangeEnhancement).build(),
            r -> "Method getRangeEnhancement() in class TelephotoLens did not return the correct value");
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
        AtomicInteger cameraVisibilityRange = new AtomicInteger(10);
        Pair<Miner, Object> mocks = getMocks(conditionRef, durability, cameraVisibilityRange);
        Miner minerMock = mocks.getFirst();
        Object instance = mocks.getSecond();

        call(() -> TELEPHOTO_LENS_USE_METHOD_LINK.get().invoke(instance, minerMock));
        if (condition != EquipmentCondition.BROKEN) {
            assertEquals(20, cameraVisibilityRange.get(), emptyContext(),
                r -> "TelephotoLens.use(Miner) did not enhance Miner's camera");
        } else {
            assertEquals(10, cameraVisibilityRange.get(), emptyContext(),
                r -> "A TelephotoLens with condition = BROKEN cannot enhance a camera");
        }
        assertEquals(0d, durability.get(), emptyContext(),
            r -> "The TelephotoLens' durability was not set to 0");
    }

    private Pair<Miner, Object> getMocks(AtomicReference<EquipmentCondition> condition, AtomicDouble durability, AtomicInteger cameraVisibilityRange) {
        Answer<?> cameraAnswer = invocation -> {
            Method invokedMethod = invocation.getMethod();
            if (TestUtils.methodSignatureEquals(invokedMethod, "getVisibilityRange")) {
                return cameraVisibilityRange.get();
            } else if (TestUtils.methodSignatureEquals(invokedMethod, "setVisibilityRange", int.class)) {
                cameraVisibilityRange.set(Math.max(1, invocation.getArgument(0, Integer.class)));
                return null;
            } else {
                return invocation.callRealMethod();
            }
        };
        Camera cameraMock = Mockito.mock(Camera.class, cameraAnswer);
        Answer<?> minerAnswer = invocation -> {
            Method invokedMethod = invocation.getMethod();
            if (TestUtils.methodSignatureEquals(invokedMethod, "getCamera")) {
                return cameraMock;
            } else {
                return Mockito.RETURNS_DEFAULTS.answer(invocation);
            }
        };
        Miner minerMock = Mockito.mock(Miner.class, minerAnswer);
        Answer<?> telephotoLensAnswer = invocation -> {
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
        Object telephotoLensMock;
        try {
            telephotoLensMock = Mockito.mock(TELEPHOTO_LENS_TYPE_LINK.get().reflection(),
                Mockito.withSettings().useConstructor(10).defaultAnswer(telephotoLensAnswer));
        } catch (MockitoException e) {
            throw new RuntimeException(e.getCause());
        }

        return new Pair<>(minerMock, telephotoLensMock);
    }
}
