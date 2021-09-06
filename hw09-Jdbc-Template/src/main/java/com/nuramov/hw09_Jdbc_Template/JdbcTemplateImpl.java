package com.nuramov.hw09_Jdbc_Template;

import com.nuramov.hw09_Jdbc_Template.Annotations.id;

import java.lang.reflect.Field;
import java.sql.*;

public class JdbcTemplateImpl<T> implements JdbcTemplate {
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
                    ResultSet resultSet = metadata.getTables(null, null, "User", null);
                    if(resultSet!=null){
                        System.out.println("Table exists");
                        intoTable(connection, objectData);
                    } else {
                        makeTable(connection, objectData);
                        intoTable(connection, objectData);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }*/


                createTable(connection);
                intoTable(connection, objectData);
            }
        }
    }

    @Override
    public <T> void update(User user) {  // public <T> void update(T objectData)
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE User SET name=?, age=? WHERE id=?");

            preparedStatement.setLong(1, user.getID());
            preparedStatement.setString(2, user.getName());
            System.out.println(user.getName());                                // Проверка имени

            preparedStatement.setInt(3, user.getAge());

            int i = preparedStatement.executeUpdate();
            System.out.println("Количество изменненых строк: " + i);          // Проверка количества изменненых строк
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User load(long id) { // public <T> T load(long id, Class<T> clazz)
        User user = null;

        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM User WHERE id=?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            System.out.println(resultSet.getString(2));               // Проверка имени

            user = new User();

            user.setId(resultSet.getLong(1));
            user.setName(resultSet.getString(2));
            user.setAge(resultSet.getInt(3));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
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
     *
     * @param connection
     * @param user
     */
    private void intoTable(Connection connection, User user) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO User VALUES(?, ?, ?)");

            preparedStatement.setLong(1, user.getID());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setInt(3, user.getAge());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
