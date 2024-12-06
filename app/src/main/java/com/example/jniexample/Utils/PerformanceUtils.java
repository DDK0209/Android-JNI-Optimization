package com.example.jniexample.Utils;

import java.util.function.Supplier;

public class PerformanceUtils {
    public static  <T>  long measureExecutionTime(String methodName, Supplier<T> computation) {
        long startTime = System.nanoTime();

        T result = computation.get();

        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;
        return elapsedTime;

//        return "Kết quả (" + methodName + "): " + result + "\nThời gian: " + elapsedTime + " ns";
    }
}
