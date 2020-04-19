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


import com.example.attendance.monthCalendar;

import java.util.ArrayList;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {
    ArrayList<DataShop> dataShops;
    Context context;

    public ShopAdapter(ArrayList<DataShop> dataShops, Context context) {
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
        holder.txt_start.setText(dataShops.get(position).getTxt1());
        holder.txt_finish.setText(dataShops.get(position).getTxt2());
        holder.txt_classroom.setText(dataShops.get(position).getTxt3());
        holder.txt_MonHoc.setText(dataShops.get(position).getTxtMonHoc());
    }

    @Override
    public int getItemCount() {
        return dataShops.size();
    }
    public void RemoveItems(int position){
        dataShops.remove(position);
        notifyItemRemoved(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txt_start,txt_finish,txt_classroom,txt_MonHoc;
        Button btn_diemdanh;
        public ViewHolder(final View itemView){
            super(itemView);
            txt_start = (TextView)itemView.findViewById(R.id.tv_start);
            txt_finish = (TextView)itemView.findViewById(R.id.tv_finish);
            txt_classroom = (TextView)itemView.findViewById(R.id.tv_classroom);
            txt_MonHoc = (TextView)itemView.findViewById(R.id.monHocCustom);

            itemView.setOnClickListener(new View.OnClickListener() {
               @Override
                public void onClick(View view) {
                   Intent intent = new Intent(context,DiemDanh.class);
                   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("start",txt_start.getText().toString());
                    intent.putExtra("finish",txt_finish.getText().toString());
                    intent.putExtra("classroom",txt_classroom.getText().toString());
                    intent.putExtra("subject",txt_MonHoc.getText().toString());
                   context.startActivity(intent);
               }
          });
        }
    }
}
