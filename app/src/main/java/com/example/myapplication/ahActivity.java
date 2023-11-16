package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ahActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<AttendanceAccount> arrayList;
    private DatabaseReference databaseReference;
    private FirebaseAuth mFirebaseAuth;
    private String classNum;
    private DatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ah_detail);

        mFirebaseAuth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.ahR);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();

        datePicker = findViewById(R.id.daPicker);
        // DatePicker의 초기 날짜를 현재 날짜로 설정
        Calendar calendar = Calendar.getInstance();
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                (view, year, monthOfYear, dayOfMonth) -> {
                    // DatePicker의 날짜가 변경될 때 호출되는 메소드
                    // 선택된 날짜로 데이터를 다시 불러와서 RecyclerView 갱신
                    loadAttendanceData(year, monthOfYear + 1, dayOfMonth);
                });

        Intent intent = getIntent();
        classNum = intent.getStringExtra("classnum");

        // 초기에는 현재 날짜의 데이터를 불러옴
        loadAttendanceData(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
    }

    private void loadAttendanceData(int year, int month, int day) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("attendance");

        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.d("ahActivity", "Snapshot: " + snapshot.toString());

                    AttendanceAccount attendanceAccount = new AttendanceAccount();
                    attendanceAccount.setStudentname(snapshot.child("studentname").getValue(String.class));
                    attendanceAccount.setStudentnum(String.valueOf(snapshot.child("studentnum").getValue()));
                    attendanceAccount.setAttend(snapshot.child("attend").getValue(String.class));
                    // classnum 값이 Long일 경우 String으로 변환
                    Object classNumObj = snapshot.child("classnum").getValue();
                    String databaseClassNum = (classNumObj != null) ? String.valueOf(classNumObj) : null;

                    String databaseAttendDate = snapshot.child("attend_date").getValue(String.class);

                    // classnum이 일치하고, attendDate가 선택한 날짜와 일치하는 경우에만 추가
                    if (classNum != null && classNum.equals(databaseClassNum) &&
                            isSameDate(year, month, day, databaseAttendDate)) {
                        arrayList.add(attendanceAccount);
                    }
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("ahActivity", String.valueOf(error.toException()));
            }
        });

        adapter = new AttendanceAdapter(arrayList, ahActivity.this);
        recyclerView.setAdapter(adapter);
    }

    private boolean isSameDate(int year, int month, int day, String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = null;
        try {
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return calendar.get(Calendar.YEAR) == year &&
                calendar.get(Calendar.MONTH) == month - 1 &&
                calendar.get(Calendar.DAY_OF_MONTH) == day;
    }
}

