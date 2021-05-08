package com.mad2021.classapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SGetStudentAdapter extends RecyclerView.Adapter<SGetStudentAdapter.MyViewHolder> {


    private Context context;
    private ArrayList<StudentData> studentData;
    public SGetStudentAdapter(Context context, ArrayList<StudentData> studentData){
        this.context = context;
        this.studentData = studentData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.all_student_s_list,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        StudentData studentData = this.studentData.get(position);
        myViewHolder.name.setText(studentData.getName());




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
            name= itemView.findViewById(R.id.stName);




        }
    }
}
