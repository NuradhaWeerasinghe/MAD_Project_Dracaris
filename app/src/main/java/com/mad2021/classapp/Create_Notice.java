package com.mad2021.classapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Create_Notice extends AppCompatActivity {

    private EditText nName,noticeDes;
    private Button cNbtn ;

    DatabaseReference database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__notice);

        nName = findViewById(R.id.nName);
        noticeDes = findViewById(R.id.noticeDes);

        cNbtn = findViewById(R.id.cNbtn);

        database = FirebaseDatabase.getInstance().getReference().child("Notice");

        cNbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id,name,description;

                id = database.push().getKey();
                name = nName.getText().toString();
                description = noticeDes.getText().toString();


                if(name.equals("")){
                    Toast.makeText(com.mad2021.classapp.Create_Notice.this, "Notice Name Required!", Toast.LENGTH_SHORT).show();
                }
                else if(description.equals("")){
                    Toast.makeText(com.mad2021.classapp.Create_Notice.this, "Description Required!", Toast.LENGTH_SHORT).show();
                }
                else{
                    NoticeData noticeData = new NoticeData(id,name,description);
                    database.child(id).setValue(noticeData);
                    Toast.makeText(com.mad2021.classapp.Create_Notice.this, "done", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(com.mad2021.classapp.Create_Notice.this, com.mad2021.classapp.TeacherViewNotice.class);
                    startActivity(i);
                }
            }
        });
    }
}