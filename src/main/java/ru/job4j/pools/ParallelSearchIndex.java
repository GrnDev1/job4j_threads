package ru.job4j.pools;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSearchIndex<T> extends RecursiveTask<Integer> {

    private final T[] array;
    private final int from;
    private final int to;
    private final T obj;

    public ParallelSearchIndex(T obj, T[] array, int from, int to) {
        this.array = array;
        this.from = from;
        this.to = to;
        this.obj = obj;
    }

    public int linearSearch() {
        int result = -1;
        for (int i = from; i <= to; i++) {
            if (array[i].equals(obj)) {
                result = i;
                break;
            }
        }
        return result;
    }

    @Override
    protected Integer compute() {
        if (to - from <= 10) {
            return linearSearch();
        }
        int mid = (from + to) / 2;
        ParallelSearchIndex<T> leftSort = new ParallelSearchIndex<>(obj, array, from, mid);
        ParallelSearchIndex<T> rightSort = new ParallelSearchIndex<>(obj, array, mid + 1, to);
        leftSort.fork();
        rightSort.fork();
        return Math.max(leftSort.join(), rightSort.join());
    }

    public static <T> Integer searchIndex(T obj, T[] array) {
        return new ForkJoinPool().invoke(new ParallelSearchIndex<T>(obj, array, 0, array.length - 1));
    }
}
