package com.mad2021.classapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SJoinClass extends AppCompatActivity {
    Button submit;
    EditText classId,teacherId;

    // Declaring Variables
    private FirebaseUser user;
    private DatabaseReference reference,reference1;
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
        setContentView(R.layout.activity_s_join_class);

        submit = (Button)findViewById(R.id.button);
        classId = (EditText)findViewById(R.id.username);
        teacherId = (EditText)findViewById(R.id.username3);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Variables
                String clasID = classId.getText().toString().trim();
                String tId = teacherId.getText().toString().trim();

                // Getting student data

                // Initializing
                user = FirebaseAuth.getInstance().getCurrentUser();
                reference = FirebaseDatabase.getInstance().getReference("Students");
                userID = user.getUid();


                // Getting data from DB // View User
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

                            // Creating student instance in class
                            student student = new student(name,age,gender,school,email);
                            FirebaseDatabase.getInstance().getReference("Class1").child(tId).child(clasID).child("Students").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(student).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(SJoinClass.this, "Sucessfully Joined to the class", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(SJoinClass.this,Student_DashBorad.class));
                                    }else {
                                        Toast.makeText(SJoinClass.this, "Join Failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(SJoinClass.this,"Something is wrong !!", Toast.LENGTH_LONG).show();
                    }
                });
                // Create student instance when join

                // Initializing
                user = FirebaseAuth.getInstance().getCurrentUser();
                reference1 = FirebaseDatabase.getInstance().getReference("Class1").child(tId);


                // Getting data from DB // View User
                reference1.child(clasID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        ClassData classdat = snapshot.getValue(ClassData.class);
                        if (classdat != null){
                            String userIdn = classdat.userId;
                            String className = classdat.className;
                            String subject = classdat.subject;
                            String teacher = classdat.teacher;
                            String ins = classdat.ins;
                            String des = classdat.des;

                            // Creating student instance in class
                            ClassData ClassData = new ClassData(userIdn,className,subject,teacher,ins,des);
                            FirebaseDatabase.getInstance().getReference("Students").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Class").child(clasID)
                                    .setValue(ClassData);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(SJoinClass.this,"Something is wrong !!", Toast.LENGTH_LONG).show();
                    }
                });
            }



        });





    }
}