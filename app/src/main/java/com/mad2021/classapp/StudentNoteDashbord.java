package com.mad2021.classapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StudentNoteDashbord extends AppCompatActivity implements View.OnClickListener {

    FirebaseUser user;
    FirebaseAuth auth;
    Button addBtn, viewBtn, logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_note_dashbord);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        if(user == null){
            startActivity(new Intent(this, Landing_page.class));
        }

        addBtn = findViewById(R.id.add_note);
        viewBtn = findViewById(R.id.view_notes);
        logoutBtn = findViewById(R.id.logout);
        logoutBtn.setOnClickListener(this);
        addBtn.setOnClickListener(this);
        viewBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.add_note){
            startActivity(new Intent(this, Create_Note.class));
        }else if(id == R.id.view_notes){
            startActivity(new Intent(this, viewNote.class));
        }else if(id == R.id.logout){
            System.out.println("logou");
            auth.signOut();
            startActivity(new Intent(this, Landing_page.class));
            this.finish();
        }
    }
}