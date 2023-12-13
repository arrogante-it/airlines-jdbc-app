package com.airlines.jdbc.app.persistance.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DbConnector {
    private final String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    private final String APPLICATION_PROPERTIES_FILE = rootPath + "application.properties";

    public HikariDataSource createBasicDataSource() {
        Properties properties = getProperties();

        HikariConfig config = new HikariConfig();
        config.setDriverClassName(properties.getProperty("db.driver-class-name"));
        config.setJdbcUrl(properties.getProperty("db.url"));
        config.setUsername(properties.getProperty("db.username"));
        config.setPassword(properties.getProperty("db.password"));

        return new HikariDataSource(config);
    }

    private Properties getProperties() {
        Properties properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream(APPLICATION_PROPERTIES_FILE);
            properties.load(fileInputStream);
            fileInputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return properties;
    }
}
