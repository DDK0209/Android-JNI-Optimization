package com.example.jniexample.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jniexample.Constants.AppConstants;
import com.example.jniexample.Controllers.BasicController;
import com.example.jniexample.MainActivity;
import com.example.jniexample.R;
import com.example.jniexample.Utils.PerformanceUtils;
import com.example.jniexample.Utils.ToolBarUtils;
import com.example.jniexample.data.SearchResult;
import com.example.jniexample.databinding.ActivitySortAlgorithmBinding;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Arrays;

public class SortAlgorithmActivity extends AppCompatActivity {

    private ActivitySortAlgorithmBinding binding;

    static {
        System.loadLibrary("jniexample");
    }


    private  long[] sharedArray;
    private   long[] sortedArray;

    private   long searchResult;
    PerformanceUtils performanceUtils;
    BasicController controller;
    long javaResult=0;
    long nativeResult=0;
    int enteredNumber=0;


    // UI components
    EditText inputNumberSoft,targetNumber;
    TextView javaResultTV,nativeResultTV,comparisonText;
    TextView sortedArrayJavaView,sortedArrayNativeView,unsortedArrayView;
    Button javaButton,nativeButton;
    Button javaSearchButton,nativeSearchButton;
    TextView searchResultView,searchComparisonView,searchJavaResult,searchNativeResult;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_sort_algorithm);

        binding = ActivitySortAlgorithmBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        bindings();
        initialize();
        onClick();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initialize(){
        setSupportActionBar(toolbar);
        ToolBarUtils.setupBasicToolbar(toolbar,() -> finish());
        performanceUtils= new PerformanceUtils();
        controller=new BasicController();
    }
    private void bindings(){
        toolbar = binding.toolBarView;
        inputNumberSoft = binding.inputSort;
        javaButton = binding.sortJavaButton;
        nativeButton = binding.sortNativeButton;
        javaResultTV = binding.sortJavaResultView;
        nativeResultTV = binding.sortNativeResultView;
        comparisonText = binding.sortCompareResultView;
        sortedArrayNativeView= binding.sortedArrayNativeView;
        sortedArrayJavaView= binding.sortedArrayJavaView;
        unsortedArrayView= binding.unsortedArrayView;

//        search
        javaSearchButton= binding.searchJavaButton;
        nativeSearchButton = binding.searchNativeButton;
        targetNumber = binding.inputTargetNumber;
        searchResultView = binding.searchResultView;
        searchComparisonView = binding.searchCompareResultView;
        searchJavaResult = binding.searchJavaResultView;
        searchNativeResult = binding.searchNativeResultView;

    }
    private void onClick(){
        onHandleSortClick();
        onHandleSearchClick();
    }
    private void onHandleSortClick(){
        onHandleSortByJava();
        onHandleSortByNative();
    }
    private void onHandleSearchClick(){
        onHandleSearchByNative();
        onHandleSearchByJava();
    }

    private void onHandleSortByJava(){
        javaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = inputNumberSoft.getText().toString();
                try{
                    int number = Integer.parseInt(input);
                    if(number  >= AppConstants.MAX_SIZE){
                        Toast.makeText(SortAlgorithmActivity.this, "Too large number. Please input smaller number", Toast.LENGTH_LONG).show();
                        return;
                    }
                    updateSelectedNumber(number);
                    ensureArrayExists(number);
                    // Chạy sắp xếp trong background thread bằng ExecutorService
                    ExecutorService executor = Executors.newSingleThreadExecutor();
                    executor.submit(new Runnable() {
                        @Override
                        public void run() {
                            // Measure time and do sorting in background
                            long[] arrayCopy = sharedArray.clone();
                            long result = performanceUtils.measureExecutionTime("Java", () -> {
                                sortedArray = controller.quickSort(arrayCopy,0,arrayCopy.length-1);
                                return sortedArray;
                            });

                            javaResult=result;
                            // Update results to UI thread
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    javaResultTV.setText(result+" ns");
                                    onChangeComparisonVisible(number);
                                    sortedArrayJavaView.setText(Arrays.toString(sortedArray));
                                }
                            });
                        }
                    });
                    executor.shutdown();
                }catch(Exception e){
                    Toast.makeText(SortAlgorithmActivity.this, "Invalid input", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void onHandleSortByNative(){
        nativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = inputNumberSoft.getText().toString();
                try {
                    int number = Integer.parseInt(input);
                    if(number >= AppConstants.MAX_SIZE){
                        Toast.makeText(SortAlgorithmActivity.this, "Too large number. Please input smaller number", Toast.LENGTH_LONG).show();
                        return;
                    }
                    updateSelectedNumber(number);
                    ensureArrayExists(number);
                    ExecutorService executor = Executors.newSingleThreadExecutor();
                    executor.submit(new Runnable() {
                        @Override
                        public void run() {
                            long[] arrayCopy = sharedArray.clone();
                            long result =
                                    performanceUtils.measureExecutionTime("Native", () -> {
                                return  qsort(arrayCopy,arrayCopy.length);
//                                 null;
                            });
                            System.out.println("Time taken by JNI QuickSort: " + (result) + " nanoseconds");
                            nativeResult=result;
                            // Update results to UI thread
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    nativeResultTV.setText(result+" ns");
                                    onChangeComparisonVisible(number);
                                    sortedArrayNativeView.setText(Arrays.toString(sortedArray));
                                }
                            });
                        }
                    });
                    executor.shutdown();
                }catch(Exception e){
                    Toast.makeText(SortAlgorithmActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void onHandleSearchByNative(){
        nativeSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sharedArray==null || sharedArray.length==0){
                    Toast.makeText(SortAlgorithmActivity.this, "Please generate array and sort before trying to search", Toast.LENGTH_LONG).show();
                    return;
                }
                String input = targetNumber.getText().toString();
                try {
                    int number = Integer.parseInt(input);
                    if(number >= AppConstants.MAX_SIZE){
                        Toast.makeText(SortAlgorithmActivity.this, "Too large number. Please input smaller number", Toast.LENGTH_LONG).show();
                        return;
                    }
//                    long result =performanceUtils.measureExecutionTime("Native", () -> {
//
//                        searchResult= searchResult.getResult();
//
//                    });
                    SearchResult searchResultModel = binarySearchAndMeasureTime(sortedArray,number);
                    searchResult= searchResultModel.getResult();
                    searchResultView.setText(searchResult+"");
                    long elapsedTime=searchResultModel.getElapsedTime();
                    searchNativeResult.setText(elapsedTime+" ns");
                    sortedArrayJavaView.setText(Arrays.toString(sortedArray));
                }catch (Exception e){
                    Toast.makeText(SortAlgorithmActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void onHandleSearchByJava(){
        javaSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sharedArray==null || sharedArray.length==0){
                    Toast.makeText(SortAlgorithmActivity.this, "Please generate array and sort before trying to search", Toast.LENGTH_LONG).show();
                    return;
                }
                String input = targetNumber.getText().toString();
                try {
                    int number = Integer.parseInt(input);
                    if(number >= AppConstants.MAX_SIZE){
                        Toast.makeText(SortAlgorithmActivity.this, "Too large number. Please input smaller number", Toast.LENGTH_LONG).show();
                        return;
                    }
                    long result =performanceUtils.measureExecutionTime("Native", () -> {
                        searchResult = controller.binarySearch(sortedArray,number);
                        return searchResult;
                    });
                    searchResultView.setText(searchResult+"");
                    nativeResult=result;
                    searchJavaResult.setText(result+" ns");
                    sortedArrayJavaView.setText(Arrays.toString(sortedArray));
                }catch (Exception e){
                    Toast.makeText(SortAlgorithmActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void ensureArrayExists(int size) {
//        if (sharedArray == null || sharedArray.length != size) {
            sharedArray = new long[size];
            for (int i = 0; i < size; i++) {
                sharedArray[i] =  size - i;
            }
            unsortedArrayView.setText(Arrays.toString(sharedArray));
//        }
    }
    void updateSelectedNumber(int value){
        if(value!=enteredNumber){
            enteredNumber=value;
        }
    }
    void onChangeComparisonVisible(int value){
        if(nativeResult!=0 && javaResult!=0){
            if(value==enteredNumber){
                String comparison = "";
                if (javaResult < nativeResult) {
                    comparison = "<";
                } else if (javaResult > nativeResult) {
                    comparison = ">";
                } else {
                    comparison = "=";
                }
                comparisonText.setText("Java " +  comparison + "Native");
            }
        }
    }
    // native methods
    public native  void nativeSort(int[] array);
    public native int[] bubbleSortJNI(int[] array);
//    public native void nativeQuickSort(int[] array);
    public native long[] qsort(long[] array,long arrayLength);

    public native int binarySearch(int[] array,int arrayLength,int target);
    public native SearchResult binarySearchAndMeasureTime(long[] array, long target);
}