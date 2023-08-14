package ru.job4j.thread;

import net.jcip.annotations.*;

import java.util.*;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    private final int size;

    public SimpleBlockingQueue(int size) {
        this.size = size;
    }

    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();

    public synchronized void offer(T value) throws InterruptedException {
        while (queue.size() == size) {
            wait();
        }
        queue.offer(value);
        notifyAll();
    }

    public synchronized T poll() throws InterruptedException {
        while (queue.size() == 0) {
            wait();
        }
        T result = queue.poll();
        notifyAll();
        return result;
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}