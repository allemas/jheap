package com.allemas.jheap.storage;


import com.allemas.jheap.schema.Metric;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.duckdb.DuckDBConnection;

import java.io.IOException;
import java.sql.*;

public class AvroParquetWriter {

    private final Connection connection;

    public AvroParquetWriter(String dbPath) throws SQLException {
        String url = "jdbc:duckdb:" + dbPath;
        this.connection = DriverManager.getConnection(url);
    }

    public void writeAvroToParquet(Metric record, String outputPath) throws SQLException {
        String sqlCreateTable = "CREATE TABLE IF NOT EXISTS metrics (" +
                "name TEXT, " +
                "source TEXT, " +
                "timestamp BIGINT, " +
                "value FLOAT, " +
                "details TEXT" +
                ")";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sqlCreateTable);
        }

        String sqlInsert = "INSERT INTO metrics (name, source, timestamp, value, details) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sqlInsert)) {
            stmt.setString(1, record.getName().toString());
            stmt.setString(2, record.getSource().toString());
            stmt.setLong(3, (Long) record.getTimestamp());
            stmt.setObject(4, record.getValue());
            stmt.setString(5, record.getDetails() != null ? record.getDetails().toString() : null);
            stmt.executeUpdate();
        }

        String sqlCopy = "COPY metrics TO '" + outputPath + "' (FORMAT PARQUET)";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sqlCopy);
        }
    }

    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
