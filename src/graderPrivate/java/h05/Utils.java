package h05;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertTrue;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.emptyContext;

public class Utils {

    public static boolean methodSignatureEquals(Method method, String methodName, Class<?>... parameterTypes) {
        return method.getName().equals(methodName) && Arrays.equals(method.getParameterTypes(), parameterTypes);
    }

    public static void testPublicMethodsExist(String className, Method[] expectedMethods, Method[] actualMethods) {
        for (Method expectedMethod : expectedMethods) {
            String expectedName = expectedMethod.getName();
            Class<?>[] expectedParameterTypes = expectedMethod.getParameterTypes();
            assertTrue(Arrays.stream(actualMethods).anyMatch(method -> Utils.methodSignatureEquals(method, expectedName, expectedParameterTypes)),
                emptyContext(),
                r -> "Class %s does not have method %s(%s)"
                    .formatted(className, expectedName, Arrays.stream(expectedParameterTypes).map(Class::toString).collect(Collectors.joining(", "))));
        }
    }
}
