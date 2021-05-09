package com.mad2021.classapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    public ArrayList<com.mad2021.classapp.Note> notes;

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        public TextView date, noteName, description;
        public ImageButton updateBtn, deleteBtn;

        public NoteViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.dateTV);
            noteName = itemView.findViewById(R.id.nameTV);
            description = itemView.findViewById(R.id.descriptionTV);
            updateBtn = itemView.findViewById(R.id.updateBtn);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
        }
    }

    public NoteAdapter(ArrayList<com.mad2021.classapp.Note> notes) {
        this.notes = notes;
    }

    @NonNull
    @NotNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_card, parent, false);
       NoteViewHolder nvh = new NoteViewHolder(view);
       return  nvh;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull com.mad2021.classapp.NoteAdapter.NoteViewHolder holder, int position) {
        com.mad2021.classapp.Note note = notes.get(position);
        holder.noteName.setText(note.getName());
        holder.description.setText(note.getDescription());
        holder.date.setText(note.getTime());

        holder.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent update = new Intent(view.getContext(), com.mad2021.classapp.UpdateNote.class);
                update.putExtra("id", note.getId());
                view.getContext().startActivity(update);
            }
        });

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(view.getContext()).setTitle("Delete note")
                        .setMessage("Are you sure you want to delete this note?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                db.collection("notes").document(note.getId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(view.getContext(), "Note successfully deleted", Toast.LENGTH_LONG).show();
                                        ((Activity) view.getContext()).finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(view.getContext(), "Can't delete the note right now, try again", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }).setNegativeButton(android.R.string.no, null).setIcon(android.R.drawable.ic_menu_delete)
                        .show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return notes.size();
    }


}
