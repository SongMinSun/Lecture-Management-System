package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SelectActivity extends AppCompatActivity {
    private Button chbtn;
    private String className;
    private String classNum;
    private String classDay;
    private String classTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        chbtn = findViewById(R.id.main_chbtn);

        Intent intent = getIntent();
        if (intent != null) {
            className = intent.getStringExtra("classname");
            classNum = intent.getStringExtra("classnum");
            classDay = intent.getStringExtra("classday");
            classTime = intent.getStringExtra("classtime");

            TextView mainNameTextView = findViewById(R.id.ClassName);
            mainNameTextView.setText(className);
        }

        chbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ahActivity로 전달할 Intent 생성
                Intent ahActivityIntent = new Intent(SelectActivity.this, ahActivity.class);
                ahActivityIntent.putExtra("classname", className);
                ahActivityIntent.putExtra("classnum", classNum);
                ahActivityIntent.putExtra("classday", classDay);
                ahActivityIntent.putExtra("classtime", classTime);
                startActivity(ahActivityIntent);
            }
        });
    }
}
