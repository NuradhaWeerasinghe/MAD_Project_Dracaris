package com.mad2021.classapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class activity_student_signUp extends AppCompatActivity implements View.OnClickListener {

    // Declaring Variables
    private TextView banner, registerUser,home;
    private EditText editTextName, editTextSchool, editTextEmail, editTextPassword,editTextAge,editTextGender;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_sign_up);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // Initialize
        banner = (TextView) findViewById(R.id.banner);
        banner.setOnClickListener(this);

        registerUser = (TextView) findViewById(R.id.registerUser);
        registerUser.setOnClickListener(this);

        home = (TextView)findViewById(R.id.home) ;
        home.setOnClickListener(this);

        editTextName = (EditText) findViewById(R.id.name);
        editTextSchool = (EditText) findViewById(R.id.school);
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);
        editTextGender = (EditText) findViewById(R.id.gender);
        editTextAge= (EditText) findViewById(R.id.age);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.banner:
                startActivity(new Intent(this,student_signIn.class));
                break;
            case R.id.home:
                startActivity(new Intent(this,Landing_page.class));
                break;
            case R.id.registerUser:
                registerUser();
                break;      
        }
    }

    private void registerUser() {
        // Variables
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String name = editTextName.getText().toString().trim();
        String school = editTextSchool.getText().toString().trim();
        String gender = editTextGender.getText().toString().trim();
        String age = editTextAge.getText().toString().trim();

        // Validations
        if(name.isEmpty()){
            editTextName.setError("Name is required");
            editTextName.requestFocus();
            return;
        }
        if(email.isEmpty()){
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please provide valid email !");
            editTextEmail.requestFocus();
            return;
        }

        if(age.isEmpty()){
            editTextAge.setError("Age is required");
            editTextAge.requestFocus();
            return;
        }
        if(gender.isEmpty()){
            editTextGender.setError("Age is required");
            editTextGender.requestFocus();
            return;
        }
        if(school.isEmpty()){
            editTextSchool.setError("Age is required");
            editTextSchool.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }
        if(password.length() < 6){
            editTextPassword.setError("Min password length should be 6 characters");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            student student = new student(name,age,gender,school,email,password);

                            FirebaseDatabase.getInstance().getReference("Students").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(student).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(activity_student_signUp.this,"Student has been Registered Successfully !", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);

                                        startActivity(new Intent(activity_student_signUp.this,student_signIn.class));
                                    }
                                    else {
                                        Toast.makeText(activity_student_signUp.this, "Failed to register ! Try again", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(activity_student_signUp.this, "Failed to register ! Try again", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }
}