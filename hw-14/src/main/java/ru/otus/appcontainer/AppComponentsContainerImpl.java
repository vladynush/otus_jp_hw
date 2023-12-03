package ru.otus.appcontainer;

import org.reflections.Reflections;
import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    private final Map<Class, Object> createdObjects;
    private final Map<Class, Integer> orderClasses;

    record NamesBean(String nameMethod, String nameReturnParam) {
    }

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        createdObjects = new HashMap<>();
        orderClasses = new HashMap<>();
        processConfig(initialConfigClass);
    }

    public AppComponentsContainerImpl(Class<?> initialConfigClass1, Class<?> initialConfigClass2) {
        createdObjects = new HashMap<>();
        orderClasses = new HashMap<>();
        processConfig(initialConfigClass1);
        processConfig(initialConfigClass2);
        sortAppComponents();
    }

    public AppComponentsContainerImpl(String packageName) {
        createdObjects = new HashMap<>();
        orderClasses = new HashMap<>();
        Set<Class<?>> classes = new Reflections(packageName).getTypesAnnotatedWith(AppComponentsContainerConfig.class);
        if (classes.isEmpty()) {
            throw new RuntimeException();
        }
        for (Class cl : classes
        ) {
            processConfig(cl);
        }
        sortAppComponents();
    }

    private void sortAppComponents() {
        appComponents.clear();
        orderClasses.entrySet().stream().sorted(Map.Entry.comparingByValue()).forEach(en -> {
            appComponents.add(en.getKey());
        });
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);
        Map<NamesBean, Integer> mapWirhOrders = new HashMap<>();
        Map<String, String> mapWirhOrdersForNames = new HashMap<>();
        Method[] methods = configClass.getDeclaredMethods();
        for (Method method : methods
        ) {
            AppComponent an = method.getAnnotation(AppComponent.class);
            if (an != null) {
                for (Map.Entry<String, String> en : mapWirhOrdersForNames.entrySet()
                ) {
                    if (en.getValue().equals(an.name())) {
                        throw new RuntimeException();
                    }
                }
                NamesBean nb = new NamesBean(an.name(), method.getReturnType().getSimpleName());
                mapWirhOrders.put(nb, an.order());
                mapWirhOrdersForNames.put(method.getReturnType().getSimpleName(), an.name());
            }
        }
        mapWirhOrders.entrySet().stream().sorted(Map.Entry.<NamesBean, Integer>comparingByValue()).forEach(en -> {
            try {
                String packName = configClass.getPackageName();
                if (packName.equals("ru.otus")) {
                    packName += ".config";
                }
                Class clInt = Class.forName(packName.replace("config", "services") + "." + en.getKey().nameReturnParam);
                Set<Class> classes = new Reflections("ru.otus").getSubTypesOf(clInt);
                if (classes.isEmpty()) {
                    throw new RuntimeException();
                }
                Class cl = classes.iterator().next();
                appComponents.add(cl);
                appComponentsByName.put(mapWirhOrdersForNames.get(en.getKey().nameReturnParam), cl);
                orderClasses.put(cl, en.getValue());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        C obj = null;
        boolean isObj = false;
        for (Object component : appComponents
        ) {
            C createObj = (C) createObject((Class) component);
            if (component == componentClass) {
                if (isObj) {
                    throw new RuntimeException();
                }
                obj = createObj;
                isObj = true;
            } else if ((componentClass).isAssignableFrom((Class<?>) component)) {
                if (isObj) {
                    throw new RuntimeException();
                }
                obj = createObj;
                isObj = true;
            }
        }
        if (!isObj) {
            throw new RuntimeException();
        }
        return obj;
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        C obj = null;
        boolean isObj = false;
        for (Object component : appComponents
        ) {
            C createObj = (C) createObject((Class) component);
            for (Map.Entry<String, Object> en : appComponentsByName.entrySet()
            ) {
                if (en.getKey().equals(componentName) && component == en.getValue()) {
                    if (isObj) {
                        throw new RuntimeException();
                    }
                    isObj = true;
                    obj = createObj;
                }
            }
        }
        if (!isObj) {
            throw new RuntimeException();
        }
        return obj;
    }

    private <C> Object createObject(Class<C> componentClass) {
        List<Object> paramsForCreate = new ArrayList<>();
        Constructor[] constructors = componentClass.getDeclaredConstructors();
        if (constructors.length == 0) {
            return null;
        }
        Class[] params = constructors[0].getParameterTypes();
        if (params.length == 0) {
            try {
                C obj = (C) constructors[0].newInstance();
                createdObjects.put(componentClass, obj);
                return obj;
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        } else {
            for (Class param : params) {
                if (param == PrintStream.class) {
                    paramsForCreate.add(System.out);
                } else if (param == InputStream.class) {
                    paramsForCreate.add(System.in);
                } else {
                    Set<Class> classes = new Reflections("ru.otus").getSubTypesOf(param);
                    if (classes.isEmpty()) {
                        throw new RuntimeException();
                    }
                    Class cl = classes.iterator().next();
                    for (Map.Entry<Class, Object> paramObj : createdObjects.entrySet()) {
                        if (paramObj.getKey() == cl) {
                            paramsForCreate.add(paramObj.getValue());
                            break;
                        }
                    }
                }
            }
            try {
                C obj = (C) constructors[0].newInstance(paramsForCreate.toArray());
                createdObjects.put(componentClass, obj);
                return obj;
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
