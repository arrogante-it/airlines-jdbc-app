package com.airlines.jdbc.app;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class InputUtils {
    public static void createTables(DataSource dataSource) {
        String script = "create_tables.sql";
        String createTablesSql = new FileReader().readWholeFileFromResources(script);

        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(createTablesSql);
            statement.close();
        } catch (SQLException e) {
            throw new IllegalArgumentException("can't get data from " + script);
        }
    }

    public static void populate(DataSource dataSource) {
        String script = "populate.sql";
        String populateSql = new FileReader().readWholeFileFromResources(script);

        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(populateSql);
            statement.close();
        } catch (SQLException e) {
            throw new IllegalArgumentException("can't get data from " + script);
        }
    }
}
