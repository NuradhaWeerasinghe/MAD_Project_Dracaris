package com.mad2021.classapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class viewNote extends AppCompatActivity implements View.OnClickListener{

    private RecyclerView noteRecycler;
    private RecyclerView.Adapter noteAdapter;
    private RecyclerView.LayoutManager noteLayout;
    ArrayList<com.mad2021.classapp.Note> notes = new ArrayList<>();
    FirebaseUser user;
    FirebaseFirestore fireStore;
    ProgressBar progressBar;
    Button addNew;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);
        noteRecycler = findViewById(R.id.notes_rv);
        noteRecycler.setHasFixedSize(true);
        addNew = findViewById(R.id.add_note_btn);
        addNew.setOnClickListener(this);
        noteLayout = new LinearLayoutManager(this);
        fireStore = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        progressBar = findViewById(R.id.progressBar);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(user == null){
            Toast.makeText(this, "Please log in",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, Landing_page.class));
        }else{
            notes.clear();
            progressBar.setVisibility(View.VISIBLE);
            fireStore.collection("notes").whereEqualTo("userId", user.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {

                        if(task.isSuccessful()){
                            if(task.getResult().size() > 0){
                                for(QueryDocumentSnapshot doc:task.getResult()){
                                    com.mad2021.classapp.Note tempNote = new com.mad2021.classapp.Note(doc.getId(), doc.getString("userId"), doc.getString("name"), doc.getString("description"), doc.getString("time"));
                                    notes.add(tempNote);
                                }
                                noteAdapter = new NoteAdapter(notes);
                                noteRecycler.setLayoutManager(noteLayout);
                                noteRecycler.setAdapter(noteAdapter);
                            }else{
                                Toast.makeText(com.mad2021.classapp.viewNote.this, "No Notes found", Toast.LENGTH_LONG).show();
                            }
                        }else{
                            Toast.makeText(com.mad2021.classapp.viewNote.this, "Cannot load Notes right now, Try again", Toast.LENGTH_LONG).show();
                        }
                    progressBar.setVisibility(View.GONE);
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.add_note_btn){
            startActivity(new Intent(this, com.mad2021.classapp.Create_Note.class));
        }
    }
}