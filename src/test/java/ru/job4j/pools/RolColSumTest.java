package ru.job4j.pools;

import org.junit.jupiter.api.Test;
import ru.job4j.pools.RolColSum.Sums;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.*;

class RolColSumTest {
    int[][] array = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
    Sums first = new Sums(6, 12);
    Sums second = new Sums(15, 15);
    Sums third = new Sums(24, 18);

    @Test
    void whenSum() {
        Sums[] expected = {first, second, third};
        assertThat(RolColSum.sum(array)).isEqualTo(expected);
    }

    @Test
    void whenAsyncSum() throws ExecutionException, InterruptedException {
        Sums[] expected = {first, second, third};
        assertThat(RolColSum.asyncSum(array)).isEqualTo(expected);
    }
}