package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class cmActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<ClassAccount> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(getApplicationContext());
        setContentView(R.layout.cm_list);

        recyclerView = findViewById(R.id.classBtn);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>(); // class 객체 담을 어레이 리스트

        database = FirebaseDatabase.getInstance(); //파이어베이스 데이터베이스 연동

        databaseReference = database.getReference("class"); //DB 테이블 연결
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear(); // 기존 배열리스트가 존재하지 않게 초기화
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){ //반복문으로 데이터 List 추출
                    Log.d("cmActivity", "Snapshot: " + snapshot.toString());
                    ClassAccount classAccount = new ClassAccount();
                    classAccount.setClassname(snapshot.child("classname").getValue(String.class));
                    classAccount.setClassnum(String.valueOf(snapshot.child("classnum").getValue())); // Long을 String으로 변환
                    classAccount.setClassday(snapshot.child("classday").getValue(String.class));
                    classAccount.setClasstime(snapshot.child("classtime").getValue(String.class));
                    classAccount.setIdToken(snapshot.child("idToken").getValue(String.class));
                    Log.d("cmActivity", "ClassAccount: " + classAccount);

                    arrayList.add(classAccount);//담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼 준비
                }
                adapter.notifyDataSetChanged(); //리스트 저장 및 새로고침
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("cmActivity", String.valueOf(error.toException()));

            }
        });

        adapter = new ClassAdapter(arrayList, this);
        recyclerView.setAdapter(adapter); //리사이클러뷰에 어뎁터 연결

    }
}
