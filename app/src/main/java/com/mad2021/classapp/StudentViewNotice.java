package com.mad2021.classapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

public class StudentViewNotice  extends AppCompatActivity {
    private RecyclerView recyclerView;
    private String test,test2;
    private ArrayList<com.mad2021.classapp.NoticeData> noticeData;
    private com.mad2021.classapp.NoticeStudentAdapter noticeAdapter;
    DatabaseReference dbRef;
//    EditText nName,noticeDes;
//    String className;
    Button nNavBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_student_view_notice);
     
        recyclerView = findViewById(R.id.recyclerNoticeS);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        noticeData = new  ArrayList<com.mad2021.classapp.NoticeData>();

        test = FirebaseAuth.getInstance().getCurrentUser().getUid();
        dbRef = FirebaseDatabase.getInstance().getReference().child("Notice").child("CF0jx7R9TAgOkPqEruhr5KFZwYA2");
        dbRef.addListenerForSingleValueEvent(valueEventListener);


        nNavBtn = findViewById(R.id.nNavBtn);



        nNavBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(com.mad2021.classapp.StudentViewNotice.this, com.mad2021.classapp.StudentViewNotice.class);
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
                com.mad2021.classapp.NoticeData uData = snapshot1.getValue(com.mad2021.classapp.NoticeData.class);
                noticeData.add(uData);
            }
            noticeAdapter= new com.mad2021.classapp.NoticeStudentAdapter(com.mad2021.classapp.StudentViewNotice.this,noticeData);
            recyclerView.setAdapter(noticeAdapter);


        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

}