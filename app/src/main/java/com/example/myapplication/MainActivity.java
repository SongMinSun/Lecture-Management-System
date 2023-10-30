package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button cmbtn, chbtn, ambtn, ahbtn, logout;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        cmbtn = findViewById(R.id.main_cmbtn);
        chbtn = findViewById(R.id.main_chbtn);
        ambtn = findViewById(R.id.main_ambtn);
        ahbtn = findViewById(R.id.main_ahbtn);

        cmbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, cmActivity.class);
                startActivity(intent);
            }
        });
        chbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, chActivity.class);
                startActivity(intent);
            }
        });
        ambtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, amActivity.class);
                startActivity(intent);
            }
        });
        ahbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, ahActivity.class);
                startActivity(intent);
            }
        });
    }
}
