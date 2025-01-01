package com.allemas.jheap;

import com.allemas.jheap.jfr.JFRExporter;
import com.allemas.jheap.schema.Metric;

import java.lang.instrument.Instrumentation;
import java.time.Duration;


public class AgentBootstrap {

    public static void agentmain(String agentArgs, Instrumentation inst) {
        initAgent();
    }

    public static void premain(String agentArgs, Instrumentation inst) {
        initAgent();
    }

    static void initAgent() {
        Storage<Metric> storage = new Storage<>(10);
        JFRExporter.create(Duration.ofSeconds(1), storage).run();
        Drain.create(storage).start();
    }

}
