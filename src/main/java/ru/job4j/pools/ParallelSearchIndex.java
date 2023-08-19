package ru.job4j.pools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ParallelSearchIndex<T> extends RecursiveAction {

    private final T[] array;
    private final int from;
    private final int to;
    private final T obj;
    private static final List<Integer> RESULT = new ArrayList<>();

    public ParallelSearchIndex(T obj, T[] array, int from, int to) {
        this.array = array;
        this.from = from;
        this.to = to;
        this.obj = obj;
    }

    public static <T> int linearSearch(T obj, T[] array) {
        int result = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(obj)) {
                result = i;
                break;
            }
        }
        return result;
    }

    @Override
    protected void compute() {
        if (to - from <= 10) {
            int temp = linearSearch(obj, array);
            if (temp != -1) {
                RESULT.add(temp);
            }
        } else {
            int mid = (from + to) / 2;
            ParallelSearchIndex<T> leftSort = new ParallelSearchIndex<>(obj, array, from, mid);
            ParallelSearchIndex<T> rightSort = new ParallelSearchIndex<>(obj, array, mid + 1, to);
            invokeAll(leftSort, rightSort);
        }
    }

    public static <T> Integer searchIndex(T obj, T[] array) {
        new ForkJoinPool().invoke(new ParallelSearchIndex<T>(obj, array, 0, array.length - 1));
        Collections.sort(RESULT);
        int result = RESULT.isEmpty() ? -1 : RESULT.get(0);
        RESULT.clear();
        return result;
    }
}
