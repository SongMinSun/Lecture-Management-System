package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabaseRef;
    private EditText et_email;
    private EditText et_pass;
    private EditText et_name;
    private EditText et_phone;
    private EditText et_birth;
    private Button signup_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("face");


        et_email =  findViewById(R.id.et_email);
        et_pass = findViewById(R.id.et_pass);
        et_name = findViewById(R.id.et_name);
        et_phone = findViewById(R.id.et_phone);
        et_birth = findViewById(R.id.et_birth);

        signup_btn = (Button) findViewById(R.id.signup_btn);

        //firebaseAuth 진행
        firebaseAuth.createUserWithEmailAndPassword(et_email,et_pass,et_name,et_phone,et_birth).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {}


        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!et_email.getText().toString().equals("")&&!et_pass.getText().toString().equals("")&&!et_name.getText().toString().equals("")&&!et_phone.getText().toString().equals("")&&!et_birth.getText().toString().equals("")){
                    createUser(et_email.getText().toString(), et_pass.getText().toString(), et_name.getText().toString(),et_phone.getText().toString(), et_birth.getText().toString());
                } else {
                    // 이메일과 비밀번호가 공백인 경우
                    Toast.makeText(SignupActivity.this, "계정과 비밀번호를 입력하세요.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void createUser(String email, String password, String name, String phone, String birth){
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task){
                        if(task.isSuccessful()){
                            //회원가입 성공시
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Toast.makeText(SignupActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                            finish();
                        }else{
                            //계정 중복인 경우
                            Toast.makeText(SignupActivity.this,"이미 존재하는 계정입니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}