package com.example.attendance.ui.share;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.attendance.Activity.Main2Activity;
import com.example.attendance.Activity.MainActivity;
import com.example.attendance.Model.Acount;
import com.example.attendance.R;
import com.example.attendance.Sqlife.DBHelper;
import com.example.attendance.ui.home.HomeFragment;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ShareFragment extends Fragment {

    TextView txt_tk;
    Button btn_dangxuat;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_share, container, false);
        txt_tk = root.findViewById(R.id.textView_tentk);
        btn_dangxuat=root.findViewById(R.id.button_dangxuat);
        txt_tk.setText(Main2Activity.taikhoan);
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
                        Intent intent = new Intent(getActivity(),MainActivity.class);
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