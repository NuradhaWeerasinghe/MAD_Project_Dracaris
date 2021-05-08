package com.mad2021.classapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Student_viewProfile extends AppCompatActivity {
    // Declaring Variables
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_view_profile);

        // Initializing
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Students");
        userID = user.getUid();

        // Create instances for textViews
        final TextView emailTextView = (TextView)findViewById(R.id.email);
        final TextView nameTextView = (TextView)findViewById(R.id.name);
        final TextView ageTextView = (TextView)findViewById(R.id.age);
        final TextView genderTextView = (TextView)findViewById(R.id.gender);
        final TextView schoolTextView = (TextView)findViewById(R.id.school);

        // Getting data from DB
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
                Toast.makeText(Student_viewProfile.this,"Something is wrong !!", Toast.LENGTH_LONG).show();
            }
        });
    }
}