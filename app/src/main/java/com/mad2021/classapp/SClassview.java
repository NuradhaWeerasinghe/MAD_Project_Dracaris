package com.mad2021.classapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SClassview  extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<StudentData> studentData;
    private com.mad2021.classapp.SGetStudentAdapter sGetStudentAdapter;
    DatabaseReference dbRef;
    TextView ecName;
    String className;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_classview);

        ecName = findViewById(R.id.sTop);
        Intent i = getIntent();
        className = i.getStringExtra("className");
        ecName.setText(className);

        recyclerView = findViewById(R.id.recycler4);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        studentData = new  ArrayList<StudentData>();

        dbRef = FirebaseDatabase.getInstance().getReference().child("Students");
        dbRef.addListenerForSingleValueEvent(valueEventListener);




    }



    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            for (DataSnapshot snapshot1: snapshot.getChildren()){
                StudentData uData = snapshot1.getValue(StudentData.class);
                studentData.add(uData);
            }
            sGetStudentAdapter= new com.mad2021.classapp.SGetStudentAdapter(com.mad2021.classapp.SClassview.this,studentData);
            recyclerView.setAdapter(sGetStudentAdapter);


        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

}