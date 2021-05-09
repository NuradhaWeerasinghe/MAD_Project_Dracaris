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

public class TeacherViewNotice  extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<NoticeData> noticeData;
    private  NoticeAdapter noticeAdapter;
    DatabaseReference dbRef;
//    EditText nName,noticeDes;
//    String className;
    Button noticeCreatebtn,nNavBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_teacher_view_notice);
     
        recyclerView = findViewById(R.id.recyclerNotice);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        noticeData = new  ArrayList<NoticeData>();

        dbRef = FirebaseDatabase.getInstance().getReference().child("Notice");
        dbRef.addListenerForSingleValueEvent(valueEventListener);

        noticeCreatebtn = findViewById(R.id.noticeCreatebtn);
        nNavBtn = findViewById(R.id.nNavBtn);

        noticeCreatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(com.mad2021.classapp.TeacherViewNotice.this,Create_Notice.class);
                startActivity(i);
                finish();
            }
        });

        nNavBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(com.mad2021.classapp.TeacherViewNotice.this, com.mad2021.classapp.TeacherViewNotice.class);
                finish();
                overridePendingTransition(0,0);
                startActivity(i);
                overridePendingTransition(0,0);
            }
        });


    }



    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            for (DataSnapshot snapshot1: snapshot.getChildren()){
                NoticeData uData = snapshot1.getValue(NoticeData.class);
                noticeData.add(uData);
            }
            noticeAdapter= new NoticeAdapter(com.mad2021.classapp.TeacherViewNotice.this,noticeData);
            recyclerView.setAdapter(noticeAdapter);


        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

}