package com.nuramov.hw09_Jdbc_Template;

import com.nuramov.hw09_Jdbc_Template.Annotations.id;
import com.nuramov.hw09_Jdbc_Template.Fields.FieldID;
import com.nuramov.hw09_Jdbc_Template.Fields.FieldsTypeAndValue;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.Map;
import java.util.StringJoiner;

/**
 * класс JdbcTemplateImpl позволяет работать с таблицей в БД
 */
public class JdbcTemplateImpl implements JdbcTemplate {
    private static String URL = "";
    private static long count = 0;
    private Connection connection;
    private final FieldsTypeAndValue fieldsTypeAndValue;
    private final FieldID fieldID;
    private final JdbcConnection jdbcConnection;

    public JdbcTemplateImpl(String URL) {
        JdbcTemplateImpl.URL = URL;
        fieldsTypeAndValue = new FieldsTypeAndValue();
        fieldID = new FieldID();
        jdbcConnection = new JdbcConnection();

        // Пока здесь сделал connection, т.к. отдельно пока не работает
        connection = jdbcConnection.getConnection(URL);
    }

    @Override
    public <T> void createTable(T objectData) {
        // Проверяем наличие поля с аннотацией @id
        boolean idState = fieldID.getAnnotatedID(objectData);
        if(!idState) return;

        // Проверяем наличие таблицы
        boolean tableState = getDatabaseMetaData(objectData);
        if(tableState) return;

        //Не работает почему-то
        createTable(connection, objectData);
    }

    @Override
    public <T> void create(T objectData) {
        Class<?> clazz = objectData.getClass();
        boolean idState = fieldID.getAnnotatedID(clazz);

        if(idState) {
            //createTable(connection, objectData);
            checkingRowsInTable(connection, objectData);
        }
    }

    @Override
    public <T> void update(T objectData) {
        // Пока так делаем и в конце метода закрываем
        //Connection connection = jdbcConnection.getConnection(URL);



        Class<?> clazz = objectData.getClass();
        String fieldIdName = fieldID.getIdName(objectData);
        Map<String, Object> fieldsNameAndValue = fieldsTypeAndValue.getFieldsNameAndValue(objectData);

        // Формируем перечисление имен полей "name=?, age=?" и т.д.
        StringJoiner fieldsName = new StringJoiner("=?, ");
        for(Map.Entry<String, Object> fld : fieldsNameAndValue.entrySet()) {
            fieldsName.add(fld.getKey());
        }
        String resultFieldsName = fieldsName + "=? ";

        // SQL: "UPDATE User SET name=?, age=? WHERE id=?"
        try(PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE " + clazz.getSimpleName() + " SET " +
                            resultFieldsName + "WHERE " + fieldIdName + "=?")) {

            // Устанавливаем значения полей по порядку в SQL запросе
            // 1: name = значение, 2: age = значение, 3: id = значение
            int count = 0;
            for(Map.Entry<String, Object> fld : fieldsNameAndValue.entrySet()) {
                count++;
                preparedStatement.setObject(count, fld.getValue());
            }
            // Значение поля id устанавливаем отдельно, т.к. в map fieldsNameAndValue его нет
            count++;
            preparedStatement.setObject(count, fieldID.getIdValue(objectData));

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        //jdbcConnection.closeConnection(connection);
    }

    @Override
    public <T> T load(long id, Class<T> clazz) {
        // Пока так делаем и в конце метода закрываем
        //Connection connection = jdbcConnection.getConnection(URL);




        //Создаем экземпляр класс. Поля будут заполнены данными из таблицы
        T objectData = null;
        try {
           objectData = clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException |
                NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }

        // Определяем название поля с аннотацией @id
        String fieldIdName = null;
        if (objectData != null) {
            fieldIdName = fieldID.getIdName(objectData);
        }

        //Определяем нужную строку по id и получаем нужные значения полей
        //SQL: "SELECT * FROM User WHERE id=?"
        try(PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM " + clazz.getSimpleName() + " WHERE " + fieldIdName + "=?")) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            int count = 0;
            // Заполняем поля нужными значениями через resultSet, который получили ранее
            Field[] fields = clazz.getDeclaredFields();
            for(Field field : fields) {
                try {
                    field.setAccessible(true);
                    // count определяет порядковый номер поля, значение которого мы должны получить
                    count++;
                    field.set(objectData, resultSet.getObject(count));
                    field.setAccessible(false);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }



        //jdbcConnection.closeConnection(connection);
        return objectData;
    }

    @Override
    public <T> void insertRecord(T objectData) {
        // Пока так делаем и в конце метода закрываем
        //Connection connection = jdbcConnection.getConnection(URL);

        Class<?> cls = objectData.getClass();
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            // Проверяем наличие аннотации, прежде чем добавить в таблицу
            if(field.isAnnotationPresent(id.class)) {
                checkingRowsInTable(connection, objectData);
            }
        }


        //jdbcConnection.closeConnection(connection);
    }

    /**
     * Метод getDatabaseMetaData позволяет получить название таблицы в БД, соответсвующий названию класса
     * @param objectData - экземпляр класса
     * @return - возвращает true, если такая таблица уже есть, и false, если нет
     */
    private <T> boolean getDatabaseMetaData(T objectData) {
        Class<?> clazz = objectData.getClass();
        boolean tableState = false;
        try {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            String[] types = {"TABLE"};
            ResultSet rs = databaseMetaData.getTables(null, null, "%", types);
            while (rs.next()) {
                if(rs.getString("TABLE_NAME").startsWith(clazz.getSimpleName().toUpperCase())) {
                    tableState = true;
                }
                // если надо вывести названия всех таблицв БД
                //System.out.println(rs.getString("TABLE_NAME"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return tableState;
    }

    /**
     * Метод createTable создает таблицу с названием полученного класса в базе данных H2 c полем id (@id):
     * id bigint(20) NOT NULL auto_increment
     * @param connection - текущее соединение connection
     * @param objectData - экземпляр класса, для которого создаем таблицу в БД
     */
    private <T> void createTable(Connection connection, T objectData) {
        String fieldId = fieldID.getIdName(objectData);
        Class<?> clazz = objectData.getClass();

        // Создали таблицу User со столбцом id (поле с аннотацией @id, название поля может быть любым)
        // SQL: "CREATE TABLE User (id bigint NOT NULL auto_increment)"
        try (PreparedStatement columnId = connection.prepareStatement("CREATE TABLE " +
                    clazz.getSimpleName() + "(" + fieldId + " bigint NOT NULL auto_increment)")) {
            columnId.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Добавляем остальные колонки таблицы для каждого поля экземпляра класса
        addOtherColumnsToTable(connection, objectData);
    }

    /**
     * Метод addOtherColumnsToTable добавляет колонки в таблицу БД для каждого поля
     * @param connection - текущее соединение connection
     * @param objectData - экземпляр класса, для которого создаем таблицу в БД и добавляем колонки для каждого поля
     */
    private <T> void addOtherColumnsToTable(Connection connection, T objectData) {
        Map<String, String> fieldsNameAndSQLType = fieldsTypeAndValue.getFieldsNameAndSQLType(objectData);
        Class<?> clazz = objectData.getClass();

        // Создаем перечисление ("название поля" "SQL тип поля")
        StringJoiner SQLString = new StringJoiner(", ", "(", ")");
        String SQLSubString = "";

        // Создаем стобцы таблицы для других полей класса
        for(Map.Entry<String, String> fld : fieldsNameAndSQLType.entrySet()) {
            SQLSubString = fld.getKey() + " " + fld.getValue();
            SQLString.add(SQLSubString);
        }

        // SQL: "ALTER TABLE User ADD (name VARCHAR, age INT)
        try(PreparedStatement otherColumns = connection.prepareStatement("ALTER TABLE " +
                clazz.getSimpleName() + " ADD " + SQLString)) {
            otherColumns.executeUpdate();
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
        long id = (long) fieldID.getIdValue(objectData);

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
        Class<?> clazz = objectData.getClass();
        String fieldIdName = fieldID.getIdName(objectData);
        Map<String, Object> fieldsNameAndValue = fieldsTypeAndValue.getFieldsNameAndValue(objectData);

        // Формируем перечисление имен полей "name, age" и т.д.  и "?, ?" и т.д.
        StringJoiner fieldsName = new StringJoiner(", ");
        StringJoiner values = new StringJoiner(", ");
        for(Map.Entry<String, Object> fld : fieldsNameAndValue.entrySet()) {
            fieldsName.add(fld.getKey());
            values.add("?");
        }
        // Формируем перечисление имен всех полей для SQL запроса
        // (id, name, age) и (?, ?, ?)
        String resultFieldsName = "(" + fieldIdName + ", " + fieldsName + ")";
        String resultValues = "(?, " + values + ")";

        /*// Делаю запрос для получения id и использования дальше
        // До этого, id генерил сам и вставлял со стороннего метода
        int myId = 0;
        try(PreparedStatement preparedStatement = connection.prepareStatement()) {
            preparedStatement.setLong();
        }*/


        // Меняем значение поля id на новый, который установила БД (id > 0)
        fieldID.setIdValue(objectData, id);

        //SQL: "INSERT INTO User (id, name, age) VALUES(?, ?, ?)"
        try(PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO " + clazz.getSimpleName() +
                            resultFieldsName + " VALUES" + resultValues)) {

            // Устанавливаем значения полей по порядку в SQL запросе
            // 1: id = значение
            preparedStatement.setLong(1, id);

            // 2: name = значение, 3: age = значение
            int count = 1;
            for(Map.Entry<String, Object> fld : fieldsNameAndValue.entrySet()) {
                count++;
                preparedStatement.setObject(count, fld.getValue());
            }
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
