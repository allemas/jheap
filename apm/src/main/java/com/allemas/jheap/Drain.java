package com.allemas.jheap;

import com.allemas.jheap.schema.Metric;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Drain extends Thread {

    private Storage storage;

    public Drain(Storage storage) {
        this.storage = storage;
    }


    @Override
    public void run() {
        while (true) {
            try {
                if (storage.remainingCapacity() < 5) {
                    List<Metric> drainedElements = new ArrayList<>();
                    storage.drain(drainedElements);
                    MetricWriter.writeToParquetFile(drainedElements);
                }
                Thread.sleep(Duration.ofSeconds(1));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static Drain create(Storage storage) {
        return new Drain(storage);
    }

}
