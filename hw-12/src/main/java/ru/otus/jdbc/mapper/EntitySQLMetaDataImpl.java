package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.List;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {
    private EntityClassMetaData entityMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData entityMetaData) {
        this.entityMetaData = entityMetaData;
    }

    @Override
    public String getSelectAllSql() {
        StringBuilder fieldsName = new StringBuilder();
        boolean flFirst = false;
        for (Field field : (List<Field>) entityMetaData.getAllFields()
        ) {
            fieldsName.append(flFirst ? ", " : "").append(field.getName());
            flFirst = true;
        }
        return "select " + fieldsName + " from " + entityMetaData.getName();
    }

    @Override
    public String getSelectByIdSql() {
        StringBuilder fieldsName = new StringBuilder();
        boolean flFirst = false;
        for (Field field : (List<Field>) entityMetaData.getAllFields()
        ) {
            fieldsName.append(flFirst ? ", " : "").append(field.getName());
            flFirst = true;
        }
        return "select " + fieldsName + " from " + entityMetaData.getName() + " where " + entityMetaData.getIdField().getName() + " = ?";
    }

    @Override
    public String getInsertSql() {
        StringBuilder fieldsName = new StringBuilder();
        boolean flFirst = false;
        for (Field field : (List<Field>) entityMetaData.getFieldsWithoutId()
        ) {
            fieldsName.append(flFirst ? ", " : "").append(field.getName());
            flFirst = true;
        }
        fieldsName = new StringBuilder("insert into " + entityMetaData.getName() + "(").append(fieldsName).append(") values (");
        flFirst = false;
        for (int i = 0; i < entityMetaData.getFieldsWithoutId().size(); i++
        ) {
            fieldsName.append(flFirst == true ? ",?" : "?");
            flFirst = true;
        }
        return fieldsName + ")";
    }

    @Override
    public String getUpdateSql() {
        StringBuilder fieldsName = new StringBuilder();
        for (Field field : (List<Field>) entityMetaData.getFieldsWithoutId()
        ) {
            fieldsName.append(field.getName()).append(" = ? ");
        }
        return "update " + entityMetaData.getName() + " set " + fieldsName + " where " + entityMetaData.getIdField().getName() + " = ?";
    }

    @Override
    public EntityClassMetaData getEntityClassMetaData() {
        return entityMetaData;
    }

}
