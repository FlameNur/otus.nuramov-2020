package com.nuramov.hw09_Jdbc_Template;

import com.nuramov.hw09_Jdbc_Template.Annotations.id;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.channels.ClosedSelectorException;
import java.sql.*;

public class JdbcTemplateImpl<T> implements JdbcTemplate {
    private static long count = 0;
    private final Connection connection;

    public JdbcTemplateImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public <T> void create(T objectData) {
        // Определяем поля класса
        Class<?> clazz = objectData.getClass();

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if(field.isAnnotationPresent(id.class)) {
                /*try {
                    // Проверяем наличие таблицы
                    DatabaseMetaData metadata = connection.getMetaData();
                    String[] types = {"TABLE"};
                    ResultSet resultSet = metadata.getTables(null, null, "%", types);

                    if(resultSet==null) createTable(connection);
                    intoTable(connection, objectData, count);
                } catch (SQLException e) {
                    e.printStackTrace();
                }*/

                createTable(connection, objectData);
                checkingRowsInTable(connection, objectData);
            }
        }
    }

    @Override
    public <T> void update(T objectData) {
        String fieldId = "";
        String fieldName = "";
        String fieldAge = "";
        long id = 0;
        String name = "";
        int age = 0;

        Class<?> clazz = objectData.getClass();
        // Определяем названия и значения полей для SQL запроса
        Field[] fields = clazz.getDeclaredFields();
        for(Field field : fields) {
            try {
                field.setAccessible(true);
                Object obj =  field.get(objectData);

                if(obj instanceof Long) {
                    fieldId = field.getName();
                    id = (Long) obj;
                }
                if(obj instanceof String) {
                    fieldName = field.getName();
                    name = (String) obj;
                }
                if(obj instanceof Integer) {
                    fieldAge = field.getName();
                    age = (Integer) obj;
                }

                field.setAccessible(false);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        try(PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE " + clazz.getSimpleName() + " SET " +
                            fieldName + "=?, " + fieldAge + "=? WHERE " + fieldId + "=?")) {

            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setLong(3, id);

            int i = preparedStatement.executeUpdate();
            System.out.println("Количество изменненых строк: " + i);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public <T> T load(long id, Class<T> clazz) {
        //Создаем экземпляр класс
        T objectData = null;
        try {
           objectData = clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException |
                NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }

        try(PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM " + clazz.getSimpleName() + " WHERE id=?")) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            Field[] fields = clazz.getDeclaredFields();
            for(Field field : fields) {
                try {
                    field.setAccessible(true);
                    Object objectField =  field.get(objectData);

                    if(objectField instanceof Long) {
                        //Устанавливаем значение id в поле полученного экземпляря класса
                        field.setLong(objectData, resultSet.getLong(1));
                    }
                    if(objectField instanceof String) {
                        //Устанавливаем значение name в поле полученного экземпляря класса
                        field.set(objectData, resultSet.getString(2));
                    }
                    if(objectField instanceof Integer) {
                        //Устанавливаем значение age в поле полученного экземпляря класса
                        field.setInt(objectData, resultSet.getInt(3));
                    }


                    /*//Устанавливаем значение name в поле полученного экземпляря класса
                    field.set(objectData, resultSet.getString(2));*/


                    field.setAccessible(false);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return objectData;
    }

    @Override
    public <T> void insertRecord(T objectData) {
        Class<?> cls = objectData.getClass();

        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            // Проверяем наличие аннотации, прежде чем добавить в таблицу
            if(field.isAnnotationPresent(id.class)) {
                checkingRowsInTable(connection, objectData);
            }
        }
    }

    @Override
    public void getDatabaseMetaData() {
        try {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            String[] types = {"TABLE"};
            ResultSet rs = databaseMetaData.getTables(null, null, "%", types);
            while (rs.next()) {
                System.out.println("Названия всех таблиц в базе даных:");
                System.out.println(rs.getString("TABLE_NAME"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод createTable создает таблицу с названием полученного класса в базе данных H2 c полями:
     * id bigint(20) NOT NULL auto_increment
     * name varchar(255)
     * age int(3)
     *
     * @param connection - текущее соединение connection
     * @param objectData - экземпляр класса, для которого создаем таблицу в БД
     */
    private <T> void createTable(Connection connection, T objectData) {
        String fieldId = "";
        String fieldName = "";
        String fieldAge = "";

        Class<?> clazz = objectData.getClass();
        // Определяем названия полей для SQL запроса
        Field[] fields = clazz.getDeclaredFields();
        for(Field field : fields) {
            try {
                field.setAccessible(true);
                Object obj =  field.get(objectData);

                if(obj instanceof Long) {
                    fieldId = field.getName();
                }
                if(obj instanceof String) {
                    fieldName = field.getName();
                }
                if(obj instanceof Integer) {
                    fieldAge = field.getName();
                }

                field.setAccessible(false);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE " +
                    clazz.getSimpleName() + "(" + fieldId + " bigint(20) NOT NULL auto_increment, " + fieldName +
                            " varchar(255), " + fieldAge + " int(3))")) {

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * В методе checkingRowsInTable проводится проверка возможности добавления нового объекта в таблицу
     * @param connection - текущее соединение connection
     * @param objectData - экземпляр класса, которого добавляем в таблицу БД
     */
    private <T> void checkingRowsInTable(Connection connection, T objectData) {
        long id = 0;
        Class<?> clazz = objectData.getClass();
        // Определяем значение id
        Field[] fields = clazz.getDeclaredFields();
        for(Field field : fields) {
            try {
                field.setAccessible(true);
                Object obj =  field.get(objectData);
                if(obj instanceof Long) {
                    id = (long) obj;
                }
                field.setAccessible(false);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        // Каждая строка (экземпляр класса) получае id > 0 при добавлении в таблицу
        // Ниже проводим проверку
        if(id > 0) {
            System.out.println("Такая строка уже существует");
        } else {
            id = ++count;
            // Добавляем строку в таблицу
            addRowToTable(connection, objectData, id);
        }
    }

    /**
     * Метод addRowToTable добавляет строки в таблицу БД
     * @param connection - текущее соединение connection
     * @param objectData - экземпляр класса, которого добавляем в таблицу БД
     */
    private <T> void addRowToTable(Connection connection, T objectData, long id) {
        String fieldId = "";
        String fieldName = "";
        String fieldAge = "";
        String name = "";
        int age = 0;

        Class<?> clazz = objectData.getClass();
        // Определяем имена всех полей, значения полей name и age, чтобы вставить в SQL запрос
        // Меняем значение поля id на новый, который установила БД
        Field[] fields = clazz.getDeclaredFields();
        for(Field field : fields) {
            try {
                field.setAccessible(true);
                Object obj =  field.get(objectData);

                if(obj instanceof Long) {
                    fieldId = field.getName();
                    // Устанавливаем значение id в поле полученного экземпляря класса
                    field.setLong(objectData, id);
                }
                if(obj instanceof String) {
                    fieldName = field.getName();
                    name = (String) obj;
                }
                if(obj instanceof Integer) {
                    fieldAge = field.getName();
                    age = (int) obj;
                }

                field.setAccessible(false);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }


        // Почему-то не работает с name??? Если заменить на fieldName, вылетает ошибка

        //String sql = "INSERT INTO ";
        //sql = sql.concat(clazz.getSimpleName()).concat("(").concat(fieldId).concat(", ").concat(fieldName).concat(", ").concat(fieldAge).concat(") VALUES(?, ?, ?)");


        try(PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO " + clazz.getSimpleName() +
                            "(" + fieldId + ", name, " + fieldAge + ") VALUES(?, ?, ?)")) {

            preparedStatement.setLong(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setInt(3, age);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
