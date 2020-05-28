package com.example.attendance.ui.send;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.attendance.Activity.Main2Activity;
import com.example.attendance.Model.Acount;
import com.example.attendance.R;
import com.example.attendance.Sqlife.DBHelper;

import java.util.ArrayList;

public class SendFragment extends Fragment {

    Button btn_xacnhan,btn_huy;
    EditText ed_passwordOld,ed_passwordNew;
    String taikhoan,matkhau;
    DBHelper dbHelper;
    ArrayList<Acount> listAcount;
    private String getValue(EditText editText) {
        return editText.getText().toString().trim();
    }
    @Override
    public void onStart()
    {
        super.onStart();
        dbHelper.openDB();
    }
    @Override
    public void onStop()
    {
        super.onStop();
        dbHelper.closeDB();
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_send, container, false);
        ed_passwordOld = root.findViewById(R.id.editText_mkcu);
        ed_passwordNew = root.findViewById(R.id.editText_mkmoi);
        btn_xacnhan = root.findViewById(R.id.button_xacnhan1);
        btn_huy = root.findViewById(R.id.button_huy1);
        taikhoan = Main2Activity.taikhoan;
        matkhau = Main2Activity.matkhau;
        dbHelper = new DBHelper(getContext());
        btn_xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ed_passwordOld.getText().length() !=0 && ed_passwordNew.getText().length() !=0) {
                    if (ed_passwordOld.getText().toString().equals(matkhau)) {
                        long resultUpdate = dbHelper.Update(taikhoan, getValue(ed_passwordNew));
                        if (resultUpdate == 0) {
                            Toast.makeText(getActivity(), "Lỗi", Toast.LENGTH_SHORT);
                        } else if (resultUpdate == 1) {
                            Toast.makeText(getActivity(), "Thay đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                            ed_passwordNew.setText("");
                            ed_passwordOld.setText("");
                        } else {
                            Toast.makeText(getActivity(), "Lỗi,update theo username", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(getActivity(), "Sai mật khẩu cũ,vui lòng nhập lại", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(getActivity(), "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed_passwordNew.setText("");
                ed_passwordOld.setText("");
            }
        });
        return root;
    }
}