#include <jni.h>
#include <string>
#define PI 3.141592653589793

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_jniexample_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

void quicksort(jlong index, jlong i, jlong *pInt);

void exchange(jlong i, jlong j, jlong *pInt);


// bubbleSort
struct SortTask {
    jint *array;
    jsize length;
};

void bubbleSort(jint* array, jsize length) {
    for (int i = 0; i < length - 1; i++) {
        for (int j = 0; j < length - 1 - i; j++) {
            if (array[j] > array[j + 1]) {
                jint temp = array[j];
                array[j] = array[j + 1];
                array[j + 1] = temp;
            }
        }
    }
}

void* sortArray(void* arg) {
    SortTask* task = (SortTask*)arg;
    bubbleSort(task->array, task->length);
    return nullptr;
}

extern "C"  JNIEXPORT void JNICALL
Java_com_example_jniexample_Views_SortAlgorithmActivity_nativeSort(JNIEnv *env, jobject obj, jintArray array) {
    // Lấy thông tin từ mảng đầu vào
    jsize length = env->GetArrayLength(array);
    jint* arr_elements = env->GetIntArrayElements(array, NULL);
    bubbleSort(arr_elements,length);

//    SortTask task = {arr_elements, length};
//    pthread_t thread;
//    pthread_create(&thread, nullptr, sortArray, &task);
//    pthread_join(thread, nullptr);
//    env->ReleaseIntArrayElements(array, arr_elements, 0);
//    return array;
}



// others
extern "C" JNIEXPORT jdouble JNICALL
Java_com_example_jniexample_Views_OtherAlgorithmActivity_calculateCircleAreaNative(JNIEnv *env, jobject obj, jdouble radius) {
    return PI * radius * radius;
}

extern "C" JNIEXPORT jint JNICALL
Java_com_example_jniexample_Views_OtherAlgorithmActivity_fibonacciNative(
        JNIEnv* env,
        jobject obj,jlong number) {
    if (number <= 1) return number;

    jlong a = 0, b = 1, result;
    for (long i = 2; i <= number; ++i) {
        result = a + b;
        a = b;
        b = result;
    }
    return result;
}

extern "C" JNIEXPORT void JNICALL
Java_com_example_jniexample_Views_OtherAlgorithmActivity_callEmptyNativeFunction(JNIEnv *env, jobject obj) {
    // Do nothing, just measure latency when calling JNI
}


extern "C" JNIEXPORT jlong JNICALL
Java_com_example_jniexample_Views_OtherAlgorithmActivity_nativeMemoryAccessTest(JNIEnv *env, jobject obj,jint size) {
    struct timespec start, end;
    clock_gettime(CLOCK_MONOTONIC, &start);

    int* array = new int[size];

    for (int i = 0; i < size; i++) {
        array[i] = i;
    }

    long sum = 0;
    for (int i = 0; i < size; i++) {
        sum += array[i];
    }
    clock_gettime(CLOCK_MONOTONIC, &end);
    // Free memory
    delete[] array;

    long executionTime = (end.tv_sec - start.tv_sec) * 1e9 + (end.tv_nsec - start.tv_nsec);
    return executionTime;
}



extern "C" JNIEXPORT jlong JNICALL
Java_com_example_jniexample_Views_OtherAlgorithmActivity_nativeStringProcessingTest(JNIEnv *env, jobject obj,jint length) {
    struct timespec start, end;
    clock_gettime(CLOCK_MONOTONIC, &start);

    std::string result = "";
    for (int i = 0; i < length; i++) {
        result.append("Test");
    }

    clock_gettime(CLOCK_MONOTONIC, &end);
    long executionTime = (end.tv_sec - start.tv_sec) * 1e9 + (end.tv_nsec - start.tv_nsec);
    return executionTime;
}




extern "C"  JNIEXPORT jintArray JNICALL
Java_com_example_jniexample_Views_SortAlgorithmActivity_bubbleSortJNI(JNIEnv *env, jobject instance, jintArray oldValues) {
    const jsize length = (*env).GetArrayLength( oldValues);
    jint *oarr = (*env).GetIntArrayElements( oldValues, NULL);
    for (int i = 0; i < length - 1; i++) {
        // Last i elements are already in place
        for (int j = 0; j < length - i - 1; j++) {
            if (oarr[j] > oarr[j + 1]) {
                int temp = oarr[j];
                oarr[j] = oarr[j + 1];
                oarr[j + 1] = temp;
            }
        }
    }
    env->ReleaseIntArrayElements(oldValues, oarr, 0);
    return oldValues;
}

extern "C" JNIEXPORT jlongArray JNICALL
Java_com_example_jniexample_Views_SortAlgorithmActivity_qsort(JNIEnv* env, jobject instance,
                                        jlongArray arr, jlong arrayLength) {
    jlong *carr;
    carr = env->GetLongArrayElements(arr, NULL);
    // Check for empty or null array
    if (carr == NULL) {
        return 0;
    }
    jlong *numbers = carr;
    jlong startIndex = 0;
//    clock_t startTime = clock();
//    quicksort(startIndex, arrayLength - 1,numbers);
//    clock_t endTime = clock();
//    long elapsedTime = (endTime - startTime) * 1000000000 / CLOCKS_PER_SEC;  // nano
//    jlong jElapsedTime = static_cast<jlong>(elapsedTime);
//    return  jElapsedTime;
    quicksort(startIndex, arrayLength - 1,numbers);
    env->ReleaseLongArrayElements(arr, carr, 0);
    return arr;
}

void quicksort(jlong low, jlong high,jlong *numbers) {
    jlong i = low, j = high;
    // Get the pivot element from the middle of the list
    jlong pivot = numbers[low + (high-low)/2];
    // Divide into two lists
    while (i <= j) {
        // If the current value from the left list is smaller then the pivot
        // element then get the next element from the left list
        while (numbers[i] < pivot) {
            i++;
        }
        // If the current value from the right list is larger then the pivot
        // element then get the next element from the right list
        while (numbers[j] > pivot) {
            j--;
        }
        // If left list is larger then the pivot element and if we have found
        // a value in the right list which is smaller then the pivot element
        // then we exchange the values.
        if (i <= j) {
            exchange(i, j,numbers);
            i++;
            j--;
        }
    }
    if (low < j) quicksort(low, j,numbers);
    if (i < high) quicksort(i, high,numbers);
}

void exchange(jlong i, jlong j,jlong *numbers) {
    jlong temp = numbers[i];
    numbers[i] = numbers[j];
    numbers[j] = temp;
}


extern "C" JNIEXPORT jint JNICALL
Java_com_example_jniexample_Views_SortAlgorithmActivity_binarySearch(JNIEnv *env, jobject instance,
                                                                     jintArray arr,
                                                                     jint arrayLength,
                                                                     jint target) {
    jint *carr =  env->GetIntArrayElements(arr, NULL);
    if (carr == NULL) {
        return -1;
    }

    jint low = 0;
    jint high = arrayLength - 1;
    jint result = -1;
    while (low <= high) {
        jint mid = low + (high - low) / 2;
        if (carr[mid] == target) {
            result = carr[mid];
            return result;
        }
        if (carr[mid] < target) {
            low = mid + 1;
        } else {
            high = mid - 1;
        }
    }
    return -1;

}


extern "C" JNIEXPORT jobject JNICALL
Java_com_example_jniexample_Views_SortAlgorithmActivity_binarySearchAndMeasureTime(JNIEnv *env, jobject instance,
                                                                                   jlongArray arr, jlong target) {
    jlong *array = env->GetLongArrayElements(arr, NULL);
    if (array == NULL) {
        return NULL; // Lỗi khi lấy mảng
    }

    jlong low = 0;
    jlong high = env->GetArrayLength(arr) - 1;
    jlong result = -1;

//    clock_t startTime = clock();
    struct timespec start, end;
    clock_gettime(CLOCK_MONOTONIC, &start);

    while (low <= high) {
        jint mid = low + (high - low) / 2;
        if (array[mid] == target) {
            result = array[mid];
            break;
        }
        if (array[mid] < target) {
            low = mid + 1;
        } else {
            high = mid - 1;
        }
    }
//    clock_t endTime = clock();
//    long elapsedTime = (endTime - startTime) * 1000000000 / CLOCKS_PER_SEC;  // nano
//    jlong jElapsedTime = static_cast<jlong>(elapsedTime);
    clock_gettime(CLOCK_MONOTONIC, &end);
    long executionTime = (end.tv_sec - start.tv_sec) * 1e9 + (end.tv_nsec - start.tv_nsec);
    jlong jElapsedTime = static_cast<jlong>(executionTime);

    // Free the array memory
    env->ReleaseLongArrayElements(arr, array, 0);

    // Create a result object to return (search results and time)
    jclass resultClass = env->FindClass("com/example/jniexample/data/SearchResult");
    jmethodID constructor = env->GetMethodID(resultClass, "<init>", "(JJ)V");

    jobject resultObj = env->NewObject(resultClass, constructor, result, jElapsedTime);

    return resultObj;
}