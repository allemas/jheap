package com.allemas.jheap.jfr;

import com.allemas.jheap.Storage;
import com.allemas.jheap.schema.Metric;
import jdk.jfr.consumer.RecordedEvent;

import java.time.Instant;
import java.util.function.Consumer;

public class JFRConsumer implements Consumer<RecordedEvent> {
    private JFRRecord jfrRecord;
    private Storage storage;


    public JFRConsumer(JFRRecord jfrRecord, Storage storage) {
        this.jfrRecord = jfrRecord;
        this.storage = storage;
    }

    @Override
    public void accept(RecordedEvent recordedEvent) {
        try {
            storage.push(Metric.newBuilder()
                    .setName(this.jfrRecord.name())
                    .setValue(Float.valueOf(recordedEvent.getValue(jfrRecord.key()).toString()))
                    .setSource("jheap-apm")
                    .setDetails("?")
                    .setTimestamp(Instant.now().toEpochMilli())
                    .build()
            );
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static JFRConsumer build(JFRRecord jfrRecordInfo, Storage storage) {
        return new JFRConsumer(jfrRecordInfo, storage);
    }


}
