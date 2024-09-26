package com.example.test;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class CrackScreen extends Service {
    private Button mButton;
    private ImageView mImage;

    @Override
    public IBinder onBind(Intent paramIntent) {
        return null;
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onCreate() {
        super.onCreate();

        // Check if permission is granted to draw overlays
        if (!Settings.canDrawOverlays(this)) {
            Toast.makeText(this, "Permission required to show overlay", Toast.LENGTH_SHORT).show();
            stopSelf();
            return;
        }

        // Initialize views
        mButton = new Button(this);
        mImage = new ImageView(this);
        mButton.setText(".");
        mButton.setTextColor(-12303292);
        mButton.setVisibility(View.VISIBLE);
        mButton.setBackgroundColor(0);
        mButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                stopSelf();
            }
        });
        mImage.setImageResource(R.drawable.liquid_crystal_spill);
        mImage.setScaleType(ImageView.ScaleType.FIT_XY);

        // Create layout parameters for the button
        WindowManager.LayoutParams paramsButton = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
                        ? WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
                        : WindowManager.LayoutParams.TYPE_PHONE, // For pre-Oreo devices
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        paramsButton.gravity = Gravity.TOP | Gravity.LEFT;
        paramsButton.x = 100; // Adjust the position
        paramsButton.y = 100; // Adjust the position

        // Create layout parameters for the image
        WindowManager.LayoutParams paramsImage = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
                        ? WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
                        : WindowManager.LayoutParams.TYPE_PHONE, // For pre-Oreo devices
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        paramsImage.gravity = Gravity.CENTER;

        // Get WindowManager and add views
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        windowManager.addView(mButton, paramsButton);
        windowManager.addView(mImage, paramsImage);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mButton != null) {
            ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).removeView(mButton);
            mButton = null;
        }
        if (mImage != null) {
            ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).removeView(mImage);
            mImage = null;
        }
    }
}
