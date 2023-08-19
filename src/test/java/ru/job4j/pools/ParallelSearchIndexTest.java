package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ParallelSearchIndexTest {

    @Test
    void whenSearchInteger() {
        Integer[] array = {5, 3, 2, 4, 15, 21, 96};
        int expected = 3;
        assertThat(ParallelSearchIndex.searchIndex(4, array)).isEqualTo(expected);
    }

    @Test
    void whenSearchString() {
        String[] array = {"Petr", "Alex", "Roman"};
        int expected = 2;
        assertThat(ParallelSearchIndex.searchIndex("Roman", array)).isEqualTo(expected);
    }

    @Test
    void whenSearchFalse() {
        String[] array = {"Petr", "Alex", "Roman"};
        int expected = -1;
        assertThat(ParallelSearchIndex.searchIndex("Igor", array)).isEqualTo(expected);
    }

    @Test
    void whenParallelSearch() {
        Integer[] array = {5, 3, 2, 4, 15, 21, 96, 2, 5, 4, 56, 1, 2, 3, 4, 5, 45, 25, 14, 25, 36, 19};
        int expected = array.length - 1;
        assertThat(ParallelSearchIndex.searchIndex(19, array)).isEqualTo(expected);
    }

    @Test
    void whenSearchWithDuplicatedValues() {
        Integer[] array = {5, 5, 5};
        int expected = 0;
        assertThat(ParallelSearchIndex.searchIndex(5, array)).isEqualTo(expected);
    }
}