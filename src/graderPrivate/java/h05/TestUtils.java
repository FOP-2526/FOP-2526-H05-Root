package h05;

import h05.mineable.MiningProgress;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.mockito.Mockito;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.stubbing.Answer;
import org.tudalgo.algoutils.tutor.general.reflections.TypeLink;

import java.awt.*;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertTrue;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.emptyContext;

public class TestUtils {

    public static boolean pointInWorld(int worldSize, Point point) {
        return point.x >= 0 && point.x < worldSize && point.y >= 0 && point.y < worldSize;
    }

    public static boolean methodSignatureEquals(Method method, String methodName, Class<?>... parameterTypes) {
        return method.getName().equals(methodName) && Arrays.equals(method.getParameterTypes(), parameterTypes);
    }

    public static void testPublicMethodsExist(String className, Method[] expectedMethods, Method[] actualMethods) {
        for (Method expectedMethod : expectedMethods) {
            String expectedName = expectedMethod.getName();
            Class<?>[] expectedParameterTypes = expectedMethod.getParameterTypes();
            assertTrue(Arrays.stream(actualMethods).anyMatch(method -> TestUtils.methodSignatureEquals(method, expectedName, expectedParameterTypes)),
                emptyContext(),
                r -> "Class %s does not have method %s(%s)"
                    .formatted(className, expectedName, Arrays.stream(expectedParameterTypes).map(Class::toString).collect(Collectors.joining(", "))));
        }
    }

    public static Set<Class<?>> getInterfaces(Class<?> clazz) {
        Set<Class<?>> interfaces = new HashSet<>();
        getInterfacesHelper(clazz, interfaces);
        return Collections.unmodifiableSet(interfaces);
    }

    private static void getInterfacesHelper(Class<?> clazz, Set<Class<?>> interfaces) {
        if (clazz == null || clazz == Object.class) return;
        for (Class<?> _interface : clazz.getInterfaces()) {
            interfaces.add(_interface);
            getInterfacesHelper(_interface, interfaces);
        }
        getInterfacesHelper(clazz.getSuperclass(), interfaces);
    }

    public static class GetProgressArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                Arguments.of(100d, MiningProgress.UNSTARTED),
                Arguments.of(50d, MiningProgress.IN_PROGRESS),
                Arguments.of(0d, MiningProgress.COMPLETED)
            );
        }
    }

    public enum ToolClass {
        NONE,
        AXE,
        PICKAXE
    }

    public static class OnMinedArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            Answer<?> axeAnswer = invocation -> {
                Method invokedMethod = invocation.getMethod();
                if (methodSignatureEquals(invokedMethod, "getName")) {
                    return "Axe";
                } else if (methodSignatureEquals(invokedMethod, "getMiningPower")) {
                    return 5d;
                } else {
                    return invocation.callRealMethod();
                }
            };
            Optional<?> axeMock;
            try {
                axeMock = Optional.ofNullable(Links.AXE_TYPE_LINK.get())
                    .map(TypeLink::reflection)
                    .map(clazz -> Mockito.mock(clazz,
                        Mockito.withSettings().useConstructor().defaultAnswer(axeAnswer)));
            } catch (MockitoException e) {
                throw new RuntimeException(e.getCause());
            }
            Answer<?> pickaxeAnswer = invocation -> {
                Method invokedMethod = invocation.getMethod();
                if (methodSignatureEquals(invokedMethod, "getName")) {
                    return "Pickaxe";
                } else if (methodSignatureEquals(invokedMethod, "getMiningPower")) {
                    return 15d;
                } else {
                    return invocation.callRealMethod();
                }
            };
            Optional<?> pickaxeMock;
            try {
                pickaxeMock = Optional.ofNullable(Links.PICKAXE_TYPE_LINK.get())
                    .map(TypeLink::reflection)
                    .map(clazz -> Mockito.mock(clazz,
                        Mockito.withSettings().useConstructor().defaultAnswer(pickaxeAnswer)));
            } catch (MockitoException e) {
                throw new RuntimeException(e.getCause());
            }

            return Stream.of(
                Arguments.of(ToolClass.NONE, Optional.empty()),
                Arguments.of(ToolClass.AXE, axeMock),
                Arguments.of(ToolClass.PICKAXE, pickaxeMock)
            );
        }
    }
}
