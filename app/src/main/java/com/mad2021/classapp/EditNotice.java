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

public class EditNotice extends AppCompatActivity {
    EditText nEditName,noticeEditDes;
    Button noticeEBtn;
    String id,name,description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_notice);

        nEditName = findViewById(R.id.nEditName);
        noticeEditDes = findViewById(R.id.noticeEditDes);
        noticeEBtn = findViewById(R.id.noticeEBtn);


        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        name = intent.getStringExtra("name");
        description = intent.getStringExtra("description");
    

        nEditName.setText(name);
        noticeEditDes.setText(description);
    


        noticeEBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Notice").child(id);

                String name,description;


                name = nEditName.getText().toString();
                description = noticeEditDes.getText().toString();
               
                com.mad2021.classapp.NoticeData noticeData = new com.mad2021.classapp.NoticeData(id,name,description);
                databaseReference.setValue(noticeData);
                Toast.makeText(com.mad2021.classapp.EditNotice.this, "Data Updated", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(com.mad2021.classapp.EditNotice.this, com.mad2021.classapp.TeacherViewNotice.class);
                startActivity(i);

            }
        });
    }
}