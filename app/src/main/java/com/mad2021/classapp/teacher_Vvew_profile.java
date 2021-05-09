package com.mad2021.classapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class teacher_Vvew_profile extends AppCompatActivity {
    // Declaring Variables
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    private Button delete,edit;
    private ProgressBar progressBar;
    private TextView logout;
    private ImageView back_Home;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher__vvew_profile);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        // Initializing
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Teachers");
        userID = user.getUid();

        // Create instances for textViews
        delete = (Button)findViewById(R.id.button_View_delete);
        edit= (Button) findViewById(R.id.button_View_Edit);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        back_Home = (ImageView)findViewById(R.id.back_home);
        logout = (TextView) findViewById(R.id.logout);
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
                Toast.makeText(teacher_Vvew_profile.this,"Something is wrong !!", Toast.LENGTH_LONG).show();
            }
        });

        //Edit user Redirecting  Data
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(teacher_Vvew_profile.this,teacher_edit_view.class));
            }
        });

        // Redirecting  user to the dashboard
        back_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(teacher_Vvew_profile.this,Student_DashBorad.class));
            }
        });

        // Calling logout
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(teacher_Vvew_profile.this,Landing_page.class));
            }
        });

        // Deleting User
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(teacher_Vvew_profile.this);
                dialog.setTitle("Are You sure ?");
                dialog.setMessage("Deleting this account will remove your total profile with its details.!!");
                dialog.setPositiveButton("delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        progressBar.setVisibility(View.VISIBLE);
                        firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    Toast.makeText(teacher_Vvew_profile.this, "Account Deleted", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(teacher_Vvew_profile.this,Landing_page.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(teacher_Vvew_profile.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        });
                    }
                });
                dialog.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = dialog.create();
                alertDialog.show();
            }
        });

    }
}