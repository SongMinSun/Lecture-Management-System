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

public class chActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<ConcentrationAccount> arrayList;
    private DatabaseReference databaseReference;
    private FirebaseAuth mFirebaseAuth;
    private String classNum;
    private DatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ch_detail);

        recyclerView = findViewById(R.id.chR);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();
        adapter = new ConcentrationAdapter(this, arrayList); // Adapter 초기화

        datePicker = findViewById(R.id.daPicker2);
        Calendar calendar = Calendar.getInstance();
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                (view, year, monthOfYear, dayOfMonth) -> {
                    loadAttendanceData(year, monthOfYear + 1, dayOfMonth);
                });

        Intent intent = getIntent();
        classNum = intent.getStringExtra("classnum");

        loadAttendanceData(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // onResume 시에 RecyclerView를 업데이트
        adapter.notifyDataSetChanged();
    }

    private void loadAttendanceData(int year, int month, int day) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("concentration");

        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.d("chActivity", "Snapshot: " + snapshot.toString());

                    ConcentrationAccount concentrationAccount = new ConcentrationAccount();
                    concentrationAccount.setStudnetname(snapshot.child("studentname").getValue(String.class));
                    concentrationAccount.setStudentnum(String.valueOf(snapshot.child("studentnum").getValue()));
                    concentrationAccount.setHigh_count(String.valueOf(snapshot.child("high_count").getValue()));
                    concentrationAccount.setLow_count(String.valueOf(snapshot.child("low_count").getValue()));
                    concentrationAccount.setCon_time(snapshot.child("con_time").getValue(String.class));
                    Object classNumObj = snapshot.child("classnum").getValue();
                    String databaseClassNum = (classNumObj != null) ? String.valueOf(classNumObj) : null;
                    String databaseConcentrationDate = snapshot.child("con_date").getValue(String.class);

                    if (classNum != null && classNum.equals(databaseClassNum) &&
                            isSameDate(year, month, day, databaseConcentrationDate)) {
                        arrayList.add(concentrationAccount);
                    }
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("chActivity", String.valueOf(error.toException()));
            }
        });
    }

    private boolean isSameDate(int year, int month, int day, String dateString) {
        // dateString이 null이거나 빈 문자열인 경우 false를 반환
        if (dateString == null || dateString.isEmpty()) {
            return false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date;

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
