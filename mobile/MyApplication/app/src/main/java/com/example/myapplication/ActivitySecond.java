package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class ActivitySecond extends AppCompatActivity {

    TextView welcomeTxv, luckyNumberTxv;
    Button click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        welcomeTxv = findViewById(R.id.textView2);
        luckyNumberTxv = findViewById(R.id.luckyNumber);
        click = findViewById(R.id.button2);

        Intent intent = getIntent();
        String userName = intent.getStringExtra("name");
        int random_num = generateNumBer();
        luckyNumberTxv.setText(""+random_num);

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareData(userName, random_num);
            }
        });
    }

    private void ShareData(String userName, int random_num) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, userName+ " , your lucky number is" +random_num);
        startActivity(Intent.createChooser(intent, "choose a platform"));
    }

    private int generateNumBer() {
        Random random = new Random();
        int upper = 1000;
        int random_num = random.nextInt(upper);
        return random_num;
    }
}