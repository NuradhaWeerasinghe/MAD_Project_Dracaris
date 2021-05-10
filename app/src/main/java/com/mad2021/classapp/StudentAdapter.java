package com.mad2021.classapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.MyViewHolder> {


    private Context context;
    private ArrayList<ClassData> classData;
    public StudentAdapter(Context context,ArrayList<ClassData> classData){
        this.context = context;
        this.classData = classData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.student_class_list,viewGroup,false);
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




        // clickable Card view
        myViewHolder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(),SClassview.class);
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
