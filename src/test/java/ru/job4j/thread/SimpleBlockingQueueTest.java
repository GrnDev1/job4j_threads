package ru.job4j.thread;


import org.junit.jupiter.api.Test;

import java.util.concurrent.CopyOnWriteArrayList;

import static org.assertj.core.api.Assertions.*;

class SimpleBlockingQueueTest {
    SimpleBlockingQueue<String> queue = new SimpleBlockingQueue<>(3);

    @Test
    void whenGetB() throws InterruptedException {
        queue.offer("A");
        queue.offer("B");
        queue.offer("C");
        Thread producer = new Thread(
                () -> {
                    try {
                        queue.offer("D");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
        Thread consumer = new Thread(
                () -> {
                    try {
                        queue.poll();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertThat(queue.poll()).isEqualTo("B");
    }

    @Test
    void whenGetD() throws InterruptedException {
        queue.offer("A");
        queue.offer("B");
        queue.offer("C");
        Thread producer = new Thread(
                () -> {
                    try {
                        queue.offer("D");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
        Thread consumer = new Thread(
                () -> {
                    try {
                        queue.poll();
                        queue.poll();
                        queue.poll();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertThat(queue.poll()).isEqualTo("D");
    }

    @Test
    public void whenFetchAllThenGetIt() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(3);
        Thread producer = new Thread(
                () -> {
                    try {
                        queue.offer(1);
                        queue.offer(2);
                        queue.offer(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer).containsExactly(1, 2, 3);
    }

}