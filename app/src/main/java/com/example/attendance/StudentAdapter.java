package com.example.attendance;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendance.ui.home.HomeFragment;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {
    public static ArrayList<Student> list_student;
    Context context;

    public StudentAdapter(ArrayList<Student> list_student, Context context) {
        this.list_student = list_student;
        this.context = context;
    }

    @NonNull
    @Override
    public StudentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_student,parent,false);
        return new StudentAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final StudentAdapter.ViewHolder holder, final int position) {
        holder.txt_msv.setText(list_student.get(position).getStudentID());
        holder.txt_hoten.setText(list_student.get(position).getHoTen());
        holder.txt_monhoc.setText(list_student.get(position).getClassName());
//        holder.checkBox.setChecked(list_student.get(position).isDiHoc());
    }

    @Override
    public int getItemCount() {
        return list_student.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txt_msv,txt_hoten,txt_monhoc;
        CheckBox checkBox;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            txt_msv = (TextView)itemView.findViewById(R.id.tv_msv);
            txt_hoten = (TextView)itemView.findViewById(R.id.tv_tensv);
            txt_monhoc = (TextView)itemView.findViewById(R.id.tv_monhoc);
            //checkBox = (CheckBox)itemView.findViewById(R.id.checkBox);

//            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                    //list_student.get(getAdapterPosition()).setDiHoc(b);
//                }
//            });
        }
    }
}
