package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class CrackScreenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CrackScreenView crackScreenView = new CrackScreenView(this);
        setContentView(crackScreenView);

        // Tự động tắt activity sau 5 giây hoặc khi người dùng chạm vào
        new Handler().postDelayed(() -> {
            stopService(new Intent(this, CrackScreenService.class));
            finish();
        }, 5000);  // 5 giây
    }
}