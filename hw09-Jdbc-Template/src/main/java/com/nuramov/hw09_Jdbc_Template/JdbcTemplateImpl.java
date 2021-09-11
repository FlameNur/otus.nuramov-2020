package com.nuramov.hw09_Jdbc_Template;

import com.nuramov.hw09_Jdbc_Template.Annotations.id;

import java.lang.reflect.Field;
import java.sql.*;

public class JdbcTemplateImpl<T> implements JdbcTemplate {
    private static long count = 0;
    private final Connection connection;

    public JdbcTemplateImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public <T> void create(User objectData) { // public <T> void create(T objectData)
        // Определяем поля класса
        Class<?> cls = objectData.getClass();

        Field[] fields = cls.getDeclaredFields();
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

                createTable(connection);
                intoTable(connection, objectData);
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
    public void insertRecord(User objectData) {
        Class<?> cls = objectData.getClass();

        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            if(field.isAnnotationPresent(id.class)) {
                intoTable(connection, objectData);
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
     * Метод createTable() создает таблицу "User" в базе данных H2 c полями:
     * id bigint(20) NOT NULL auto_increment
     * name varchar(255)
     * age int(3)
     *
     * @param connection
     */
    private void createTable(Connection connection) {
        // Перекинул из JdbcTemplateDemo
        try (PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE User" +
                "(id bigint(20) NOT NULL auto_increment, name varchar(255), age int(3))")) {

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод intoTable добаавляет объекты в таблицу
     * @param connection
     * @param user
     */
    private void intoTable(Connection connection, User user) {
        if(user.getID() > 0) {
            System.out.println("Такая строка уже существует");
        } else {
            long id = ++count;

            try(PreparedStatement preparedStatement =
                        connection.prepareStatement("INSERT INTO User(id, name, age) VALUES(?, ?, ?)")) {

                user.setId(id);
                preparedStatement.setLong(1, id);
                preparedStatement.setString(2, user.getName());
                preparedStatement.setInt(3, user.getAge());

                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
