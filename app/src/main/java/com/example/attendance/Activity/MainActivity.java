package com.example.attendance.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.attendance.Model.Acount;
import com.example.attendance.R;
import com.example.attendance.Model.Student;
import com.example.attendance.Sqlife.DBHelper;
import com.example.attendance.ui.home.HomeFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btndangNhap, btndangKi;
    Toolbar toolbar;
    EditText edTaikhoan, edPassword;
    DBHelper dbHelper;
    ArrayList<Acount> listAcount;
    int dem = 0;

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
        dbHelper = new DBHelper(MainActivity.this);
        btndangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog  =new Dialog(MainActivity.this);
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
                                    Toast.makeText(MainActivity.this,"Đã có tên tài khoản này,vui lòng nhập lại",Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(MainActivity.this,"Đăng Kí thành công",Toast.LENGTH_SHORT).show();
                                    dialog.cancel();
                                }
                            }else{
                                Toast.makeText(MainActivity.this,"Mật khẩu không khớp",Toast.LENGTH_SHORT).show();
                            }

                        }else
                        {
                            Toast.makeText(MainActivity.this,"Vui Lòng nhập đầy đủ thông tin",Toast.LENGTH_SHORT).show();
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
                if(edTaikhoan.getText().length() !=0 && edPassword.getText().length() !=0)
                {
                    for(int i=0;i<listAcount.size();i++)
                    {
                        if(edTaikhoan.getText().toString().equals(listAcount.get(i).getUsername()) && edPassword.getText().toString().equals(listAcount.get(i).getPassword())) {
                            Toast.makeText(MainActivity.this, "Bạn đã đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("acount", edTaikhoan.getText().toString());
                            intent.putExtra("password", edPassword.getText().toString());
                            startActivity(intent);
                            dem++;
                        }
                    }
                    if(dem==0)
                    {
                        Toast.makeText(MainActivity.this, "Bạn đã nhập sai tài khản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(MainActivity.this,"Vui lòng nhập đầy đủ thông tin",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
