package com.example.jniexample.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jniexample.Controllers.BasicController;
import com.example.jniexample.R;
import com.example.jniexample.Utils.ImageUtils;
import com.example.jniexample.Utils.PerformanceUtils;
import com.example.jniexample.Utils.ToolBarUtils;
import com.example.jniexample.contracts.ImageLoadCallback;
import com.example.jniexample.databinding.ActivityImageTestBinding;

public class ImageTestActivity extends AppCompatActivity implements ImageLoadCallback {
    ActivityImageTestBinding binding;
    private Button pgnButton, webpButton;
    private ImageView pngImageView, webpImageView;
    private TextView pngView, webpView;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_image_test);
        binding = ActivityImageTestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setBinding();
        initView();
        onHandleClick();
    }
    private void setBinding()
    {
        pngView = binding.pgnImageLoadTime;
        webpView = binding.webpImageLoadTime;
        pngImageView = binding.pngImageView;
        webpImageView = binding.webpImageView;
        pgnButton = binding.pgnButton;
        webpButton = binding.webpButton;
        toolbar = binding.toolBarImageTest;
    }
    private void initView()
    {
        setSupportActionBar(toolbar);
        ToolBarUtils.setupBasicToolbar(toolbar,() -> finish());
    }
    private void onHandleClick()
    {
        pgnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageUtils.testImageLoadPNG(ImageTestActivity.this,pngImageView,  (ImageLoadCallback) ImageTestActivity.this);
            }
        });

        webpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageUtils.testImageLoadWebP(ImageTestActivity.this,webpImageView,  (ImageLoadCallback) ImageTestActivity.this);
            }
        });
    }

    @Override
    public void onWebpImageLoadTime(long loadTime) {
        webpView.setText(loadTime + "ms");
    }

    @Override
    public void onPngImageLoadTime(long loadTime) {
        pngView.setText(loadTime + "ms");
    }
}