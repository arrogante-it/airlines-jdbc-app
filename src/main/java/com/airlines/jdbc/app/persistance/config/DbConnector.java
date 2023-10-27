package com.airlines.jdbc.app.persistance.config;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;

public class DbConnector {
    private final static String DEFAULT_DATABASE_NAME = "airlines_db";
//    private final static String USERNAME = "root";
//    private final static String PASS = "root";
    private final static String CONNECTION_URL = "jdbc:mysql://localhost:3306/%s";

    public static DataSource createBasicDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl(formatTESTDbUrl(DEFAULT_DATABASE_NAME));
//        dataSource.setUsername(USERNAME);
//        dataSource.setPassword(PASS);

        return dataSource;
    }

    private static String formatTESTDbUrl(String databaseName) {
        return String.format(CONNECTION_URL, databaseName);
    }
}
