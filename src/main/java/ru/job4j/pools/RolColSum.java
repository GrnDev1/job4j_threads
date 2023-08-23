package ru.job4j.pools;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {

    public static Sums[] sum(int[][] matrix) {

        Sums[] array = new Sums[matrix.length];
        for (int i = 0; i < array.length; i++) {
            int rowSum = 0;
            int colSum = 0;
            for (int j = 0; j < matrix.length; j++) {
                rowSum += matrix[i][j];
                colSum += matrix[j][i];
            }
            array[i] = new Sums(rowSum, colSum);
        }
        return array;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] array = new Sums[matrix.length];
        for (int i = 0; i < array.length; i++) {
            array[i] = getSums(matrix, i).get();
        }
        return array;
    }

    private static CompletableFuture<Sums> getSums(int[][] matrix, int index) {
        return CompletableFuture.supplyAsync(() -> {
            int rowSum = 0;
            int colSum = 0;
            for (int i = 0; i < matrix.length; i++) {
                rowSum += matrix[index][i];
                colSum += matrix[i][index];
            }
            return new Sums(rowSum, colSum);
        });
    }
}