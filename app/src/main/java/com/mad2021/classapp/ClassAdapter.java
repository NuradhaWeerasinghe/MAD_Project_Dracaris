package com.mad2021.classapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.MyViewHolder> {


    private Context context;
    private ArrayList<ClassData> classData;
    public ClassAdapter(Context context,ArrayList<ClassData> classData){
        this.context = context;
        this.classData = classData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.class_list,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        ClassData classData = this.classData.get(position);
        myViewHolder.className.setText(classData.getClassName());
        myViewHolder.subject.setText(classData.getSubject());
        myViewHolder.teacher.setText(classData.getTeacher());
        myViewHolder.ins.setText(classData.getIns());
        myViewHolder.des.setText(classData.getDes());

        //delete
        myViewHolder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDlg = new AlertDialog.Builder(v.getContext());
                alertDlg.setMessage("Are you sure?");
                alertDlg.setCancelable(false);
                alertDlg.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Class1").child(classData.getUserId());
                        databaseReference.removeValue();
                        Toast.makeText(context,"DELETED",Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(v.getContext(),TDashborad.class);
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



        //edit details
        myViewHolder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(),TEClass.class);
                i.putExtra("id",classData.getUserId());
                i.putExtra("className",classData.getClassName());
                i.putExtra("subject",classData.getSubject());
                i.putExtra("teacher",classData.getTeacher());
                i.putExtra("ins",classData.getIns());
                i.putExtra("des",classData.getDes());
                v.getContext().startActivity(i);

            }
        });

        // clickable Card view
        myViewHolder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(),TClassView.class);
                i.putExtra("className",classData.getClassName());
                v.getContext().startActivity(i);

            }
        });


    }

    @Override
    public int getItemCount() {
        return classData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView className,subject,teacher,ins,des;
        Button del,editBtn;
        CardView card;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            className= itemView.findViewById(R.id.cName);
            subject= itemView.findViewById(R.id.cSubject);
            teacher= itemView.findViewById(R.id.cTname);
            ins= itemView.findViewById(R.id.cIns);
            des= itemView.findViewById(R.id.cDes);
            del = itemView.findViewById(R.id.delBtn);
            editBtn = itemView.findViewById(R.id.editBtn);
            card = itemView.findViewById(R.id.cardView);

        }
    }
}
