package com.example.jniexample.data;

public class SortResult {
    private int[] sortedArray; // Mảng đã sắp xếp
    private long elapsedTime; // Thời gian tính toán (microseconds)

    public SortResult(int[] sortedArray, long elapsedTime) {
        this.sortedArray = sortedArray;
        this.elapsedTime = elapsedTime;
    }

    public int[] getSortedArray() {
        return sortedArray;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }
}
