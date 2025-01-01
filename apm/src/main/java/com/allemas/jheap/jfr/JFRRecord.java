package com.allemas.jheap.jfr;

import java.time.Duration;

public record JFRRecord(String name, Duration period, String key) {
}

