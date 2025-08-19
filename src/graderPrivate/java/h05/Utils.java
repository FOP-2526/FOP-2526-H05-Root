package h05;

import java.lang.reflect.Method;
import java.util.Arrays;

public class Utils {

    public static boolean methodSignatureEquals(Method method, String methodName, Class<?>... parameterTypes) {
        return method.getName().equals(methodName) && Arrays.equals(method.getParameterTypes(), parameterTypes);
    }
}
