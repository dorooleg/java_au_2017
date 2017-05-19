package ru.spbau.mit;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Oleg on 5/18/2017.
 */
public class SimpleImplementor implements Implementor {
    private String outputDirectory;

    public SimpleImplementor(String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    @Override
    public String implementFromDirectory(String directoryPath, String className) throws ImplementorException {
        Class<?> clazz = loadClass(directoryPath, className);
        writeClass(clazz, clazz.getPackage());
        return clazz.getCanonicalName() + "Impl";
    }

    @Override
    public String implementFromStandardLibrary(String className) throws ImplementorException {
        Class<?> clazz = loadClass(className);
        writeClass(clazz, null);
        return clazz.getSimpleName() + "Impl";
    }

    private void writeClass(Class<?> clazz, Package classPackage) throws ImplementorException {
        StringBuilder text = new StringBuilder();
        text.append(createPackage(classPackage));
        text.append(createClass(clazz));
        String packageName = classPackage != null ? classPackage.getName() : "";
        File directory = Paths.get(outputDirectory, packageName.split("\\.")).toFile();
        File file = new File(directory, clazz.getSimpleName() + "Impl.java");
        directory.mkdirs();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.append(text);
        } catch (IOException e) {
            throw new ImplementorException("Can't write to file", e);
        }
    }

    private Class<?> loadClass(String directoryPath, String className) throws ImplementorException {
        try {
            File file = new File(directoryPath);
            URL url = file.toURI().toURL();
            URL[] urls = new URL[]{url};
            ClassLoader classLoader = new URLClassLoader(urls);
            return classLoader.loadClass(className);
        } catch (MalformedURLException | ClassNotFoundException e) {
            throw new ImplementorException(className + ": load error", e);
        }
    }

    private Class<?> loadClass(String className) throws ImplementorException {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new ImplementorException(className + ": load error", e);
        }
    }

    private StringBuilder createPackage(Package classPackage) {
        StringBuilder text = new StringBuilder();
        if (classPackage != null && !classPackage.getName().isEmpty()) {
            text.append(classPackage).append(";" + System.lineSeparator());
        }
        return text;
    }

    private StringBuilder createClass(Class<?> clazz) throws ImplementorException {
        StringBuilder text = new StringBuilder();
        text.append(createClassDeclaration(clazz));
        text.append(" {");
        text.append(createMethods(clazz));
        text.append("}");
        return text;
    }

    private StringBuilder createClassDeclaration(Class<?> clazz) throws ImplementorException {
        StringBuilder text = new StringBuilder();
        int newModifiers = clazz.getModifiers() & ~Modifier.ABSTRACT & ~Modifier.INTERFACE;
        text.append(Modifier.toString(newModifiers));
        text.append(" class ").append(clazz.getSimpleName()).append("Impl");
        if (clazz.isInterface()) {
            text.append(" implements ").append(clazz.getCanonicalName());
        } else if (Modifier.isAbstract(clazz.getModifiers())) {
            text.append(" extends ").append(clazz.getCanonicalName());
        } else {
            throw new ImplementorException("Class must be is abstract or interface");
        }
        return text;
    }

    private StringBuilder createMethods(Class<?> clazz) {
        StringBuilder text = new StringBuilder();
        Set<StringBuilder> set = new HashSet<>();
        while (clazz != null) {

            for (Method method : clazz.getDeclaredMethods()) {
                if (Modifier.isAbstract(method.getModifiers())) {
                    set.add(createMethod(method));
                }
            }

            for (Class<?> interfaceClass : clazz.getInterfaces()) {
                for (Method method : interfaceClass.getDeclaredMethods()) {
                    set.add(createMethod(method));
                }
            }

            clazz = clazz.getSuperclass();
        }
        for (StringBuilder method : set) {
            text.append(method);
        }
        return text;
    }

    private StringBuilder createMethod(Method method) {
        StringBuilder text = new StringBuilder();
        text.append(createMethodDeclaration(method));
        text.append(createMethodImplementation(method));
        return text;
    }

    private StringBuilder createMethodDeclaration(Method method) {
        StringBuilder text = new StringBuilder();
        int newModifiers = method.getModifiers() & ~Modifier.ABSTRACT;
        text
                .append(System.lineSeparator())
                .append(Modifier.toString(newModifiers))
                .append(" ")
                .append(method.getReturnType().getCanonicalName())
                .append(method.getName())
                .append("(");
        for (int i = 0; i < method.getParameterTypes().length; i++) {
            if (i != 0) {
                text.append(", ");
            }
            text.append(method.getParameterTypes()[i].getCanonicalName() + " p" + i);
        }
        return text;
    }

    private StringBuilder createMethodImplementation(Method method) {
        StringBuilder text = new StringBuilder();
        final Class<?> returnType = method.getReturnType();
        text.append(" {" + System.lineSeparator());
        if (returnType.getName().equals("boolean")) {
            text.append("return false;");
        } else if (returnType.isPrimitive()) {
            text.append("return 0;");
        } else if (!returnType.getName().equals("void")) {
            text.append("return null;");
        }
        text.append(System.lineSeparator() + "}" + System.lineSeparator());
        return text;
    }
}
