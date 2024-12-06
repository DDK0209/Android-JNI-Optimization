package com.example.jniexample.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jniexample.Controllers.BasicController;
import com.example.jniexample.R;
import com.example.jniexample.Utils.PerformanceUtils;
import com.example.jniexample.Utils.ToolBarUtils;
import com.example.jniexample.databinding.ActivityOtherAlgorithmBinding;
public class OtherAlgorithmActivity extends AppCompatActivity {

    private PerformanceUtils performanceUtils;
    private BasicController controller;
    ActivityOtherAlgorithmBinding binding;


    // UI components
    private EditText inputNumber;
    private Button fibonacciJavaBtn,fibonacciNativeBtn;

    private Button circleAreaJavaBtn, circleAreaNativeBtn;
    private TextView nativeResultTV,javaResultTV;
    Toolbar toolbar;

    private EditText inputCheckingNumber;
    private Button jniDelayButton;
    private Button memoryAccessJavaBtn,memoryAccessNativeBtn;
    private Button stringProcessingJavaBtn,stringProcessingNativeBtn;
    private TextView nativeCheckingResultTV,javaCheckingResultTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_other_algorithm);
        binding = ActivityOtherAlgorithmBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        bindings();
        initialize();
        onHandleClick();
        handleClick();
    }

    private void initialize(){
        setSupportActionBar(toolbar);
        ToolBarUtils.setupBasicToolbar(toolbar,() -> finish());
        performanceUtils =new PerformanceUtils();
        controller = new BasicController();
    }


    private void bindings(){
        toolbar = binding.toolBarOtherView;
        inputNumber= (EditText) binding.inputNumber;
        fibonacciNativeBtn = (Button) binding.fibonacciNativeButton;
        fibonacciJavaBtn = (Button) binding.fibonacciJavaButton;
        circleAreaNativeBtn = (Button) binding.circleAreaNativeButton;
        circleAreaJavaBtn = (Button) binding.circleAreaJavaButton;
        nativeResultTV = (TextView) binding.nativeResultView;
        javaResultTV = (TextView) binding.javaResultView;
//
        inputCheckingNumber = (EditText) binding.inputChecking;
        jniDelayButton = (Button) binding.jniDelayButton;
        memoryAccessJavaBtn = (Button) binding.testMemoryAccessJavaButton;
        memoryAccessNativeBtn = (Button) binding.testMemoryAccessNativeButton;
        stringProcessingJavaBtn = (Button) binding.stringProcessingJavaButton;
        stringProcessingNativeBtn = (Button) binding.stringProcessingNativeButton;
        nativeCheckingResultTV = (TextView) binding.nativeCheckingResultView;
        javaCheckingResultTV = (TextView) binding.javaCheckingResultView;
    }

    private void onHandleClick(){
        onHandleFibonacciJavaClick();
        onHandleFibonacciNativeClick();
        onHandleCircleAreaJavaClick();
        onHandleCircleAreaNativeClick();
    }
    private void onHandleFibonacciJavaClick(){
        fibonacciJavaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    long number = Integer.parseInt(inputNumber.getText().toString());
                    long result = performanceUtils.measureExecutionTime("Java", () -> controller.fibonacciJava(number));
                    javaResultTV.setText(result+" ns");
                }catch (Exception e){
                    Toast.makeText(OtherAlgorithmActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void onHandleFibonacciNativeClick(){
        fibonacciNativeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int number = Integer.parseInt(inputNumber.getText().toString());
                    long result = performanceUtils.measureExecutionTime("Native", () -> fibonacciNative(number));
                    nativeResultTV.setText(result+" ns");
                }catch (Exception e){
                    Toast.makeText(OtherAlgorithmActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void onHandleCircleAreaJavaClick(){
        circleAreaJavaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    double radius = Double.parseDouble(inputNumber.getText().toString());
                    long result = performanceUtils.measureExecutionTime("Java", () -> controller.calculateCircleAreaJava(radius));
                    javaResultTV.setText(result+" ns");
                }catch (Exception e){
                    Toast.makeText(OtherAlgorithmActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    private void onHandleCircleAreaNativeClick(){
        circleAreaNativeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    double radius = Double.parseDouble(inputNumber.getText().toString());
                    long result = performanceUtils.measureExecutionTime("Native", () -> calculateCircleAreaNative(radius));
                    nativeResultTV.setText(result+" ns");
                }catch (Exception e){
                    Toast.makeText(OtherAlgorithmActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    private void handleClick(){
        handleJniDelayClick();
        handleMemoryAccessClick();
        handleStringProcessingClick();
    }
    private void handleJniDelayClick(){
        jniDelayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int number = Integer.parseInt(inputCheckingNumber.getText().toString());
                    if(number>0){
                        long jniLatency = performanceUtils.measureExecutionTime("JNI Delay",() -> {
                            for (int i = 0; i < number; i++) {
                                callEmptyNativeFunction();
                            }
                            return null;
                        });
                        javaCheckingResultTV.setText(String.format("JNI Latency: %d ns\n", jniLatency));
                    }else{
                        Toast.makeText(OtherAlgorithmActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception e){
                    Toast.makeText(OtherAlgorithmActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
    private void handleMemoryAccessClick(){
        memoryAccessJavaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              try {
                    int number = Integer.parseInt(inputCheckingNumber.getText().toString());
                    long javaIntTime = performanceUtils.measureExecutionTime("Memory access java",() -> controller.javaMemoryAccessTest(number));
                    javaCheckingResultTV.setText(String.format("Memory access: %d ns\n", javaIntTime));
                }catch(Exception e){
                    Toast.makeText(OtherAlgorithmActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
                }
            }
        });

        memoryAccessNativeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int number = Integer.parseInt(inputCheckingNumber.getText().toString());
                    long nativeIntTime = nativeMemoryAccessTest(number);
                    nativeCheckingResultTV.setText(String.format("Memory access: %d ns\n", nativeIntTime));
                }catch(Exception e){
                    Toast.makeText(OtherAlgorithmActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void handleStringProcessingClick(){
        stringProcessingJavaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int number = Integer.parseInt(inputCheckingNumber.getText().toString());
                    long javaStringTime = performanceUtils.measureExecutionTime("String Processing Java",() -> controller.javaStringProcessingTest(number)); ;
                    javaCheckingResultTV.setText(String.format("String processing: %d ns\n", javaStringTime));
                }catch(Exception e){
                    Toast.makeText(OtherAlgorithmActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
                }

            }
        });

        stringProcessingNativeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int number = Integer.parseInt(inputCheckingNumber.getText().toString());
                    long nativeStringTime =  performanceUtils.measureExecutionTime("String Processing Native",() -> nativeStringProcessingTest(number));
                    nativeCheckingResultTV.setText(String.format("String processing: %d ns\n", nativeStringTime));
                }catch (Exception e){
                    Toast.makeText(OtherAlgorithmActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    /**
     * A native method that is implemented by the 'demo1' native library,
     * which is packaged with this application.
     */

    public native int fibonacciNative(long number);

    public native double calculateCircleAreaNative(double radius);

    public native long nativeMemoryAccessTest(int size);

    public native long nativeStringProcessingTest(int length);

    public native void callEmptyNativeFunction();
}