package ru.otus.jdbc.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;

import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private static final Logger log = LoggerFactory.getLogger(DataTemplateJdbc.class);

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
    }

    private T createObject(ResultSet rs) {
        List<Object> objList = new ArrayList<>();
        Constructor<T> cons = entitySQLMetaData.getEntityClassMetaData().getConstructor();

        for (Field field : (List<Field>) entitySQLMetaData.getEntityClassMetaData().getAllFields()
        ) {
            if (field.getType() == String.class) {
                try {
                    objList.add(rs.getString(field.getName()));
                } catch (SQLException e) {
                    e.printStackTrace();
                    log.info("error findById " + e);
                }
            } else if (field.getType() == Long.class) {
                try {
                    objList.add(rs.getLong(field.getName()));
                } catch (SQLException e) {
                    e.printStackTrace();
                    log.info("error findById " + e);
                }
            }
        }

        Object newInstance;
        try {
            newInstance = cons.newInstance(objList.toArray());
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new RuntimeException(e);
        }
        return (T) newInstance;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {

        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), rs -> {
            try {
                if (rs.next()) {
                    return createObject(rs);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                log.info("error findById " + e);
            }
            return null;
        });
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectAllSql(), Collections.emptyList(), rs -> {
            var clientList = new ArrayList<T>();
            try {
                while (rs.next()) {
                    Object newInstance = createObject(rs);
                    clientList.add((T) newInstance);
                }
                return clientList;
            } catch (SQLException e) {
                e.printStackTrace();
                log.info("error findAll " + e);
            }
            return null;
        }).orElseThrow(() -> new RuntimeException("Unexpected error"));
    }

    @Override
    public long insert(Connection connection, T client) {
        try {
            List<Object> objList = new ArrayList<>();
            Method method = client.getClass().getDeclaredMethod("getFields");
            Map<String, Object> fieldsMap = (Map<String, Object>) method.invoke(client);
            for (Field field : (List<Field>) entitySQLMetaData.getEntityClassMetaData().getFieldsWithoutId()
            ) {
                if (fieldsMap.get(field.getName()) == null) {
                    if (field.getType() == String.class) {
                        objList.add("");
                    } else if (field.getType() == Long.class) {
                        objList.add(0);
                    }
                } else {
                    objList.add(fieldsMap.get(field.getName()));
                }
            }
            return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(),
                    objList);
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    @Override
    public void update(Connection connection, T client) {
        try {
            List<Object> objList = new ArrayList<>();
            Method method = client.getClass().getDeclaredMethod("getFields");
            Map<String, Object> fieldsMap = (Map<String, Object>) method.invoke(client);
            for (Field field : (List<Field>) entitySQLMetaData.getEntityClassMetaData().getFieldsWithoutId()
            ) {
                objList.add(fieldsMap.get(field.getName()));
            }
            dbExecutor.executeStatement(connection, entitySQLMetaData.getUpdateSql(),
                    objList);
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }
}
