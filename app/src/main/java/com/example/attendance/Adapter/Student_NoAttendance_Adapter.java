package com.example.attendance.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendance.R;
import com.example.attendance.Model.Student;

import java.util.ArrayList;

public class Student_NoAttendance_Adapter extends RecyclerView.Adapter<Student_NoAttendance_Adapter.ViewHolder> {
    public static ArrayList<Student> list_student_noattendance;
    public static Context context;

    public Student_NoAttendance_Adapter(ArrayList<Student> list_student_noattendance, Context context) {
        this.list_student_noattendance = list_student_noattendance;
        this.context = context;
    }

    @NonNull
    @Override
    public Student_NoAttendance_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_student,parent,false);
        return new Student_NoAttendance_Adapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final Student_NoAttendance_Adapter.ViewHolder holder, final int position) {
        holder.txt_msv.setText(list_student_noattendance.get(position).getStudentID());
        holder.txt_hoten.setText(list_student_noattendance.get(position).getHoTen());
        holder.txt_monhoc.setText(list_student_noattendance.get(position).getClassName());
        holder.checkBox.setChecked(list_student_noattendance.get(position).isDihoc());
    }

    @Override
    public int getItemCount() {
        return list_student_noattendance.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_msv,txt_hoten,txt_monhoc;
        CheckBox checkBox;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_msv = (TextView)itemView.findViewById(R.id.tv_msv);
            txt_hoten = (TextView)itemView.findViewById(R.id.tv_tensv);
            txt_monhoc = (TextView)itemView.findViewById(R.id.tv_monhoc);
            checkBox = (CheckBox)itemView.findViewById(R.id.checkBox);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    list_student_noattendance.get(getAdapterPosition()).setDihoc(b);
                }
            });
        }
    }
}
