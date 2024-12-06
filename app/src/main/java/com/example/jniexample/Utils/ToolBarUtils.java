package com.example.jniexample.Utils;

import android.view.View;

import androidx.appcompat.widget.Toolbar;

import com.example.jniexample.R;

public class ToolBarUtils {
    public static void setupBasicToolbar(Toolbar toolbar, Runnable onFinish){

        toolbar.setNavigationIcon(R.drawable.black_arrow_back);
        toolbar.setNavigationContentDescription("Back");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFinish.run();
            }
        });
    }
}
