package ru.job4j.thread;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
public class CASCount {
    private final AtomicInteger count = new AtomicInteger();

    public void increment() {
        int temp;
        do {
            temp = count.get();
        } while (!count.compareAndSet(temp, ++temp));
    }

    public int get() {
        return count.get();
    }
}