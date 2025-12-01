package h05.equipment;

import h05.TestUtils;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.reflections.*;

import java.lang.reflect.Modifier;

import static h05.Links.*;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@TestForSubmission
public class AxeTest {

    @Test
    public void testDefinition() {
        TypeLink type = AXE_TYPE_LINK.get();
        assertNotNull(type, emptyContext(), r -> "Could not find class h05.equipment.Axe");
        assertTrue((type.modifiers() & Modifier.ABSTRACT) == 0, emptyContext(),
            r -> "Class Axe is abstract");
        assertTrue(TestUtils.getInterfaces(type.reflection()).contains(Tool.class), emptyContext(),
            r -> "Class Axe does not implement interface Tool");

        ConstructorLink constructor =  AXE_CONSTRUCTOR_LINK.get();
        assertNotNull(constructor, emptyContext(), r -> "Class Axe has no constructor Axe()");

        TestUtils.testPublicMethodsExist(type.name(), Tool.class.getMethods(), type.reflection().getMethods());
    }

    @Test
    public void testGetMiningPower() throws Throwable {
        Object instance = AXE_CONSTRUCTOR_LINK.get().invoke();
        assertEquals(5d, AXE_GET_MINING_POWER_METHOD_LINK.get().invoke(instance), emptyContext(),
            r -> "Axe.getMiningPower() did not return the correct value");
    }
}
