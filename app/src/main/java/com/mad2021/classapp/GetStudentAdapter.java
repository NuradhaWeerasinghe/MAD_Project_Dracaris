package com.mad2021.classapp;

import android.app.AlertDialog;
import android.content.Context;
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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class GetStudentAdapter extends RecyclerView.Adapter<GetStudentAdapter.MyViewHolder> {


    private Context context;
    private ArrayList<StudentData> studentData;
    public GetStudentAdapter(Context context,ArrayList<StudentData> studentData){
        this.context = context;
        this.studentData = studentData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.all_student_list,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        StudentData studentData = this.studentData.get(position);
        myViewHolder.name.setText(studentData.getName());

        //delete
        myViewHolder.delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDlg = new AlertDialog.Builder(v.getContext());
                alertDlg.setMessage("Are you sure?");
                alertDlg.setCancelable(false);
                alertDlg.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Students").child(studentData.getName());
                        databaseReference.removeValue();
                        Toast.makeText(context,"DELETED",Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(v.getContext(),TClassView.class);
                        v.getContext().startActivity(i);
                    }
                });
                alertDlg.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDlg.create().show();

            }


        });


    }

    @Override
    public int getItemCount() {
        return studentData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        ImageButton delBtn;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name= itemView.findViewById(R.id.sName);
            delBtn = itemView.findViewById(R.id.delBtn);



        }
    }
}
