package com.allemas.jheap;

import com.allemas.jheap.schema.Metric;
import org.apache.avro.generic.GenericRecord;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.avro.AvroParquetReader;
import org.apache.parquet.hadoop.ParquetReader;


import java.io.IOException;
import java.sql.SQLException;
import java.time.Duration;



public class Main {
    public static void main(String[] args) throws  SQLException {
        final String INPUT_FILE = "/tmp/jheap-metrics.parquet";
        Path path = new Path(INPUT_FILE);
        com.allemas.jheap.storage.AvroParquetReader reader = new com.allemas.jheap.storage.AvroParquetReader(":memory:");
        Metric record = reader.readParquetFile(INPUT_FILE);

        if (record != null) {
            System.out.println("Read record:");
            System.out.println("Name: " + record.getName());
            System.out.println("Source: " + record.getSource());
            System.out.println("Timestamp: " + record.getTimestamp());
            System.out.println("Value: " + record.getValue());
        } else {
            System.out.println("No record found in the Parquet file.");
        }


        /**

        try (ParquetReader<GenericRecord> reader = AvroParquetReader.<GenericRecord>builder(path).build()) {
            GenericRecord record = null;
            while ((record = reader.read()) != null) {
                System.out.println(record);
            }
            try {
                Thread.sleep(Duration.ofSeconds(2));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
**/
    }
}
