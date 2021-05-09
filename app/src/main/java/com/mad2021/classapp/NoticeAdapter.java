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
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.MyViewHolder> {


    private Context context;
    private ArrayList<com.mad2021.classapp.NoticeData> noticeData;
    public NoticeAdapter(Context context,ArrayList<com.mad2021.classapp.NoticeData> noticeData){
        this.context = context;
        this.noticeData = noticeData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notice_list,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        com.mad2021.classapp.NoticeData noticeData = this.noticeData.get(position);
        myViewHolder.name.setText(noticeData.getName());
        myViewHolder.description.setText(noticeData.getDescription());


        //delete
//        myViewHolder.del.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                AlertDialog.Builder alertDlg = new AlertDialog.Builder(v.getContext());
//                alertDlg.setMessage("Are you sure?");
//                alertDlg.setCancelable(false);
//                alertDlg.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Class1").child(noticeData.getUserId());
//                        databaseReference.removeValue();
//                        Toast.makeText(context,"DELETED",Toast.LENGTH_SHORT).show();
//                        Intent i = new Intent(v.getContext(),TDashborad.class);
//                        v.getContext().startActivity(i);
//                    }
//                });
//                alertDlg.setNegativeButton("No", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//                alertDlg.create().show();
//
//            }
//
//
//        });



        //edit details
        myViewHolder.noticeEBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(),EditNotice.class);
                i.putExtra("id",noticeData.getId());
                i.putExtra("name",noticeData.getName());
                i.putExtra("description",noticeData.getDescription());

                v.getContext().startActivity(i);

            }
        });

        myViewHolder.nDelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDlg = new AlertDialog.Builder(v.getContext());
                alertDlg.setMessage("Are you sure?");
                alertDlg.setCancelable(false);
                alertDlg.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Notice").child(noticeData.getId());
                        databaseReference.removeValue();
                        Toast.makeText(context,"DELETED",Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(v.getContext(), com.mad2021.classapp.TeacherViewNotice.class);
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
        return noticeData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,description;
        Button nDelBtn,noticeEBtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name= itemView.findViewById(R.id.noticeName);
            description= itemView.findViewById(R.id.noticeDesc);
            noticeEBtn = itemView.findViewById(R.id.nEdit);
            nDelBtn = itemView.findViewById(R.id.nDelBtn);

        

        }
    }
}
