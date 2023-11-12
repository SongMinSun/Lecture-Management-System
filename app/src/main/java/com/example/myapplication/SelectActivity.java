package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Intent intent = getIntent();
        if (intent != null) {
            String className = intent.getStringExtra("classname");
            String classNum = intent.getStringExtra("classnum");
            String classDay = intent.getStringExtra("classday");
            String classTime = intent.getStringExtra("classtime");

            TextView mainNameTextView = findViewById(R.id.ClassName);
            mainNameTextView.setText(className);

            // 나머지 데이터도 필요에 따라 표시
        }
    }
}
