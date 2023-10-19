package com.airlines.jdbc.app;

import com.airlines.jdbc.app.exception.SQLOperationException;
import com.mysql.cj.jdbc.MysqlDataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    private final static String DEFAULT_DATABASE_NAME = "airlines_db";
    private final static String USERNAME = "root";
    private final static String PASS = "root";
    private final static String CONNECTION_URL = "jdbc:mysql://localhost:3306/%s";

    public static Connection getConnection() {
        try {
            String url = formatMySqlDbUrl();
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, USERNAME, PASS);
        } catch (SQLException | ClassNotFoundException e) {
            throw new SQLOperationException(e);
        }
    }

    public static DataSource createBasicDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl(formatTESTDbUrl(DEFAULT_DATABASE_NAME));
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASS);

        return dataSource;
    }

//    public static Connection getConnectionTEST() {
//        try {
//            String url = formatMySqlDbUrl();
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            return createDefaultDataSource().getConnection();
//        } catch (SQLException | ClassNotFoundException e) {
//            throw new SQLOperationException(e);
//        }
//    }
//
    private static String formatMySqlDbUrl() {
        return String.format(CONNECTION_URL, DBConnector.DEFAULT_DATABASE_NAME);
    }
//
//    public static DataSource createDefaultDataSource() {
//        String url = formatTESTDbUrl(DEFAULT_DATABASE_NAME);
//        return createDataSource(url, USERNAME, PASS);
//    }
//
//    public static DataSource createDataSource(String url, String username, String password) {
//        MysqlDataSource dataSource = new MysqlDataSource();
//        dataSource.setUser(username);
//       dataSource.setPassword(password);
//        dataSource.setUrl(url);
//
//        return dataSource;
//    }

    private static String formatTESTDbUrl(String databaseName) {
        return String.format(CONNECTION_URL, databaseName);
    }
}
