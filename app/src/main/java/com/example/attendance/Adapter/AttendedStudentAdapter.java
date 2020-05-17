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

import com.example.attendance.Model.Student;
import com.example.attendance.R;

import java.util.ArrayList;
import java.util.HashSet;

public class  AttendedStudentAdapter extends RecyclerView.Adapter<AttendedStudentAdapter.ViewHolder> {
    public static ArrayList<Student> studentList;
    public static HashSet<String> attendedStudentSet;
    Context context;

    public AttendedStudentAdapter(ArrayList<Student> studentList, HashSet<String> attendedStudentSet, Context context) {
        this.studentList = studentList;
        this.attendedStudentSet = attendedStudentSet;
        this.context = context;
    }

    @NonNull
    @Override
    public AttendedStudentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_student,parent,false);
        return new AttendedStudentAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.txt_msv.setText(studentList.get(position).getStudentID());
        holder.txt_hoten.setText(studentList.get(position).getHoTen());
        holder.txt_monhoc.setText(studentList.get(position).getClassName());
        holder.checkBox.setChecked(studentList.get(position).isDihoc());
        if(attendedStudentSet != null){
            if(attendedStudentSet.contains(studentList.get(position).getStudentID())){
                holder.checkBox.setChecked(true);
            }else{
                holder.checkBox.setChecked(false);
            }
        }
        holder.checkBox.setChecked(studentList.get(position).isDihoc());

    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txt_msv,txt_hoten,txt_monhoc;
        CheckBox checkBox;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            txt_msv = (TextView)itemView.findViewById(R.id.tv_msv);
            txt_hoten = (TextView)itemView.findViewById(R.id.tv_tensv);
            txt_monhoc = (TextView)itemView.findViewById(R.id.tv_monhoc);
            checkBox = (CheckBox)itemView.findViewById(R.id.checkBox);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    //  studentList.get(getAdapterPosition()).setDihoc(b);
                    studentList.get(getAdapterPosition()).setDihoc(b);
                }
            });

        }
    }
}
