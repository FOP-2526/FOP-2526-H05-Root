package h05.mineable;

import com.google.common.util.concurrent.AtomicDouble;
import h05.TestUtils;
import h05.equipment.Tool;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Context;

import java.lang.reflect.Method;
import java.util.Optional;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@TestForSubmission
public class RockTest {

    @Test
    public void testGetName() throws NoSuchMethodException {
        Method getNameMethod = Rock.class.getMethod("getName");
        Rock instance = new Rock();
        assertCallEquals("Rock", () -> getNameMethod.invoke(instance), emptyContext(),
            r -> "Rock.getName() did not return the correct value");
    }

    @ParameterizedTest
    @ArgumentsSource(TestUtils.GetProgressArgumentsProvider.class)
    public void testGetProgress(double durability, MiningProgress expected) {
        Context context = contextBuilder().add("durability", durability).build();
        Answer<?> answer = invocation -> {
            if (TestUtils.methodSignatureEquals(invocation.getMethod(), "getDurability")) {
                return durability;
            } else {
                return invocation.callRealMethod();
            }
        };
        Rock rockMock = Mockito.mock(Rock.class, Mockito.withSettings().useConstructor().defaultAnswer(answer));

        assertEquals(expected, rockMock.getProgress(), context,
            r -> "Rock.getProgress() did not return the correct value");
    }

    @ParameterizedTest
    @ArgumentsSource(TestUtils.OnMinedArgumentsProvider.class)
    public void testOnMined(TestUtils.ToolClass toolClass, Optional<? extends Tool> tool) {
        double expectedReduction = switch (toolClass) {
            case NONE -> 5d;
            case AXE -> 1.5d;
            case PICKAXE -> 2d;
        };
        AtomicDouble durability = new  AtomicDouble(100);
        Answer<?> answer = invocation -> {
            Method invokedMethod = invocation.getMethod();
            if (TestUtils.methodSignatureEquals(invokedMethod, "getDurability")) {
                return durability.get();
            } else if (TestUtils.methodSignatureEquals(invokedMethod, "setDurability", double.class)) {
                durability.set(Math.max(0, Math.min(invocation.getArgument(0, Double.class), 100)));
                return null;
            } else if (TestUtils.methodSignatureEquals(invokedMethod, "reduceDurability", double.class)) {
                durability.set(Math.max(0, durability.get() - invocation.getArgument(0, Double.class)));
                return null;
            } else {
                return invocation.callRealMethod();
            }
        };
        Rock rockMock = Mockito.mock(Rock.class, Mockito.withSettings().useConstructor().defaultAnswer(answer));

        Context context = contextBuilder()
            .add("durability", durability.get())
            .add("tool", toolClass)
            .add("tool mining power", 1d)
            .build();
        assertFalse(rockMock.onMined(toolClass == TestUtils.ToolClass.NONE ? null : tool.orElseThrow()), context,
            r -> "The resource was not mined completely but Rock.onMined(Tool) returned true");
        assertEquals(100d - expectedReduction, durability.get(), context,
            r -> "Rock.onMine(Tool) did not reduce the durability by the correct amount");

        durability.set(1d);
        context = contextBuilder()
            .add("durability", durability.get())
            .add("tool", toolClass)
            .add("tool mining power", 1d)
            .build();
        assertTrue(rockMock.onMined(toolClass == TestUtils.ToolClass.NONE ? null : tool.orElseThrow()), context,
            r -> "The resource was mined completely but Rock.onMined(Tool) returned false");
        assertEquals(0d, durability.get(), context,
            r -> "Rock.onMine(Tool) did not reduce the durability to zero");
    }
}
