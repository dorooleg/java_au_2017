package ru.spbau.mit;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;


public final class Injector {
    private static Set<Class<?>> visited;
    private static Map<Class<?>, Object> instances;

    private Injector() {
    }

    /**
     * Create and initialize object of `rootClassName` class using classes from
     * `implementationClassNames` for concrete dependencies.
     */
    public static Object initialize(String rootClassName, List<String> implementationClassNames) throws Exception {
        visited = new HashSet<>();
        instances = new LinkedHashMap<>();
        Class<?> clazz = Class.forName(rootClassName);
        Object result = createObject(clazz, implementationClassNames);
        visited = null;
        instances = null;
        return result;
    }

    private static Object createObject(Class<?> clazz, List<String> implementationClassNames) throws InjectionCycleException, IllegalAccessException, InvocationTargetException, InstantiationException, ImplementationNotFoundException, AmbiguousImplementationException {
        if (instances.containsKey(clazz)) {
            return instances.get(clazz);
        }

        if (visited.contains(clazz)) {
            throw new InjectionCycleException();
        }
        visited.add(clazz);
        Class[] parameterTypes = clazz.getConstructors()[0].getParameterTypes();
        ArrayList<Object> args = new ArrayList<>();
        for (Class param : parameterTypes) {
            args.add(implementationClassNames.contains(param.getCanonicalName())
                    ? createObject(param, implementationClassNames)
                    : findImplementation(param, implementationClassNames));
        }

        Constructor<?> constructor = clazz.getConstructors()[0];
        Object object = args.size() == 0 ? constructor.newInstance() : constructor.newInstance(args.toArray());
        visited.remove(clazz);
        instances.put(clazz, object);
        return object;

    }

    private static Object findImplementation(Class<?> clazz, List<String> implementationClassNames) throws ImplementationNotFoundException, AmbiguousImplementationException, InjectionCycleException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> resultClass = null;
        for (String className : implementationClassNames) {
            try {
                Class<?> implementationClass = Class.forName(className);
                if (clazz.isAssignableFrom(implementationClass)) {
                    if (resultClass != null) {
                        throw new AmbiguousImplementationException();
                    }

                    resultClass = implementationClass;
                }
            } catch (ClassNotFoundException e) {
                throw new ImplementationNotFoundException();
            }
        }

        if (resultClass == null) {
            throw new ImplementationNotFoundException();
        }

        return createObject(resultClass, implementationClassNames);
    }
}
