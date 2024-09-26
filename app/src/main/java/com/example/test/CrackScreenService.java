package com.example.test;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class CrackScreenService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Khi service khởi chạy, khởi động CrackScreenActivity
        Intent crackScreenIntent = new Intent(this, CrackScreenActivity.class);
        crackScreenIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(crackScreenIntent);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}