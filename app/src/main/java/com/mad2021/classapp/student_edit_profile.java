package com.mad2021.classapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class student_edit_profile extends AppCompatActivity {
    // Declaring Variables
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    private Button delete,edit,save;
    private ProgressBar progressBar;
    private EditText editTextName, editTextSchool,editTextAge,editTextGender;
    private TextView editTextEmail;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_edit_profile2);

        // Initializing
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Students");
        userID = user.getUid();


        //for edit
        editTextName = (EditText) findViewById(R.id.name);
        editTextSchool = (EditText) findViewById(R.id.school);
        editTextGender = (EditText) findViewById(R.id.gender);
        editTextAge= (EditText) findViewById(R.id.age);
        editTextEmail = (TextView) findViewById(R.id.email);
        save = (Button)findViewById(R.id.button_View_delete) ;

        // Create instances for textViews

        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        final TextView emailTextView = (TextView)findViewById(R.id.email);
        final TextView nameTextView = (TextView)findViewById(R.id.name);
        final TextView ageTextView = (TextView)findViewById(R.id.age);
        final TextView genderTextView = (TextView)findViewById(R.id.gender);
        final TextView schoolTextView = (TextView)findViewById(R.id.school);

        // Getting data from DB // View User
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                student userProfile = snapshot.getValue(student.class);
                if (userProfile != null){
                    String name = userProfile.name;
                    String email = userProfile.email;
                    String age = userProfile.age;
                    String gender = userProfile.gender;
                    String school = userProfile.school;

                    // Setting data to the text views
                    emailTextView.setText(email);
                    nameTextView.setText(name);
                    ageTextView.setText(age);
                    genderTextView.setText(gender);
                    schoolTextView.setText(school);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(student_edit_profile.this,"Something is wrong !!", Toast.LENGTH_LONG).show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Students").child(userID);


                String ename,eschool,eage,egender,eemail;
                ename = editTextName.getText().toString();
                eage = editTextAge.getText().toString();
                egender = editTextGender.getText().toString();
                eschool = editTextSchool.getText().toString();
                eemail = editTextEmail.getText().toString();
                student st = new student(ename,eage,egender,eschool,eemail);
                databaseReference.setValue(st);
                Toast.makeText(student_edit_profile.this,"Profile Updated ",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(v.getContext(),Student_viewProfile.class);
                v.getContext().startActivity(i);
            }
        });
}}