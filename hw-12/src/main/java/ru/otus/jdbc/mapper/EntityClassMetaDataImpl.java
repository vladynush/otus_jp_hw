package ru.otus.jdbc.mapper;

import ru.otus.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EntityClassMetaDataImpl implements EntityClassMetaData {
    private Class className;

    public EntityClassMetaDataImpl(Class className) {
        this.className = className;
    }

    @Override
    public String getName() {
        return this.className.getSimpleName().toLowerCase();
    }

    @Override
    public Constructor getConstructor() {
        try {
            return className.getAnnotation(Id.class) != null ?
                    className.getConstructor(Long.class, String.class) : className.getConstructor(Long.class, String.class, String.class);
        } catch (RuntimeException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Field getIdField() {
        try {
            return className.getDeclaredAnnotation(Id.class) != null ? className.getDeclaredField("id") : className.getDeclaredField("no");
        } catch (RuntimeException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Field> getAllFields() {
        return new ArrayList<>(Arrays.asList(className.getDeclaredFields()));
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return List.of(className.getDeclaredFields()).stream().filter(el -> (!el.getName().equals("id") && !el.getName().equals("no"))).toList();
    }

    public Class getClassName() {
        return className;
    }
}
