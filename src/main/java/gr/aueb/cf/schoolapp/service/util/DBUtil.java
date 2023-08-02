package gr.aueb.cf.schoolapp.service.util;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DBUtil {
    private static BasicDataSource ds = new BasicDataSource();
    private static Connection connection;

    private DBUtil() {
    }

    static {
        ds.setUrl("jdbc:mysql://localhost:3306/schooldb?serverTimezone=UTC");
        ds.setUsername("schooldbuser");
        ds.setPassword(System.getenv("SCHOOL_DB_USER"));
        ds.setInitialSize(8);
        ds.setMaxTotal(32);
        ds.setMinIdle(8);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(100);
    }

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = ds.getConnection();
            return connection;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void closeConnection() {
        try {
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
