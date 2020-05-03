package com.example.attendance;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DiemDanh extends AppCompatActivity {
    androidx.appcompat.widget.Toolbar toolbar_diemdanh;
    Button btn_diemdanh_AI,btn_diemdanh;

    TextView txt_start1,txt_finish1,txt_classroom1,txt_MonHoc1,txt_giangvien;
    ImageView anh;
    static final int REQUEST_ID_IMAGE_CAPTURE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diem_danh);
        btn_diemdanh_AI = (Button)findViewById(R.id.button_permission_AI);
        btn_diemdanh = (Button)findViewById(R.id.button_permission);
        anh = (ImageView)findViewById(R.id.image);
        toolbar_diemdanh = (Toolbar) findViewById(R.id.toolbar_diemdanh);
        txt_start1 = (TextView)findViewById(R.id.tv_start1);
        txt_finish1=(TextView)findViewById(R.id.tv_finish1);
        txt_classroom1 = (TextView)findViewById(R.id.tv_classroom1);
        txt_giangvien = (TextView)findViewById(R.id.txt_giangvien);

        txt_MonHoc1 = (TextView)findViewById(R.id.tv_subject);

        Bundle extras = getIntent().getExtras();
        if(extras!= null)
        {
            txt_start1.setText(extras.getString("sujectclass"));
            txt_finish1.setText(extras.getString("datetime"));
            txt_classroom1.setText(extras.getString("shift"));
            txt_giangvien.setText(extras.getString("eventID"));

        }
        setSupportActionBar(toolbar_diemdanh);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar_diemdanh.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_diemdanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DiemDanh.this,List_Attendance.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("eventID", txt_giangvien.getText().toString());
                startActivity(intent);
            }
        });
        btn_diemdanh_AI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyPermission();
            }
        });
    }


    private void verifyPermission()
    {
        final int permission_camera = ContextCompat.checkSelfPermission(DiemDanh.this, Manifest.permission.CAMERA);
        if(permission_camera != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(DiemDanh.this,Manifest.permission.CAMERA))
            {
                ActivityCompat.requestPermissions(DiemDanh.this, new String[]{Manifest.permission.CAMERA},
                        REQUEST_ID_IMAGE_CAPTURE);
            }
            else
            {
                ActivityCompat.requestPermissions(DiemDanh.this, new String[]{Manifest.permission.CAMERA},
                        REQUEST_ID_IMAGE_CAPTURE);
            }
        }
        else {
            Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent1, REQUEST_ID_IMAGE_CAPTURE);

        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == REQUEST_ID_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Intent intent = new Intent(DiemDanh.this,List_Attendance.class);
            startActivity(intent);
//          Bundle extras = data.getExtras();
//          Bitmap imageBitmap = (Bitmap) extras.get("data");
//          anh.setImageBitmap(imageBitmap);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){
            case REQUEST_ID_IMAGE_CAPTURE:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults.length>0){
                   verifyPermission();
                }else{
                    Toast.makeText(DiemDanh.this,"Permission denied....",Toast.LENGTH_SHORT).show();
                }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
