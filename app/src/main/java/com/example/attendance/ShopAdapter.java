package com.example.attendance;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.attendance.Model.Event_Details;
import com.example.attendance.monthCalendar;

import java.text.ParseException;
import java.util.ArrayList;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {
    ArrayList<Event_Details> dataShops;
    Context context;

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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,DiemDanh.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("sujectclass", holder.txt_sujectclass.getText().toString());
                intent.putExtra("datetime", holder.txt_datetime.getText().toString());
                intent.putExtra("shift", holder.txt_shift.getText().toString());
                intent.putExtra("eventID",dataShops.get(position).getEventID());
                context.startActivity(intent);
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
        TextView txt_sujectclass,txt_datetime,txt_shift;
        public ViewHolder(final View itemView){
            super(itemView);
            txt_sujectclass = (TextView)itemView.findViewById(R.id.lophocphan);
            txt_datetime = (TextView)itemView.findViewById(R.id.tv_ngayhoc);
            txt_shift = (TextView)itemView.findViewById(R.id.tv_ca);


        }
    }
}
