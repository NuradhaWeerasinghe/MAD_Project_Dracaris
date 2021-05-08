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
import com.google.firebase.auth.FirebaseAuth;

public class Forgot_password extends AppCompatActivity implements View.OnClickListener {
    //Initialize
    private TextView signup,signin;
    private EditText editTextEmail;
    private ImageView backto;
    private Button submit;

    //Initialize variables for DB
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        //Creating db Instance
        mAuth = FirebaseAuth.getInstance();
        //Implementing
        signin = (TextView)findViewById(R.id.forgot_signin);
        signin.setOnClickListener(this);
        signup = (TextView)findViewById(R.id.forgot_signup);
        signup.setOnClickListener(this);
        backto = (ImageView) findViewById(R.id.backto);
        backto.setOnClickListener(this);

        editTextEmail = (EditText) findViewById(R.id.email);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        submit = (Button) findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

    private void resetPassword() {
        String email = editTextEmail.getText().toString().trim();
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
        progressBar.setVisibility(View.VISIBLE);
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Forgot_password.this,"Check your email to reset your password !",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Forgot_password.this,student_signIn.class));
                }else {
                    Toast.makeText(Forgot_password.this,"Try again! Something wrong happened!",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.forgot_signin:{
                startActivity(new Intent(this,student_signIn.class));
                break;
            }
            case R.id.forgot_signup:{
                startActivity(new Intent(this,activity_student_signUp.class));
                break;
            }
            case R.id.backto:{
                startActivity(new Intent(this, Landing_page.class));
                break;
            }
        }

    }
}