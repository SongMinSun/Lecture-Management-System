package com.example.myapplication;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ModifyAttendActivity extends AppCompatActivity {

    private TextView studentNameTextView, studentNumTextView, attendStateTextView;
    private Button attendYesButton, attendNoButton;
    private DatabaseReference databaseReference;
    private String classNum;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_attend);

        studentNameTextView = findViewById(R.id.modify_studentName2);
        studentNumTextView = findViewById(R.id.modify_studentNum2);
        attendStateTextView = findViewById(R.id.modify_attendState2);
        attendYesButton = findViewById(R.id.modify_attendYes);
        attendNoButton = findViewById(R.id.modify_attendNo);

        databaseReference = FirebaseDatabase.getInstance().getReference("attendance");

        String studentName = getIntent().getStringExtra("studentname");
        String studentnum = getIntent().getStringExtra("studentnum");
        String attend = getIntent().getStringExtra("attend");
        classNum = getIntent().getStringExtra("classnum");

        studentNameTextView.setText(studentName);
        studentNumTextView.setText(studentnum);
        attendStateTextView.setText(attend);

        attendYesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 출석 상태를 "yes"로 업데이트합니다.
                updateAttendState(studentName,studentnum, "yes");
            }
        });

        attendNoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 출석 상태를 "no"로 업데이트합니다.
                updateAttendState(studentName, studentnum, "no");
            }
        });
    }

    private void updateAttendState(String studnetname,String studentnum, String attend) {
        // 해당 학생의 출석 상태만 업데이트합니다.
        String student = studnetname+studentnum;
        databaseReference.child(student).child("attend").setValue(attend)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // 업데이트가 성공하면 메시지를 로그에 기록합니다.
                        Log.d("ModifyAttendActivity", "Attendance state updated successfully");

                        // TextView를 업데이트하여 새로운 출석 상태를 반영합니다.
                        attendStateTextView.setText(attend);

                        finish();

                    } else {
                        // 업데이트가 실패하면 에러 메시지를 로그에 기록합니다.
                        Log.e("ModifyAttendActivity", "Failed to update attendance state: " + task.getException());
                        // 필요에 따라 업데이트 실패에 대한 처리 로직을 추가할 수 있습니다.
                    }
                });
    }
}
