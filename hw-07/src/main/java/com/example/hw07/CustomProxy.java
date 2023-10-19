package com.example.hw07;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomProxy {

    private CustomProxy() {
    }

    static LoggingInterface createInstance() {
        Handler handler = new Handler(new LoggingService());
        return (LoggingInterface) Proxy.newProxyInstance(
                LoggingInterface.class.getClassLoader(),
                new Class[]{LoggingInterface.class},
                handler);

    }

    static class Handler implements InvocationHandler {
        LoggingInterface original;
        Map<List<Class<?>>, Method> originalMethods;

        public Handler(LoggingInterface original) {
            this.original = original;
            this.originalMethods = new HashMap<>();
            Arrays.stream(original.getClass().getDeclaredMethods()).forEach(m ->
                    this.originalMethods.put(Arrays.asList(m.getParameterTypes()), m)
            );
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Method originalMethod = originalMethods.get(Arrays.asList(method.getParameterTypes()));
            if (originalMethod != null && originalMethod.isAnnotationPresent(Log.class)) {
                System.out.println(buildLogString(method, args));
            }
            method.invoke(original, args);
            return null;
        }

        private String buildLogString(Method method, Object[] args) {
            if (Array.get(args, 0) instanceof Object[] arr) {
                return "Method " + method.getName() + " with " + "param(s): " +
                        Arrays.toString(arr);
            } else {
                return "Method " + method.getName() + " with " + "param(s): "
                        + Arrays.toString(args);
            }
        }
    }

}
