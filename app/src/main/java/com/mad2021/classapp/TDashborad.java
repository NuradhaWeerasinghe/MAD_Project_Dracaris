package com.mad2021.classapp;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

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

public class TDashborad extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<ClassData> classData;
    private com.mad2021.classapp.ClassAdapter userAdapter;
    DatabaseReference dbRef;
    Button cBtn,myClassB,myNotes;
    ImageButton reminderBtn;
    ImageView profileT ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t_dashborad);

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        classData = new  ArrayList<ClassData>();

        dbRef = FirebaseDatabase.getInstance().getReference().child("Class1");
        dbRef.addListenerForSingleValueEvent(valueEventListener);

        cBtn = findViewById(R.id.cBtnDash);
        myClassB = findViewById(R.id.myClassBtn);
        reminderBtn = findViewById(R.id.reminderBtn);
        profileT = findViewById(R.id.profileT);
        myNotes = findViewById(R.id.myNotes);

        cBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(com.mad2021.classapp.TDashborad.this,TCClass.class);
                startActivity(i);
                finish();
            }
        });

        //my myNotes
        myNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(com.mad2021.classapp.TDashborad.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });


        //my Class
        myClassB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(com.mad2021.classapp.TDashborad.this, com.mad2021.classapp.TDashborad.class);
                finish();
                overridePendingTransition(0,0);
                startActivity(i);
                overridePendingTransition(0,0);
            }
        });

        //Reminder Button
        reminderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(com.mad2021.classapp.TDashborad.this,reminder_alarm.class);
                startActivity(i);
                finish();
            }
        });

        //Navigate to TProfile
        profileT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(com.mad2021.classapp.TDashborad.this,teacher_Vvew_profile.class);
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
            userAdapter= new com.mad2021.classapp.ClassAdapter(com.mad2021.classapp.TDashborad.this,classData);
            recyclerView.setAdapter(userAdapter);


        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

}