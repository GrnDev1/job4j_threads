package ru.job4j.thread;


import org.junit.jupiter.api.Test;

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

}