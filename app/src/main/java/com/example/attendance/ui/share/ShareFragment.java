package com.example.attendance.ui.share;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.attendance.Activity.MainActivity;
import com.example.attendance.Activity.Login;
import com.example.attendance.R;

public class ShareFragment extends Fragment {

    TextView txt_name,txt_sdt,txt_diachi;
    Button btn_dangxuat;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_share, container, false);
        txt_name = root.findViewById(R.id.textView_tentk);
        txt_sdt = root.findViewById(R.id.textView_sdt);
        txt_diachi = root.findViewById(R.id.textView_diachi);
        btn_dangxuat=root.findViewById(R.id.button_dangxuat);
        txt_name.setText(MainActivity.name);
        txt_sdt.setText(MainActivity.sdt);
        txt_diachi.setText(MainActivity.diachi);
        btn_dangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Đăng xuất");
                builder.setMessage("Bạn có muốn đăng xuất không?");
                builder.setCancelable(false);
                builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       // Toast.makeText(getActivity(), "Không thoát được", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        Intent intent = new Intent(getActivity(), Login.class);
                        startActivity(intent);
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        return root;
    }
}