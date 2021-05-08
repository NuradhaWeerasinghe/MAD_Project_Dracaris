package com.mad2021.classapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Teacher_SignUP extends AppCompatActivity implements View.OnClickListener {
    // Declaring variables
    private TextView back,signin;
    private EditText editTextName, editTextBirth, editTextEmail, editTextPassword,
            editTextSchool, editTextGender, editTextPhone;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private Button btn_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher__sign_u_p);
        
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        //Initialize other variables
        back = (TextView) findViewById(R.id.student_back);
        back.setOnClickListener(this);
        
        signin = (TextView) findViewById(R.id.banner);
        signin.setOnClickListener(this);

        btn_signup = (Button) findViewById(R.id.submit);
        btn_signup.setOnClickListener(this);

                editTextName = findViewById(R.id.student_name);
        editTextBirth = findViewById(R.id.student_dateofB);
        editTextEmail= findViewById(R.id.email);
        editTextPassword = findViewById(R.id.student_password);
        editTextSchool = findViewById(R.id.student_school);
        editTextGender = findViewById(R.id.gender);
        editTextPhone = findViewById(R.id.student_phoneNumber);


        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.banner:
                startActivity(new Intent(this,teacher_signin.class));
                break;
            case R.id.student_back:
                startActivity(new Intent(this,Landing_page.class));
                break;
            case R.id.submit:
                registerUser();

        }
        
    }

    private void registerUser() {
        // Validations
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String name = editTextName.getText().toString().trim();
        String birth = editTextBirth.getText().toString().trim();
        String gender = editTextGender.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();
        String school = editTextSchool.getText().toString().trim();

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
        if(name.isEmpty()){
            editTextName.setError("Name is required");
            editTextName.requestFocus();
            return;
        }
        if(birth.isEmpty()){
            editTextBirth.setError("Birthday is required");
            editTextBirth.requestFocus();
            return;
        }
        if(gender.isEmpty()){
            editTextGender.setError("Gender is required");
            editTextGender.requestFocus();
            return;
        }
        if(phone.isEmpty()){
            editTextPhone.setError("Phone Number is required");
            editTextPhone.requestFocus();
            return;
        }
        if(phone.length() != 10){
            editTextPhone.setError("Add a valid phone number");
            editTextPhone.requestFocus();
            return;
        }
        if(school.isEmpty()){
            editTextSchool.setError("School is required");
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
                            teacher teacher = new teacher(name, birth, gender, school, email, password, phone);

                            FirebaseDatabase.getInstance().getReference("teach").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(teacher).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Teacher_SignUP.this, "Teacher has been registered successfully", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    } else {
                                        Toast.makeText(Teacher_SignUP.this, "Failed to register ! Try again", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }

                            });
                        } else {
                            Toast.makeText(Teacher_SignUP.this, "Failed to register ! Try again", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }

                    }

                });
    }
}
