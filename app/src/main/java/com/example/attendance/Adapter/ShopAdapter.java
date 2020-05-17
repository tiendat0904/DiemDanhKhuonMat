package com.example.attendance.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.attendance.Activity.AttendedStudentList;
import com.example.attendance.Activity.DiemDanh;
import com.example.attendance.Model.Event_Details;
import com.example.attendance.R;

import java.util.ArrayList;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {
    ArrayList<Event_Details> dataShops;
    Context context;
    int status;

    public ShopAdapter(ArrayList<Event_Details> dataShops, Context context) {
        this.dataShops = dataShops;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_custom,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txt_sujectclass.setText(dataShops.get(position).getSubjectClassName());
        holder.txt_datetime.setText(dataShops.get(position).getDateTime1());
        holder.txt_shift.setText(dataShops.get(position).getShiftName());
        this.status = dataShops.get(position).getStatus();
        if(dataShops.get(position).getStatus() == 1){
            holder.txt_status.setText("Đã điểm danh");
            holder.txt_status.setTextColor(Color.GREEN);
        }else{
            holder.txt_status.setText("Chưa điểm danh");
            holder.txt_status.setTextColor(Color.RED);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (status != 1) {
                    Intent intent = new Intent(context, DiemDanh.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("sujectclass", holder.txt_sujectclass.getText().toString());
                    intent.putExtra("datetime", holder.txt_datetime.getText().toString());
                    intent.putExtra("shift", holder.txt_shift.getText().toString());
                    intent.putExtra("eventID", dataShops.get(position).getEventID());
                    intent.putExtra("subjectClassID", dataShops.get(position).getSubjectClassID());
                    context.startActivity(intent);
                }else{
                    Intent intent = new Intent(context, AttendedStudentList.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("sujectclass", holder.txt_sujectclass.getText().toString());
                    intent.putExtra("datetime", holder.txt_datetime.getText().toString());
                    intent.putExtra("shift", holder.txt_shift.getText().toString());
                    intent.putExtra("eventID", dataShops.get(position).getEventID());
                    intent.putExtra("subjectClassID", dataShops.get(position).getSubjectClassID());
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataShops.size();
    }
    public void RemoveItems(int position){
//        dataShops.remove(position);
//        notifyItemRemoved(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txt_sujectclass,txt_datetime,txt_shift, txt_status;
        public ViewHolder(final View itemView){
            super(itemView);
            txt_sujectclass = (TextView)itemView.findViewById(R.id.lophocphan);
            txt_datetime = (TextView)itemView.findViewById(R.id.tv_ngayhoc);
            txt_shift = (TextView)itemView.findViewById(R.id.tv_ca);
            txt_status = (TextView)itemView.findViewById(R.id.textview_state);

        }
    }
}
