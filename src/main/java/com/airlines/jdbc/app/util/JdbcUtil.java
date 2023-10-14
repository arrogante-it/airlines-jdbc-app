package com.airlines.jdbc.app.util;

import org.h2.jdbcx.JdbcDataSource;
import javax.sql.DataSource;
import java.util.Map;

// to test
public class JdbcUtil {
    static String DEFAULT_DATABASE_NAME = "test_airlines_db";
    static String DEFAULT_USERNAME = "airlineuser";
    static String DEFAULT_PASSWORD = "airlinepass";

    public static DataSource createDefaultInMemoryH2DataSource() {
        String url = formatH2InMemoryDbUrl(DEFAULT_DATABASE_NAME);
        return createInMemoryH2DataSource(url, DEFAULT_USERNAME, DEFAULT_PASSWORD);
    }

    public static DataSource createInMemoryH2DataSource(String url, String username, String password) {
        JdbcDataSource h2DataSource = new JdbcDataSource();
        h2DataSource.setUser(username);
        h2DataSource.setPassword(password);
        h2DataSource.setUrl(url);

        return h2DataSource;
    }

    private static String formatH2InMemoryDbUrl(String databaseName) {
        return String.format("jdbc:h2:mem:%s;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=false;DATABASE_TO_UPPER=false;", databaseName);
    }

    public static Map<String, String> getInMemoryDbPropertiesMap() {
        return Map.of(
                "url", String.format("jdbc:h2:mem:%s", DEFAULT_DATABASE_NAME),
                "username", DEFAULT_USERNAME,
                "password", DEFAULT_PASSWORD);
    }
}
