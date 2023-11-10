package com.airlines.jdbc.app;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public final class InputUtil {
    public static void createTables(DataSource dataSource) {
        String createTablesSql = FileUtil.readWholeFileFromResources("create_tables.sql");

        try {
            executeSqlScript(dataSource, createTablesSql);
        } catch (SQLException e) {
            throw new IllegalArgumentException("can't get data from " + "create_tables.sql");
        }
    }

    public static void populate(DataSource dataSource) {
        String populateSql = FileUtil.readWholeFileFromResources("populate.sql");

        try {
            executeSqlScript(dataSource, populateSql);
        } catch (SQLException e) {
            throw new IllegalArgumentException("can't get data from " + "populate.sql");
        }
    }

    private static void executeSqlScript(DataSource dataSource, String SqlScript) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(SqlScript);
            statement.close();
        }
    }
}
