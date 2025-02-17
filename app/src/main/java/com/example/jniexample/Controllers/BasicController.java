package com.example.jniexample.Controllers;

import java.util.Random;

public class BasicController {
    public long fibonacciJava(long number) {
        if (number <= 1) return number;
        long a = 0, b = 1, result = 1;
        for (long i = 2; i <= number; ++i) {
            result = a + b;
            a = b;
            b = result;
        }
        return result;
    }

    public double calculateCircleAreaJava(double radius) {
        return Math.PI * radius * radius;
    }

    public long javaMemoryAccessTest(int size) {
        int[] array = new int[size];
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
        long sum = 0;
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
        }
        return sum;
    }

    public String javaStringProcessingTest(int size) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            builder.append("Test");
        }
        String result = builder.toString();
        return result;
    }

    public int[] bubbleSortJava(int[] array) {
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
        return array;
    }

    public static int partition(int[] array, int low, int high) {
        int pivot = array[high];
        int i = (low - 1);

        for (int j = low; j < high; j++) {
            if (array[j] <= pivot) {
                i++;
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }

        int temp = array[i + 1];
        array[i + 1] = array[high];
        array[high] = temp;

        return i + 1;
    }

    public static long[] quickSort(long[] array, long low, long high) {
        if (array == null || array.length == 0)
            return array;

        if (low >= high)
            return array;

        long middle = ( low + (high - low) / 2);
        long pivot = array[(int) middle];
        long i = low, j = high;

        while (i <= j) {
            while (array[(int) i] < pivot) {
                i++;
            }

            while (array[(int) j] > pivot) {
                j--;
            }

            if (i <= j) {
                long temp = array[(int) i];
                array[(int) i] = array[(int) j];
                array[(int) j] = temp;
                i++;
                j--;
            }
        }

        if (low < j)
            quickSort(array, low, j);

        if (high > i)
            quickSort(array, i, high);

        return array;
    }


    public static long binarySearch(long[] numbers, long target) {
        long low = 0;
        long high = numbers.length - 1;

        while (low <= high) {
            int mid = (int) (low + (high - low) / 2);

            if (numbers[mid] == target) {
                return numbers[mid];
            }

            if (numbers[mid] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return -1;
    }


    public int[][] multiplyMatrices(int[][] matrixA, int[][] matrixB) {
        int rowsA = matrixA.length;
        int colsA = matrixA[0].length;
        int colsB = matrixB[0].length;

        int[][] result = new int[rowsA][colsB];

        // Nhân ma trận
        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                for (int k = 0; k < colsA; k++) {
                    result[i][j] += matrixA[i][k] * matrixB[k][j];
                }
            }
        }

        return result;
    }


    public static int[][] generateRandomMatrix(int rows) {
        Random rand = new Random();
        int[][] matrix = new int[rows][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows; j++) {
                matrix[i][j] = rand.nextInt(10);
            }
        }
        return matrix;
    }



    public static int[] multiplyMatricesJava(int[] a, int[] b, int n) {
        int[] c = new int[n * n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    c[i * n + j] += a[i * n + k] * b[k * n + j];
                }
            }
        }
        return c;
    }

    public static int[] convert2DTo1D(int[][] matrix) {
        int n = matrix.length;
        int[] result = new int[n * n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result[i * n + j] = matrix[i][j];
            }
        }
        return result;
    }

}
