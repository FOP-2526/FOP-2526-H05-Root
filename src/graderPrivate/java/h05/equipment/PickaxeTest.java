package h05.equipment;

import h05.TestUtils;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.reflections.BasicTypeLink;
import org.tudalgo.algoutils.tutor.general.reflections.ConstructorLink;
import org.tudalgo.algoutils.tutor.general.reflections.TypeLink;

import java.lang.reflect.Modifier;

import static h05.Links.*;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@TestForSubmission
public class PickaxeTest {

    @Test
    public void testDefinition() {
        TypeLink type = PICKAXE_TYPE_LINK.get();
        assertNotNull(type, emptyContext(), r -> "Could not find class h05.equipment.Pickaxe");
        assertTrue((type.modifiers() & Modifier.ABSTRACT) == 0, emptyContext(),
            r -> "Class Pickaxe is abstract");
        assertTrue(type.interfaces().contains(BasicTypeLink.of(Tool.class)), emptyContext(),
            r -> "Class Pickaxe does not implement interface Tool");

        ConstructorLink constructor =  PICKAXE_CONSTRUCTOR_LINK.get();
        assertNotNull(constructor, emptyContext(), r -> "Class Pickaxe has no constructor Pickaxe()");

        TestUtils.testPublicMethodsExist(type.name(), Tool.class.getMethods(), type.reflection().getMethods());
    }

    @Test
    public void testGetMiningPower() throws Throwable {
        Object instance = PICKAXE_CONSTRUCTOR_LINK.get().invoke();
        assertEquals(15d, PICKAXE_GET_MINING_POWER_METHOD_LINK.get().invoke(instance), emptyContext(),
            r -> "Pickaxe.getMiningPower() did not return the correct value");
    }
}
