package com.airlines.jdbc.app.persistance.config;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DbConnector {
    private final String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    private final String APPLICATION_PROPERTIES_FILE =  rootPath + "application.properties";

    public DataSource createBasicDataSource() {
        Properties properties = getProperties();
        try {
            FileInputStream fileInputStream = new FileInputStream(APPLICATION_PROPERTIES_FILE);
            properties.load(fileInputStream);
            fileInputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(properties.getProperty("mysql.datasource.driver-class-name"));
        dataSource.setUrl(properties.getProperty("mysql.datasource.url"));
        dataSource.setUsername(properties.getProperty("mysql.datasource.username"));
        dataSource.setPassword(properties.getProperty("mysql.datasource.password"));

        return dataSource;
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
