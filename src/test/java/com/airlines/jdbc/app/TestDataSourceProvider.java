package com.airlines.jdbc.app;

import com.zaxxer.hikari.HikariDataSource;

public class TestDataSourceProvider {
    private String DEFAULT_DATABASE_NAME = "test_airlines_db";
    private String DEFAULT_USERNAME = "airlineuser";
    private String DEFAULT_PASSWORD = "airlinepass";

    public HikariDataSource createDefaultInMemoryH2DataSource() {
        String url = formatH2InMemoryDbUrl(DEFAULT_DATABASE_NAME);

        HikariDataSource h2DataSource = new HikariDataSource();
        h2DataSource.setUsername(DEFAULT_USERNAME);
        h2DataSource.setPassword(DEFAULT_PASSWORD);
        h2DataSource.setJdbcUrl(url);

        return h2DataSource;
    }

    private String formatH2InMemoryDbUrl(String databaseName) {
        return String.format("jdbc:h2:mem:%s;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=false;DATABASE_TO_UPPER=false;", databaseName);
    }
}
