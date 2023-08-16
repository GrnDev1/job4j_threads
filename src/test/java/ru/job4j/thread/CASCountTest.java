package ru.job4j.thread;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CASCountTest {
    @Test
    public void whenIncrementThen100() throws InterruptedException {
        CASCount casCount = new CASCount();
        for (int i = 0; i < 100; i++) {
            new Thread(casCount::increment).start();
        }
        Thread.sleep(1000);
        assertThat(casCount.get()).isEqualTo(100);
    }

    @Test
    public void whenIncrementThen2() throws InterruptedException {
        CASCount casCount = new CASCount();
        Thread thread1 = new Thread(casCount::increment);
        Thread thread2 = new Thread(casCount::increment);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        assertThat(casCount.get()).isEqualTo(2);
    }
}