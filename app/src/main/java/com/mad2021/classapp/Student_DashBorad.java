package com.mad2021.classapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

public class Student_DashBorad  extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<ClassData> classData;
    private com.mad2021.classapp.StudentAdapter studentAdapter;
    DatabaseReference dbRef;
    Button jBtnDash,myClassB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashborad);

        recyclerView = findViewById(R.id.recycler2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        classData = new  ArrayList<ClassData>();

        dbRef = FirebaseDatabase.getInstance().getReference().child("Class1");
        dbRef.addListenerForSingleValueEvent(valueEventListener);

        jBtnDash = findViewById(R.id.jBtnDash);

        jBtnDash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(com.mad2021.classapp.Student_DashBorad.this,SJoinClass.class);
                startActivity(i);
                finish();
            }
        });
    
    }



    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            for (DataSnapshot snapshot1: snapshot.getChildren()){
                ClassData uData = snapshot1.getValue(ClassData.class);
                classData.add(uData);
            }
            studentAdapter= new com.mad2021.classapp.StudentAdapter(com.mad2021.classapp.Student_DashBorad.this,classData);
            recyclerView.setAdapter(studentAdapter);


        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

}