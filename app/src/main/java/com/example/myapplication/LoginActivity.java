package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth; //파이어베이스 인증
    private DatabaseReference mDatabaseRef ; //실시간데이터베이스
    private EditText Id_edit,Pass_edit;
    private Button signButton,loginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();

        Id_edit = findViewById(R.id.Id_edit);
        Pass_edit = findViewById(R.id.Pass_edit);
        loginButton = findViewById(R.id.login_btn);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //로그인 요청
                String strEmail = Id_edit.getText().toString();
                String strPwd = Pass_edit.getText().toString();
                
                mFirebaseAuth.signInWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            // 로그인 성공 !
                            Toast.makeText(LoginActivity.this,"로그인 성공!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, CmlistActivity.class);
                            startActivity(intent);
                            finish(); //현재 액티비티 파괴
                        } else{
                            Toast.makeText(LoginActivity.this,"로그인 실패...", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        signButton = findViewById(R.id.sign_btn);
        signButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //회원가입 요청
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }
        });
    }
}