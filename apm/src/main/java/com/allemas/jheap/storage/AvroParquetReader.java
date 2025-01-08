package com.allemas.jheap.storage;

import com.allemas.jheap.schema.Metric;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.generic.GenericData;
import org.duckdb.DuckDBConnection;

import java.sql.*;

public class AvroParquetReader {

    private final Connection connection;

    public AvroParquetReader(String dbPath) throws SQLException {
        String url = "jdbc:duckdb:" + dbPath;
        this.connection = DriverManager.getConnection(url);
    }

    public Metric readParquetFile(String parquetFilePath) throws SQLException {
        String sqlCreateTable = "CREATE TABLE IF NOT EXISTS metrics AS SELECT * FROM read_parquet('" + parquetFilePath + "')";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sqlCreateTable);
        }

        String query = "SELECT * FROM metrics";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        Metric record = null;
        if (rs.next()) {
            record = Metric.newBuilder()
                    .setName(rs.getString("name"))
                    .setTimestamp(rs.getLong("timestamp"))
                    .setSource(rs.getString("source"))
                    .setValue(rs.getFloat("value"))
                    .setDetails(rs.getString("details"))
                    .build();
        }

        return record;
    }

    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
