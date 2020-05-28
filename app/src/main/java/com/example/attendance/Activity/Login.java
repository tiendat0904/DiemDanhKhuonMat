package com.example.attendance.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.attendance.Model.Acount;
import com.example.attendance.Model.AcountDetail;
import com.example.attendance.R;
import com.example.attendance.Sqlife.DBHelper;

import java.util.ArrayList;

public class Login extends AppCompatActivity {

    Button btndangNhap, btndangKi;
    Toolbar toolbar;
    EditText edTaikhoan, edPassword;
    DBHelper dbHelper;
    ArrayList<Acount> listAcount;
    ArrayList<AcountDetail> listAcountDetail;
    int dem = 0,dem1=0;

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
        setContentView(R.layout.activity_main);
        btndangNhap = findViewById(R.id.button_login);
        btndangKi = findViewById(R.id.button_logout);
        edTaikhoan = findViewById(R.id.editText_acount);
        edPassword = findViewById(R.id.editText_password);
        toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Đăng Nhập|Đăng Kí");
        dbHelper = new DBHelper(Login.this);

        btndangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog  =new Dialog(Login.this);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.customdialog);
                dialog.setTitle("Đăng Kí");
                TextView textView = dialog.findViewById(R.id.textView_dangki);
                EditText editText_tk = dialog.findViewById(R.id.editText_acount1);
                EditText editText_mk = dialog.findViewById(R.id.editText_password1);
                EditText editText_mk1 = dialog.findViewById(R.id.editText_password2);
                Button btn_huy = dialog.findViewById(R.id.button_huy);
                Button btn_xacnhan = dialog.findViewById(R.id.button_xacnhan);
                btn_xacnhan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(editText_mk.getText().length() !=0 && editText_tk.getText().length()!=0 && editText_mk1.getText().length()!=0)
                        {
                            if(editText_mk.getText().toString().equals(editText_mk1.getText().toString()))
                            {
                                long resultAdd = dbHelper.Insert(getValue(editText_tk),getValue(editText_mk));
                                if(resultAdd ==-1)
                                {
                                    Toast.makeText(Login.this,"Đã có tên tài khoản này,vui lòng nhập lại",Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(Login.this,"Đăng Kí thành công",Toast.LENGTH_SHORT).show();
                                    dialog.cancel();
                                }
                            }else{
                                Toast.makeText(Login.this,"Mật khẩu không khớp",Toast.LENGTH_SHORT).show();
                            }

                        }else
                        {
                            Toast.makeText(Login.this,"Vui Lòng nhập đầy đủ thông tin",Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                btn_huy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                dialog.show();
            }
        });
        btndangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listAcount=dbHelper.getAll();
                listAcountDetail=dbHelper.getAll_Detail();
                if(edTaikhoan.getText().length() !=0 && edPassword.getText().length() !=0)
                {
                    for(int i=0;i<listAcount.size();i++)
                    {
                        if(edTaikhoan.getText().toString().equals(listAcount.get(i).getUsername()) && edPassword.getText().toString().equals(listAcount.get(i).getPassword())) {
                            dem++;
                            Toast.makeText(Login.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            for(int k=0;k<listAcountDetail.size();k++)
                            {
                                if(edTaikhoan.getText().toString().equals(listAcountDetail.get(k).getUsername()))
                                {
                                    dem1++;
                                }
                            }

                        }
                    }
                    if(dem==0)
                    {
                        Toast.makeText(Login.this, "Bạn đã nhập sai tài khản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                    }else{
                        if(dem1==0)
                        {
                            Intent intent = new Intent(Login.this, AddAcountDetail.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("acount", edTaikhoan.getText().toString());
                            intent.putExtra("password", edPassword.getText().toString());
                            startActivity(intent);
                        }else{
                            for(int k=0;k<listAcountDetail.size();k++)
                            {
                                if(edTaikhoan.getText().toString().equals(listAcountDetail.get(k).getUsername()))
                                {
                                    Intent intent = new Intent(Login.this, MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.putExtra("acount", edTaikhoan.getText().toString().trim());
                                    intent.putExtra("password", edPassword.getText().toString().trim());
                                    intent.putExtra("name", listAcountDetail.get(k).getName());
                                    intent.putExtra("sdt", listAcountDetail.get(k).getSdt());
                                    intent.putExtra("diachi", listAcountDetail.get(k).getDiachi());
                                    startActivity(intent);
                                }

                            }

                        }
                    }

                }
                else{
                    Toast.makeText(Login.this,"Vui lòng nhập đầy đủ thông tin",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
