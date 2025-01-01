package com.allemas.jheap;

import org.apache.avro.specific.SpecificRecord;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.avro.AvroParquetWriter;
import org.apache.parquet.hadoop.ParquetFileWriter;
import org.apache.parquet.hadoop.ParquetWriter;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.apache.hadoop.conf.Configuration;
import org.apache.parquet.hadoop.util.HadoopOutputFile;
import org.apache.parquet.io.OutputFile;

public class MetricWriter {
    private static final String OUTPUT_FILE = "/tmp/jheap-metrics.parquet";

    public static <T extends SpecificRecord> void writeToParquetFile(Iterable<T> records, Class<T> recordClass) throws IOException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Path path = new Path(OUTPUT_FILE);
        OutputFile outputFile = HadoopOutputFile.fromPath(path, new Configuration());
        try (ParquetWriter<T> writer = AvroParquetWriter.<T>builder(outputFile)
                .withSchema(recordClass.getDeclaredConstructor().newInstance().getSchema())
                .withWriteMode(ParquetFileWriter.Mode.OVERWRITE).build()
        ) {
            for (T record : records) {
                writer.write(record);
            }
        }
    }


}
