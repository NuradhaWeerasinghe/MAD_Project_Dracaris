package com.mad2021.classapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NoticeStudentAdapter extends RecyclerView.Adapter<NoticeStudentAdapter.MyViewHolder> {


    private Context context;
    private ArrayList<NoticeData> noticeData;
    public NoticeStudentAdapter(Context context,ArrayList<NoticeData> noticeData){
        this.context = context;
        this.noticeData = noticeData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notice_st_list,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        NoticeData noticeData = this.noticeData.get(position);
        myViewHolder.name.setText(noticeData.getName());
        myViewHolder.description.setText(noticeData.getDescription());







    }

    @Override
    public int getItemCount() {
        return noticeData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,description;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name= itemView.findViewById(R.id.noticeName);
            description= itemView.findViewById(R.id.noticeDesc);




        }
    }
}
