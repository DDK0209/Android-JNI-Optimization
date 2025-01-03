package com.example.jniexample.Utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.jniexample.R;
import com.example.jniexample.contracts.ImageLoadCallback;

public class ImageUtils {
    public static void testImageLoadPNG(Context context, ImageView imageView, ImageLoadCallback callback) {
        long startTime = System.currentTimeMillis();
        Glide.with(context)
                .load(R.drawable.dashboard)  // ảnh PNG
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable com.bumptech.glide.request.transition.Transition<? super Drawable> transition) {
                        long endTime = System.currentTimeMillis();
                        long loadTime = endTime - startTime;
                        Log.d("Image Load WebP", "Time to load WebP image: " + loadTime + "ms");
                        callback.onPngImageLoadTime(loadTime);

                        imageView.setImageDrawable(resource);
                    }

                    @Override
                    public void onLoadCleared(Drawable placeholder) {
                        // Không cần xử lý trong trường hợp này
                    }
                });
    }


    public static void testImageLoadWebP(Context context, ImageView imageView, ImageLoadCallback callback) {
        long startTime = System.currentTimeMillis();
        Glide.with(context)
                .load(R.drawable.dashboard_webd)  // ảnh WebP
                .into(new CustomTarget<Drawable>() {

                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        long endTime = System.currentTimeMillis();
                        Log.d("Image Load WebP", "Time to load WebP image: " + (endTime - startTime) + "ms");
                        long loadTime = endTime - startTime;
                        Log.d("Image Load PNG", "Time to load PNG image: " + loadTime + "ms");
                        callback.onWebpImageLoadTime(loadTime);
                        imageView.setImageDrawable(resource);
                    }

                    @Override
                    public void onLoadCleared(Drawable placeholder) {
                        // Không cần xử lý trong trường hợp này
                    }
                });
    }

}
