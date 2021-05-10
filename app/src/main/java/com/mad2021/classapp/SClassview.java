package com.mad2021.classapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

import java.util.ArrayList;

public class SClassview  extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<StudentData> studentData;
    private com.mad2021.classapp.SGetStudentAdapter sGetStudentAdapter;
    DatabaseReference dbRef;
    TextView ecName;
    String className,classId;
    Button studentBtnS,noticeBtnS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_classview);

        ecName = findViewById(R.id.sTop);
        Intent i = getIntent();
        className = i.getStringExtra("className");
        classId = i.getStringExtra("classId");
        ecName.setText(className);

        recyclerView = findViewById(R.id.recycler4);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        studentData = new  ArrayList<StudentData>();

        dbRef = FirebaseDatabase.getInstance().getReference().child("Class1").child("CF0jx7R9TAgOkPqEruhr5KFZwYA2").child(classId).child("Students");
        //dbRef = FirebaseDatabase.getInstance().getReference().child("Students");
        dbRef.addListenerForSingleValueEvent(valueEventListener);

        studentBtnS = findViewById(R.id.studentBtnS);
        noticeBtnS  = findViewById(R.id.noticeBtnS);

        //Student top button
        studentBtnS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(com.mad2021.classapp.SClassview.this, com.mad2021.classapp.SClassview.class);
                finish();
                overridePendingTransition(0,0);
                startActivity(i);
                overridePendingTransition(0,0);
            }
        });

        //Navigate to Notice
        noticeBtnS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(com.mad2021.classapp.SClassview.this,StudentViewNotice.class);
                startActivity(i);
                finish();
            }
        });





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