package ru.job4j.pools;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {
    public static class Sums {
        private int rowSum;
        private int colSum;

        public Sums() {
        }

        public Sums(int rowSum, int colSum) {
            this.rowSum = rowSum;
            this.colSum = colSum;
        }

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Sums sums = (Sums) o;
            return rowSum == sums.rowSum && colSum == sums.colSum;
        }

        @Override
        public int hashCode() {
            return Objects.hash(rowSum, colSum);
        }
    }

    public static Sums[] sum(int[][] matrix) {

        Sums[] array = new Sums[matrix.length];
        for (int i = 0; i < array.length; i++) {
            Sums temp = new Sums();
            for (int j = 0; j < matrix.length; j++) {
                temp.rowSum += matrix[i][j];
                temp.colSum += matrix[j][i];
            }
            array[i] = temp;
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
            Sums temp = new Sums();
            for (int i = 0; i < matrix.length; i++) {
                temp.rowSum += matrix[index][i];
                temp.colSum += matrix[i][index];
            }
            return temp;
        });
    }
}