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

public class TCClass extends AppCompatActivity {
    private EditText cName,cSubject,cTname,cIns,cDes;
    private Button cBtn;

    DatabaseReference database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcreate_class);

        cName = findViewById(R.id.cName);
        cSubject = findViewById(R.id.cSubject);
        cTname = findViewById(R.id.cTname);
        cIns = findViewById(R.id.cIns);
        cDes = findViewById(R.id.cDes);

        cBtn = findViewById(R.id.cBtn);

        database = FirebaseDatabase.getInstance().getReference().child("Class1");

        cBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId ,className,subject,teacher,ins,des;

                userId = database.push().getKey();
                className = cName.getText().toString();
                subject = cSubject.getText().toString();
                teacher = cTname.getText().toString();
                ins = cIns.getText().toString();
                des = cDes.getText().toString();

                if(className.equals("")){
                    Toast.makeText(com.mad2021.classapp.TCClass.this, "Class Name Required!", Toast.LENGTH_SHORT).show();
                }
                else if(subject.equals("")){
                    Toast.makeText(com.mad2021.classapp.TCClass.this, "Subject Required!", Toast.LENGTH_SHORT).show();
                }
                else if(teacher.equals("")){
                    Toast.makeText(com.mad2021.classapp.TCClass.this, "Teacher Name Required!", Toast.LENGTH_SHORT).show();
                }
                else if(ins.equals("")){
                    Toast.makeText(com.mad2021.classapp.TCClass.this, "Institute Name Required!", Toast.LENGTH_SHORT).show();
                }
                else if(des.equals("")){
                    Toast.makeText(com.mad2021.classapp.TCClass.this, "Description Required!", Toast.LENGTH_SHORT).show();
                }
                else{
                    com.mad2021.classapp.ClassData classData = new com.mad2021.classapp.ClassData(userId ,className,subject,teacher,ins,des);
                    database.child(userId).setValue(classData);
                    Toast.makeText(com.mad2021.classapp.TCClass.this, "done", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(com.mad2021.classapp.TCClass.this, com.mad2021.classapp.TDashborad.class);
                    startActivity(i);
                }
            }
        });
    }
}