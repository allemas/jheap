package com.allemas.jheap;

import com.allemas.jheap.schema.Metric;
import org.apache.avro.Schema;
import org.apache.avro.SchemaBuilder;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.util.Utf8;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.avro.AvroParquetReader;
import org.apache.parquet.avro.AvroParquetWriter;
import org.apache.parquet.avro.AvroReadSupport;
import org.apache.parquet.conf.ParquetConfiguration;
import org.apache.parquet.conf.PlainParquetConfiguration;
import org.apache.parquet.hadoop.ParquetFileWriter;
import org.apache.parquet.hadoop.ParquetReader;
import org.apache.parquet.hadoop.ParquetWriter;
import org.apache.parquet.hadoop.util.HadoopInputFile;
import org.apache.parquet.io.InputFile;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

public class Main {
    public static void main(String[] args) throws IOException {
        final String INPUT_FILE = "/tmp/jheap-metrics.parquet";
        Path path = new Path(INPUT_FILE);
        InputFile inputFile = null;
        inputFile = HadoopInputFile.fromPath(path, new Configuration());

        try (ParquetReader<GenericRecord> reader = AvroParquetReader.<GenericRecord>builder(inputFile).build()) {
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

    }
}
