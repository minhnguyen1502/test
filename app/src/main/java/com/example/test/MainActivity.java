package com.example.test;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Spinner spinner = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.theme_array, android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new MyOnItemSelectedListener());

        final Runnable mMyRunnable = new Runnable() {
            @Override
            public void run(){
                Intent svc = new Intent(MainActivity.this, CrackScreen.class);
                startService(svc);
                finish();
            }
        };

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button buttonClick = (Button) findViewById(R.id.prank);
        buttonClick.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                EditText delay = (EditText) findViewById(R.id.delay);
                String str = delay.getText().toString();
                //prevent zero value
                if(str.equals("")){
                    delay.setText("0");
                }
                int dd = Integer.parseInt(delay.getText().toString());
                int de = dd * 1000;
                if (de > 0){
                }

                Handler myHandler = new Handler();
                myHandler.postDelayed(mMyRunnable, de);
                //start the service

                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
            }
        });
    }

    public class MyOnItemSelectedListener implements OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent,
                                   View view, int pos, long id) {
            Toast.makeText(parent.getContext(), "The theme is " +
                    parent.getItemAtPosition(pos).toString(), Toast.LENGTH_LONG).show();
        }

        public void onNothingSelected(AdapterView parent) {
        }
    }
}
//
//@Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        // Khi nhấn nút, kích hoạt Service để tạo hiệu ứng nứt màn hình
//        Button crackButton = findViewById(R.id.startOverlayButton);
//        crackButton.setOnClickListener(v -> {
//            Intent intent = new Intent(MainActivity.this, CrackScreenService.class);
//            startService(intent);
//        });
//    }
//}