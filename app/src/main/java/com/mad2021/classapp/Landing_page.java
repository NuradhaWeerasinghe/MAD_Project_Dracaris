package com.mad2021.classapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Landing_page extends AppCompatActivity implements View.OnClickListener {

    // Declare Variables
    private ImageView teacher,student ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        // Initialize
        teacher = (ImageView) findViewById(R.id.imageView_Teacher);
        teacher.setOnClickListener(this);

        student = (ImageView) findViewById(R.id.imageView_Student);
        student.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageView_Student:
                startActivity(new Intent(this,student_signIn.class));
                break;
            case R.id.imageView_Teacher:
                startActivity(new Intent(this, teacher_signin.class));
                break;
        }
    }
}