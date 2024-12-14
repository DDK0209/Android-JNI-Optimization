package com.example.jniexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jniexample.Utils.PerformanceUtils;
import com.example.jniexample.Views.OtherAlgorithmActivity;
import com.example.jniexample.Views.SortAlgorithmActivity;
import com.example.jniexample.databinding.ActivityMainBinding;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'jniexample' library on application startup.
    static {
        System.loadLibrary("jniexample");
    }



    private ActivityMainBinding binding;

    PerformanceUtils performanceUtils;


    Button searchBinaryButton,bundleSortButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        bindings();
        initialize();
        onClick();
    }
    private void initialize(){
        performanceUtils= new PerformanceUtils();
    }
    private void bindings(){
        searchBinaryButton = binding.searchBinaryButton;
        bundleSortButton = binding.quickSortButton;
    }
    private void onClick(){
        bundleSortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SortAlgorithmActivity.class);
                startActivity(intent);
            }
        });

        searchBinaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, OtherAlgorithmActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * A native method that is implemented by the 'jniexample' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}