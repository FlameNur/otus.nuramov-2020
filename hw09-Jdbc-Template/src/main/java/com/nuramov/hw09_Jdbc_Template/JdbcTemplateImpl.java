package com.nuramov.hw09_Jdbc_Template;

import com.nuramov.hw09_Jdbc_Template.Annotations.id;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
    public <T> void update(long id, User user) {  // public <T> void update(T objectData)
        try(PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE USER SET name=?, age=? WHERE id=?")) {

            preparedStatement.setString(1, user.getName());
            preparedStatement.setInt(2, user.getAge());
            preparedStatement.setLong(3, id);

            int i = preparedStatement.executeUpdate();
            System.out.println("Количество изменненых строк: " + i);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User load(long id) { // public <T> T load(long id, Class<T> clazz)
        User user = null;

        try(PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM USER WHERE id=?")) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            user = new User();

            user.setId(resultSet.getLong(1));
            user.setName(resultSet.getString(2));
            user.setAge(resultSet.getInt(3));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
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
        Class<?> clazz = objectData.getClass();

        // Создаем таблицу с названием класса clazz.getSimpleName()
        try (PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE " +
                clazz.getSimpleName() + "(id bigint(20) NOT NULL auto_increment, name varchar(255), age int(3))")) {

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

                    // Не работает так, все id = 0. Потому что удалил строку где я через user.setId устанавливал id
                    id = (long) obj;
                    System.out.println(id);
                }
                field.setAccessible(false);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        // Каждая строка (экземпляр класса) имеет id > 0 при добавлении в таблицу
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

        // Определяем значение name и age
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
