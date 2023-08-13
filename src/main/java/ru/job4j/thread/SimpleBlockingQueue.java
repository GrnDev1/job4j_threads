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

    public void offer(T value) throws InterruptedException {
        synchronized (queue) {
            while (queue.size() == size) {
                    queue.wait();
            }
            queue.offer(value);
            queue.notifyAll();
        }
    }

    public T poll() throws InterruptedException {
        T result;
        synchronized (queue) {
            while (queue.size() == 0) {
                queue.wait();
            }
            result = queue.poll();
            queue.notifyAll();
        }
        return result;
    }
}