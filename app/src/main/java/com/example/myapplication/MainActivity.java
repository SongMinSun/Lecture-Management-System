package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<ClassAccount> arrayList;
    private DatabaseReference databaseReference;
    private FirebaseAuth mFirebaseAuth;
    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cm_list);

        logout = findViewById(R.id.logoutBtn);
        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mFirebaseAuth.getCurrentUser();

        recyclerView = findViewById(R.id.classBtn);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();

        if (currentUser != null) {
            String userId = currentUser.getUid();

            DatabaseReference database = FirebaseDatabase.getInstance().getReference("class");

            database.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    arrayList.clear();

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Log.d("MainActivity", "Snapshot: " + snapshot.toString());
                        String classIdToken = snapshot.child("idToken").getValue(String.class);
                        if (classIdToken != null && classIdToken.equals(userId)) {
                            ClassAccount classAccount = new ClassAccount();
                            classAccount.setClassname(snapshot.child("classname").getValue(String.class));
                            classAccount.setClassnum(String.valueOf(snapshot.child("classnum").getValue()));
                            classAccount.setClassday(snapshot.child("classday").getValue(String.class));
                            classAccount.setClasstime(snapshot.child("classtime").getValue(String.class));
                            classAccount.setIdToken(classIdToken);

                            arrayList.add(classAccount);
                        }
                    }

                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("MainActivity", String.valueOf(error.toException()));
                }
            });

            adapter = new ClassAdapter(arrayList, MainActivity.this);

            // 아이템 클릭 이벤트 처리
            ((ClassAdapter) adapter).setOnItemClickListener(new ClassAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    ClassAccount selectedItem = arrayList.get(position);

                    Intent intent = new Intent(MainActivity.this, SelectActivity.class);
                    intent.putExtra("classname", selectedItem.getClassname());
                    intent.putExtra("classnum", selectedItem.getClassnum());
                    intent.putExtra("classday", selectedItem.getClassday());
                    intent.putExtra("classtime", selectedItem.getClasstime());
                    startActivity(intent);
                }
            });

            recyclerView.setAdapter(adapter);
        }

        logout.setOnClickListener(view -> {
            mFirebaseAuth.signOut();
            Toast.makeText(MainActivity.this,"로그아웃 완료",Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
