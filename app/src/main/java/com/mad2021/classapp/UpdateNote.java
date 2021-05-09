package com.mad2021.classapp;

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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class UpdateNote extends AppCompatActivity implements View.OnClickListener {

    String noteId;
    Button updateBtn;
    EditText nameET, descriptionET;
    FirebaseFirestore firestore;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);
        noteId = getIntent().getStringExtra("id");
        updateBtn = findViewById(R.id.button_update);
        nameET = findViewById(R.id.updateNameET);
        descriptionET = findViewById(R.id.updateDescET);
        progressBar = findViewById(R.id.updateProgress);
        firestore = FirebaseFirestore.getInstance();

        updateBtn.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (noteId != null) {
            progressBar.setVisibility(View.VISIBLE);
            firestore.collection("notes").document(noteId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        if (task.getResult() != null) {
                            nameET.setText(task.getResult().getString("name"));
                            descriptionET.setText(task.getResult().getString("description"));
                        }
                    }else{
                        Toast.makeText(com.mad2021.classapp.UpdateNote.this, "Can't load the note. please try again", Toast.LENGTH_LONG).show();
                    }
                    progressBar.setVisibility(View.GONE);
                }
            });
        } else {
            Toast.makeText(this, "Can't find note id, please go back and select one", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.button_update) {
            updateNote();
        }
    }

    private void updateNote() {
        String noteName = nameET.getText().toString();
        String description = descriptionET.getText().toString();

        if (validateNote(noteName, description)) {
            progressBar.setVisibility(View.VISIBLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            Map<String, Object> note = new HashMap<>();
            note.put("name", noteName);
            note.put("description", description);
            firestore.collection("notes").document(noteId).update(note).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(com.mad2021.classapp.UpdateNote.this, "note updated successfully", Toast.LENGTH_SHORT).show();
                        com.mad2021.classapp.UpdateNote.this.finish();
                    } else {
                        Toast.makeText(com.mad2021.classapp.UpdateNote.this, "Cannot update your note right now, Try again", Toast.LENGTH_LONG).show();
                    }
                    progressBar.setVisibility(View.GONE);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                }
            });
        }
    }

    private boolean validateNote(String noteName, String description) {
        boolean validity = true;
        if (noteId == null) {
            Toast.makeText(this, "Can't find note id, please go back and select one", Toast.LENGTH_LONG).show();
            validity = false;
        }
        if (noteName.isEmpty()) {
            nameET.setError("Name cannot be empty");
            validity = false;
        } else if (noteName.length() < 5) {
            validity = false;
            nameET.setError("Name must contain 5 or more characters");

        } else {
            nameET.setError(null);
        }
        if (description.isEmpty()) {
            descriptionET.setError("Description cannot be empty");
            validity = false;
        } else if (description.length() < 15) {
            validity = false;
            descriptionET.setError("Description must contain 15 or more characters");

        } else {
            descriptionET.setError(null);
        }
        return validity;
    }
}