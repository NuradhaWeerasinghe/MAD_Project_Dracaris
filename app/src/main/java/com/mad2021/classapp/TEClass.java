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

public class TEClass extends AppCompatActivity {
     EditText ecName,eSub,eTec,eIns,eDes;
     Button eBtn;
     String userId,className,subject,teacher,ins,des;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t_e_class);

        ecName = findViewById(R.id.ecName);
        eSub = findViewById(R.id.eSub);
        eTec = findViewById(R.id.eTec);
        eIns = findViewById(R.id.eIns);
        eDes = findViewById(R.id.eDes);

        eBtn = findViewById(R.id.eBtn);


        Intent intent = getIntent();
        userId = intent.getStringExtra("id");

        className = intent.getStringExtra("className");
        subject = intent.getStringExtra("subject");
        teacher = intent.getStringExtra("teacher");
        ins = intent.getStringExtra("ins");
        des= intent.getStringExtra("des");


        ecName.setText(className);
        eSub.setText(subject);
        eTec.setText(teacher);
        eIns.setText(ins);
        eDes.setText(des);


        eBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Class1").child(userId);

                String className,subject,teacher,ins,des;


                className = ecName.getText().toString();
                subject = eSub.getText().toString();
                teacher = eTec.getText().toString();
                ins = eIns.getText().toString();
                des = eDes.getText().toString();
                ClassData classData = new ClassData(userId,className,subject,teacher,ins,des);
                databaseReference.setValue(classData);
                Toast.makeText(com.mad2021.classapp.TEClass.this, "Data Updated", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(com.mad2021.classapp.TEClass.this,TDashborad.class);
                startActivity(i);

            }
        });
    }
}