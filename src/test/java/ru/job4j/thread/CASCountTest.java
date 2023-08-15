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
}