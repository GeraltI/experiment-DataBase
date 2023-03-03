package JDBC;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class jdbcDAO <T>{
    public static int update(String sql,Object... args) throws SQLException, ClassNotFoundException {
        Connection connection = jdbcUTIL.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for(int i = 0; i < args.length; i++){
            preparedStatement.setObject(i + 1,args[i]);
        }
        int rows = preparedStatement.executeUpdate();
        jdbcUTIL.close(connection,preparedStatement,null);
        return rows;
    }

    //K在调用前作为参数得知
    public static <K> List<K> getList(String sql, Class<K> clas,Object... args) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        Connection connection = jdbcUTIL.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for(int i = 0; i < args.length; i++){
            preparedStatement.setObject(i + 1,args[i]);
        }
        ResultSet resultSet = preparedStatement.executeQuery();
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        List<K> list = new ArrayList<>();
        int columnCount = resultSetMetaData.getColumnCount();
        while(resultSet.next()){
            K object = clas.newInstance();
            for(int i = 0; i < columnCount; i++){
                String columnLabel = resultSetMetaData.getColumnLabel(i + 1);
                Object value = resultSet.getObject(columnLabel);
                Field field = clas.getDeclaredField(columnLabel);
                field.setAccessible(true);
                field.set(object,value);
            }
            list.add(object);
        }
        jdbcUTIL.close(connection,preparedStatement,resultSet);
        return list;
    }

    public jdbcDAO(Class<T> clas) {
        this.clas = clas;
    }

    private Class <T> clas;

    public List<T> getList(String sql, Object... args) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        Connection connection = jdbcUTIL.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for(int i = 0; i < args.length; i++){
            preparedStatement.setObject(i + 1,args[i]);
        }
        ResultSet resultSet = preparedStatement.executeQuery();
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        List<T> list = new ArrayList<>();
        int columnCount = resultSetMetaData.getColumnCount();
        while(resultSet.next()){
            T object = clas.newInstance();
            for(int i = 0; i < columnCount; i++){
                String columnLabel = resultSetMetaData.getColumnLabel(i + 1);
                Object value = resultSet.getObject(columnLabel);
                Field field = clas.getDeclaredField(columnLabel);
                field.setAccessible(true);
                field.set(object,value);
            }
            list.add(object);
        }
        jdbcUTIL.close(connection,preparedStatement,resultSet);
        return list;
    }

}
