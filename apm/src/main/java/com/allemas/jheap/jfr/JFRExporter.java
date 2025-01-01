package com.allemas.jheap.jfr;

import com.allemas.jheap.Storage;
import jdk.jfr.consumer.RecordingStream;

import java.time.Duration;
import java.util.Arrays;


public class JFRExporter  {
    private final Duration dumpInterval;
    private Storage storage;
    JFRRecord[] jfrs;

    public JFRExporter(Duration dumpInterval, Storage storage) {
        this.dumpInterval = dumpInterval;
        this.storage = storage;
        jfrs = new JFRRecord[]{
                new JFRRecord("jdk.CPULoad", dumpInterval, "machineTotal"),
                new JFRRecord("jdk.GCHeapSummary", dumpInterval, "heapUsed"),
                new JFRRecord("jdk.MetaspaceSummary", dumpInterval, "gcThreshold"),
                new JFRRecord("jdk.MetaspaceAllocationFailure", dumpInterval, "size"),
                new JFRRecord("jdk.ResidentSetSize", dumpInterval, "size"),
                new JFRRecord("jdk.GCHeapMemoryUsage", dumpInterval, "used"),
                new JFRRecord("jdk.GCHeapMemoryPoolUsage", dumpInterval, "used")
        };
    }

    public void run() {
        try {
            var rs = new RecordingStream();
            Arrays.stream(jfrs).forEach(jfr -> {
                rs.enable(jfr.name()).withPeriod(dumpInterval);
                rs.onEvent(jfr.name(), JFRConsumer.build(jfr, storage));
            });
            rs.startAsync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static JFRExporter create(Duration duration, Storage storage){
        return new JFRExporter(duration, storage);
    }

}
