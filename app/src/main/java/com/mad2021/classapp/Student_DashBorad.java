package com.mad2021.classapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

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

public class Student_DashBorad  extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<ClassData> classData;
    private com.mad2021.classapp.StudentAdapter studentAdapter;
    DatabaseReference dbRef;
    Button jBtnDash,myClassSBtn,myNotesSBtn;
    ImageButton reminderBtnS ;
    ImageView profileS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashborad);

        recyclerView = findViewById(R.id.recycler2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        classData = new  ArrayList<ClassData>();

        dbRef = FirebaseDatabase.getInstance().getReference().child("Students").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Class");
        dbRef.addListenerForSingleValueEvent(valueEventListener);

        jBtnDash = findViewById(R.id.jBtnDash);
        reminderBtnS = findViewById(R.id.reminderBtnS);
        profileS = findViewById(R.id.profileS);
        myClassSBtn  = findViewById(R.id.myClassSBtn);
        myNotesSBtn  = findViewById(R.id.myNotesSBtn);

        //my Class
        myClassSBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(com.mad2021.classapp.Student_DashBorad.this, com.mad2021.classapp.Student_DashBorad.class);
                finish();
                overridePendingTransition(0,0);
                startActivity(i);
                overridePendingTransition(0,0);
            }
        });

        //my myNotes
        myNotesSBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(com.mad2021.classapp.Student_DashBorad.this,StudentNoteDashbord.class);
                startActivity(i);
                finish();
            }
        });

        jBtnDash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(com.mad2021.classapp.Student_DashBorad.this,SJoinClass.class);
                startActivity(i);
                finish();
            }
        });

        //Reminder Button
        reminderBtnS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(com.mad2021.classapp.Student_DashBorad.this,reminder_alarm.class);
                startActivity(i);
                finish();
            }
        });

        //Navigate to Profile
        profileS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(com.mad2021.classapp.Student_DashBorad.this,Student_viewProfile.class);
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