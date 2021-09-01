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
                makeTable(connection, objectData);
            }
            /*// Это в целом не нужно
            Class<?> fld = field.getType();
            System.out.println("Class name : " + field.getName());
            System.out.println("Class type : " + fld.getName());*/
        }
    }

    @Override
    public <T> void update(User user) {  // public <T> void update(T objectData)
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE User SET name=?, age=? WHERE id=?");

            preparedStatement.setLong(1, user.getID());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setInt(3, user.getAge());

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
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

            user = new User(); // ТАк не работает

            user.setId(resultSet.getLong(1));
            user.setName(resultSet.getString(2));
            user.setAge(resultSet.getInt(3));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return user;
    }

    private void makeTable(Connection connection, User user) {
        // Перекинул из JdbcTemplateDemo
        try (PreparedStatement preparedStatement = connection.prepareStatement("create table User" +
                "(id bigint(20) NOT NULL auto_increment, name varchar(255), age int(3))")) {

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

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
