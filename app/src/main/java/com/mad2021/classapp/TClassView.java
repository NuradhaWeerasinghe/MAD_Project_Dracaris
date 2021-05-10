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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TClassView  extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<com.mad2021.classapp.StudentData> studentData;
    private com.mad2021.classapp.GetStudentAdapter getStudentAdapter;
    DatabaseReference dbRef;
    TextView ecName;
    String className;
    Button studentBtn,noticeBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_t_class_view);
        ecName = findViewById(R.id.topText);
        Intent i = getIntent();
        className = i.getStringExtra("className");
        recyclerView = findViewById(R.id.recycler3);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        studentData = new  ArrayList<com.mad2021.classapp.StudentData>();
        ecName.setText(className);
        dbRef = FirebaseDatabase.getInstance().getReference().child("Students");
        dbRef.addListenerForSingleValueEvent(valueEventListener);

        studentBtn = findViewById(R.id.studentBtn);
        noticeBtn  = findViewById(R.id.noticeBtn);

        //Student top button
        studentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(com.mad2021.classapp.TClassView.this, com.mad2021.classapp.TClassView.class);
                finish();
                overridePendingTransition(0,0);
                startActivity(i);
                overridePendingTransition(0,0);
            }
        });


        //Navigate to Notice
        noticeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(com.mad2021.classapp.TClassView.this,TeacherViewNotice.class);
                startActivity(i);
                finish();
            }
        });



    }



    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            for (DataSnapshot snapshot1: snapshot.getChildren()){
                com.mad2021.classapp.StudentData uData = snapshot1.getValue(com.mad2021.classapp.StudentData.class);
                studentData.add(uData);
            }
            getStudentAdapter= new com.mad2021.classapp.GetStudentAdapter(com.mad2021.classapp.TClassView.this,studentData);
            recyclerView.setAdapter(getStudentAdapter);


        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

}