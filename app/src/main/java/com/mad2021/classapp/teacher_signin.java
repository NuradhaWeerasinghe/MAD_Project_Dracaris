package com.mad2021.classapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class teacher_signin extends AppCompatActivity implements View.OnClickListener {

    //Initialize
    private TextView signup, forgot;
    private EditText editTextEmail, editTextPassword;
    private ImageView backto;
    private Button signin;

    //Initialize variables for DB
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_signin);

        //Creating db Instance
        mAuth = FirebaseAuth.getInstance();

        signup = (TextView) findViewById(R.id.teacher_signup);
        signup.setOnClickListener(this);
        signin = (Button) findViewById(R.id.submit);
        signin.setOnClickListener(this);
        forgot = (TextView)findViewById(R.id.teacher_forgot);
        forgot.setOnClickListener(this);
        backto = (ImageView) findViewById(R.id.backto);
        backto.setOnClickListener(this);

        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.teacher_signup: {
                startActivity(new Intent(this, Teacher_SignUP.class));
                break;
            }
            case R.id.submit: {
                userLogin();
                break;
            }
            case R.id.teacher_forgot: {
                startActivity(new Intent(this, teacher_forgotPassword.class));
                break;
            }
            case R.id.backto: {
                startActivity(new Intent(this, Landing_page.class));
                break;
            }

        }

    }

    private void userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        // Validation
        if (email.isEmpty()) {
            editTextEmail.setError("Email is required ! ");
            editTextEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please enter an Valid Email");
            editTextEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            editTextPassword.setError("Password is required !");
            editTextPassword.requestFocus();
            return;
        }
        if (password.length() <6){
            editTextPassword.setError("Min password length is 6 characters");
            editTextPassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Get Email Verification
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user.isEmailVerified()){
                        // Redirect
                        startActivity(new Intent(teacher_signin.this, TDashborad.class));
                    }else {
                        user.sendEmailVerification();
                        Toast.makeText(teacher_signin.this,"Check your email to verify your email address", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                        clear();
                    }

                }
                else {
                    Toast.makeText(teacher_signin.this, "Failed to login ! Please check your credentials",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                    clear();
                }
            }
        });
    }

    private void clear() {
        editTextEmail.setText("");
        editTextPassword.setText("");
    }
}