package com.allemas.jheap.storage;

import org.duckdb.DuckDBConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DuckDBStorage {

    private Connection connection;

    public DuckDBStorage(String dbPath) throws SQLException {
        String url = "jdbc:duckdb:" + dbPath;
        this.connection = DriverManager.getConnection(url);
    }

    public void loadParquetFile(String parquetFilePath) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS metrics AS SELECT * FROM read_parquet('" + parquetFilePath + "')";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }
    }

    public void queryParquetFile(String query) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeQuery(query);
        }
    }

    public void writeParquet(String outputPath) throws SQLException {
        String sql = "COPY metrics TO '" + outputPath + "' (FORMAT PARQUET)";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }
    }

    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
