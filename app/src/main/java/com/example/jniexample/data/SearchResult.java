package com.example.jniexample.data;

public class SearchResult {
    private long result;
    private long elapsedTime;

    public SearchResult(long result, long elapsedTime) {
        this.result = result;
        this.elapsedTime = elapsedTime;
    }

    public long getResult() {
        return result;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }
}
