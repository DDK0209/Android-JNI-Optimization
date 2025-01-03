package com.example.jniexample.contracts;

public interface ImageLoadCallback {
    void onWebpImageLoadTime(long loadTime);
    void onPngImageLoadTime(long loadTime);
}
