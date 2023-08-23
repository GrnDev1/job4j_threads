package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

class RolColSumTest {

    @Test
    void whenSum() {
        int[][] array = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        Sums first = new Sums(6, 12);
        Sums second = new Sums(15, 15);
        Sums third = new Sums(24, 18);
        Sums[] expected = {first, second, third};
        assertThat(RolColSum.sum(array)).isEqualTo(expected);
    }

    @Test
    void whenAsyncSum() throws ExecutionException, InterruptedException {
        int[][] array = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        Sums first = new Sums(6, 12);
        Sums second = new Sums(15, 15);
        Sums third = new Sums(24, 18);
        Sums[] expected = {first, second, third};
        assertThat(RolColSum.asyncSum(array)).isEqualTo(expected);
    }
}