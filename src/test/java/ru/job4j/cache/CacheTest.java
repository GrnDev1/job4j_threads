package ru.job4j.cache;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CacheTest {
    @Test
    void whenIllegalUpdate() {
        Cache cache = new Cache();
        Base base1 = new Base(1, 0);
        Base base2 = new Base(1, 1);
        cache.add(base1);
        assertThatThrownBy(() -> cache.update(base2))
                .isInstanceOf(OptimisticException.class);
    }

    @Test
    void whenUpdateSuccessful() {
        Cache cache = new Cache();
        Base base1 = new Base(1, 3);
        Base base2 = new Base(1, 3);
        cache.add(base1);
        assertThat(cache.update(base2)).isTrue();
    }
}