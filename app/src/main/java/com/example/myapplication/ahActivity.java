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
        Calendar calendar = Calendar.getInstance();
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                (view, year, monthOfYear, dayOfMonth) -> {
                    loadAttendanceData(year, monthOfYear + 1, dayOfMonth);
                });

        Intent intent = getIntent();
        classNum = intent.getStringExtra("classnum");

        loadAttendanceData(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));

        // 아래 코드 추가
        adapter = new AttendanceAdapter(arrayList, ahActivity.this);
        ((AttendanceAdapter) adapter).setOnItemClickListener(new AttendanceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                AttendanceAccount selectedItem = arrayList.get(position);

                Intent intent = new Intent(ahActivity.this, ModifyAttendActivity.class);
                intent.putExtra("studentname", selectedItem.getStudentname());
                intent.putExtra("studentnum", selectedItem.getStudentnum());
                intent.putExtra("attend", selectedItem.getAttend());
                intent.putExtra("classnum", selectedItem.getClassnum());
                startActivity(intent);
                finish();
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // onResume 시에 RecyclerView를 업데이트
        adapter.notifyDataSetChanged();
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
                    Object classNumObj = snapshot.child("classnum").getValue();
                    String databaseClassNum = (classNumObj != null) ? String.valueOf(classNumObj) : null;
                    String databaseAttendDate = snapshot.child("attend_date").getValue(String.class);

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
