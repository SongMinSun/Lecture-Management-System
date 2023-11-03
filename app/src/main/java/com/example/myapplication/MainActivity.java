package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private Button cmbtn, chbtn, ambtn, ahbtn, logout;
    private TextView main_name;
    private FirebaseAuth mFirebaseAuth; //파이어베이스 인증
    private DatabaseReference mDatabaseRef; //실시간 데이터 베이스

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(getApplicationContext());
        setContentView(R.layout.main);

        mFirebaseAuth = FirebaseAuth.getInstance();

        cmbtn = findViewById(R.id.main_cmbtn);
        chbtn = findViewById(R.id.main_chbtn);
        ambtn = findViewById(R.id.main_ambtn);
        ahbtn = findViewById(R.id.main_ahbtn);
        logout = findViewById(R.id.main_logout);

        FirebaseUser currentUser = mFirebaseAuth.getCurrentUser();

        if (currentUser != null) {
            String userId = currentUser.getUid();
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Member").child(userId);

            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String userName = dataSnapshot.child("name").getValue(String.class);
                        TextView nameTextView = findViewById(R.id.main_name); // TextView의 ID를 실제 레이아웃에서 정의한 ID로 바꿔주세요
                        nameTextView.setText(userName);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // 데이터베이스에서 데이터 읽기가 실패한 경우
                    Log.w("TAG", "데이터베이스에서 데이터 읽기 실패", databaseError.toException());
                }
            });
        }

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
