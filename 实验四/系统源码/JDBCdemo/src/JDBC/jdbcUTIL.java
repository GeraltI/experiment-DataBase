package JDBC;

import java.sql.*;

public class jdbcUTIL {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/canteen";

    //  Database credentials -- 数据库名和密码自己修改
    static final String USER = "root";
    static final String PASS = "mysql";

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName(JDBC_DRIVER);

        //STEP 3: Open a connection
        System.out.println("Connecting to database...");
        Connection connection = DriverManager.getConnection(DB_URL,USER,PASS);
        return connection;
    }

    public static void close(Connection connection, Statement statement, ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

