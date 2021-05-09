package com.mad2021.classapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Create_Note extends AppCompatActivity implements View.OnClickListener {

    Button btnCreate;
    EditText nameET, descriptionET;
    FirebaseUser user;
    FirebaseFirestore firestore;
    ProgressBar loadingIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__note);

        btnCreate= findViewById(R.id.btn_create_note);
        nameET = findViewById(R.id.note_name_et);
        descriptionET = findViewById(R.id.decription_et);

        user = FirebaseAuth.getInstance().getCurrentUser();
        firestore = FirebaseFirestore.getInstance();
        loadingIcon = findViewById(R.id.progress_bar);
        btnCreate.setOnClickListener(this);
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        if(user == null){
//            startActivity(new Intent(this, SimpleLogin.class));
//        }
//    }

    @Override
    public void onClick(View view) {
        int id= view.getId();
        if(id == R.id.btn_create_note){
            createNewNote();
        }
    }


    @SuppressLint("NewApi")
    private void createNewNote() {
        String noteName = nameET.getText().toString();
        String description = descriptionET.getText().toString();

        if(validateNote(noteName, description)){
            loadingIcon.setVisibility(View.VISIBLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            Map<String, Object> note = new HashMap<>();
            note.put("name", noteName);
            note.put("description", description);
            note.put("userId", user.getUid());
            note.put("time", LocalDate.now().toString());
            firestore.collection("notes").document().set(note).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(com.mad2021.classapp.Create_Note.this, "note added successfully", Toast.LENGTH_SHORT).show();
                        com.mad2021.classapp.Create_Note.this.finish();
                    }else{
                        Toast.makeText(com.mad2021.classapp.Create_Note.this, "Cannot add your note right now, Try again", Toast.LENGTH_LONG).show();
                    }
                    loadingIcon.setVisibility(View.GONE);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                }
            });
        }
    }

    private boolean validateNote(String noteName, String description) {
        boolean validity = true;
        if(noteName.isEmpty()){
            nameET.setError("Name cannot be empty");
            validity = false;
        }else if(noteName.length() < 5){
            validity = false;
            nameET.setError("Name must contain 5 or more characters");

        }else{
            nameET.setError(null);
        }
        if(description.isEmpty()){
            descriptionET.setError("Description cannot be empty");
            validity = false;
        }else if(description.length() < 15){
            validity = false;
            descriptionET.setError("Description must contain 15 or more characters");

        }else{
            descriptionET.setError(null);
        }
        return validity;
    }
}