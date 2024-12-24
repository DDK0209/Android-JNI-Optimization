package com.example.jniexample.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
import com.example.jniexample.databinding.ActivityMatrixBinding;
import com.example.jniexample.databinding.ActivityOtherAlgorithmBinding;

import java.util.Arrays;

public class MatrixActivity extends AppCompatActivity {
    ActivityMatrixBinding binding;

    private PerformanceUtils performanceUtils;
    private BasicController controller;

    // UI components
    private Button genMatrix1Bt,genGenMatrix2Bt;
    private Button multiMatrixByJavaBt,multiMatrixByNativeBt;
    private EditText inputText;
    private Toolbar toolbar;
    private TextView javaMatrixResult,nativeMatrixResult;
    //
    int[][] matrixA,matrixB;
    int[] firstArray,secondArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrix);
        binding = ActivityMatrixBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getBinding();
        initialize();
        onHandleGenClick();
        onHandlePerformMultiClick();
    }
    private void getBinding()
    {
        inputText = binding.inputLength;
        genMatrix1Bt = binding.genMatrix1Button;
        genGenMatrix2Bt = binding.genMatrix2Button;
        multiMatrixByJavaBt = binding.matrixMultiplicationJava;
        multiMatrixByNativeBt = binding.matrixMultiplicationNative;
        toolbar = binding.toolBarMatrix;
        javaMatrixResult = binding.javaMatrixResult;
        nativeMatrixResult = binding.nativeMatrixResult;
    }
    private void initialize(){
        setSupportActionBar(toolbar);
        ToolBarUtils.setupBasicToolbar(toolbar,() -> finish());
        performanceUtils =new PerformanceUtils();
        controller = new BasicController();
    }
    private void onHandleGenClick()
    {
        genMatrix1Bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String inputValue = inputText.getText().toString();
                    int value = Integer.parseInt(inputValue);
                    matrixA = new int[value][value];
                    matrixA = controller.generateRandomMatrix(value);
                    firstArray = controller.convert2DTo1D(matrixA);
                    Toast.makeText(MatrixActivity.this, "Create matrix one successfully", Toast.LENGTH_SHORT).show();
                }catch (Exception e)
                {
                    Toast.makeText(MatrixActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
                }
            }
        });
        genGenMatrix2Bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String inputValue = inputText.getText().toString();
                    int value = Integer.parseInt(inputValue);
                    matrixB = new int[value][value];
                    matrixB = controller.generateRandomMatrix(value);
                    secondArray = controller.convert2DTo1D(matrixB);
                    Toast.makeText(MatrixActivity.this, "Create matrix two successfully", Toast.LENGTH_SHORT).show();
                }catch (Exception e)
                {
                    Toast.makeText(MatrixActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void onHandlePerformMultiClick()
    {
        multiMatrixByJavaBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String inputValue = inputText.getText().toString();
                    int value = Integer.parseInt(inputValue);
                    if(matrixA.length<1 || matrixB.length <1 || inputText.getText().toString().isEmpty())
                    {
                        Toast.makeText(MatrixActivity.this, "Please input", Toast.LENGTH_SHORT).show();
                    }else{
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // Code chạy trên background thread
                                long result = performanceUtils.measureExecutionTime("Java", () ->
                                        controller.multiplyMatricesJava(firstArray,secondArray, value)
                                );
                                // Quay lại UI thread
                                runOnUiThread(() -> {
                                    double elapsedTimeByMilisecond = (double) result / 1000000;
                                    javaMatrixResult.setText(elapsedTimeByMilisecond+" ms");
                                });
                            }
                        }).start();
                    }
                }catch(Exception e)
                {
                    Toast.makeText(MatrixActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
                }


            }
        });

        multiMatrixByNativeBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    String inputValue = inputText.getText().toString();
                    int value = Integer.parseInt(inputValue);
                    if(matrixA.length<1 || matrixB.length <1 || inputText.getText().toString().isEmpty())
                    {
                        Toast.makeText(MatrixActivity.this, "Please input", Toast.LENGTH_SHORT).show();
                    }else{
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // Code chạy trên background thread
                                long result = performanceUtils.measureExecutionTime("Java", () -> multiplyMatricesNative(firstArray,secondArray,value));
                                // Quay lại UI thread
                                runOnUiThread(() -> {
                                    double elapsedTimeByMilisecond = (double) result / 1000000;
                                    nativeMatrixResult.setText(elapsedTimeByMilisecond+" ms");
                                });
                            }
                        }).start();
                    }
                }catch(Exception e)
                {
                    Toast.makeText(MatrixActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
                }



            }
        });
    }


    // native methods
    public static native int[] multiplyMatricesNative(int[] a, int[] b, int n);

}