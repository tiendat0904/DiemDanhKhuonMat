package com.example.attendance.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.attendance.Model.AcountDetail;
import com.example.attendance.R;
import com.example.attendance.Sqlife.DBHelper;

import java.util.ArrayList;

public class AddAcountDetail extends AppCompatActivity {

    Toolbar toolbar;
    EditText editText_name,editText_sdt,editText_diachi;
    Button btn_tieptuc;
    DBHelper dbHelper;
    String taikhoan,matkhau;
    ArrayList<AcountDetail> listAcountDetail;
    private String getValue(EditText editText) {
        return editText.getText().toString().trim();
    }
    @Override
    protected void onStart()
    {
        super.onStart();
        dbHelper.openDB();
    }
    @Override
    protected void onStop()
    {
        super.onStop();
        dbHelper.closeDB();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        toolbar = findViewById(R.id.toolbar3);
        editText_name = findViewById(R.id.ediText_name);
        editText_sdt = findViewById(R.id.editText_sdt);
        editText_diachi = findViewById(R.id.editText_diachi);
        btn_tieptuc = findViewById(R.id.button_tieptuc);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Thêm thông tin tài khoản");
        dbHelper = new DBHelper(AddAcountDetail.this);
        Bundle extras = getIntent().getExtras();
        if(extras!= null)
        {
            taikhoan = extras.getString("acount");
            matkhau = extras.getString("password");

        }
        btn_tieptuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText_name.getText().length() !=0 && editText_diachi.getText().length()!=0 && editText_sdt.getText().length()!=0)
                {
                        long resultAdd = dbHelper.Insert_chitiettaikhoan(getValue(editText_name),getValue(editText_sdt),getValue(editText_diachi),taikhoan);
                        if(resultAdd ==-1)
                        {
                            Toast.makeText(AddAcountDetail.this,"Lỗi,vui lòng kiểm tra lại",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(AddAcountDetail.this,"Thêm thông tin tài khoản thành công",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AddAcountDetail.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("acount", taikhoan);
                            intent.putExtra("password", matkhau);
                            intent.putExtra("name", editText_name.getText().toString().trim());
                            intent.putExtra("sdt", editText_sdt.getText().toString().trim());
                            intent.putExtra("diachi", editText_diachi.getText().toString().trim());
                            startActivity(intent);
                        }

                }else
                {
                    Toast.makeText(AddAcountDetail.this,"Vui Lòng nhập đầy đủ thông tin",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
