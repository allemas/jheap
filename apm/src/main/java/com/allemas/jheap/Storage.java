package com.allemas.jheap;

import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Storage<T> {
    private final int DEFAULT_CAPACITY = 200;
    private int capacity;

    private BlockingQueue<T> queue;

    public Storage() {
        capacity = DEFAULT_CAPACITY;
        queue = new ArrayBlockingQueue<T>(DEFAULT_CAPACITY);
    }

    public Storage(int capacity) {
        capacity = capacity;
        queue = new ArrayBlockingQueue<>(capacity);
    }

    public boolean push(T event) throws Throwable {
        try {
            return queue.offer(event, 100, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException("Time to add new element inside queue elapsed", e.getCause());
        }
    }

    public T poll() {
        return this.queue.poll();
    }

    public int remainingCapacity() {
        return queue.remainingCapacity();
    }


    public int drain(Collection<T> c) {
        return queue.drainTo(c);
    }

}
