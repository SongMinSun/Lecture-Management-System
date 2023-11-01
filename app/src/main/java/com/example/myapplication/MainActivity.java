package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private Button cmbtn, chbtn, ambtn, ahbtn, logout;
    private FirebaseAuth mFirebaseAuth; //파이어베이스 인증

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mFirebaseAuth = FirebaseAuth.getInstance();

        cmbtn = findViewById(R.id.main_cmbtn);
        chbtn = findViewById(R.id.main_chbtn);
        ambtn = findViewById(R.id.main_ambtn);
        ahbtn = findViewById(R.id.main_ahbtn);
        logout = findViewById(R.id.main_logout);

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
        logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                mFirebaseAuth.signOut();
                Toast.makeText(MainActivity.this,"로그아웃 완료",Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
