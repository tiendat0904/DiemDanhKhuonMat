package com.example.attendance.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.attendance.API.IdentifyAPI;
import com.example.attendance.Model.StudentDTO;
import com.example.attendance.R;
import com.example.attendance.ui.Other.UnsafeOkHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DiemDanh extends AppCompatActivity {
    androidx.appcompat.widget.Toolbar toolbar_diemdanh;
    Button btn_diemdanh_AI,btn_diemdanh;
    String event_id="";
    File tempFile;
    TextView txt_start1,txt_classroom1,txt_shift;
    ImageView anh;
    static final int REQUEST_ID_IMAGE_CAPTURE = 100;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diem_danh);
        anhxa();

        Bundle extras = getIntent().getExtras();
        if(extras!= null)
        {
            txt_classroom1.setText(extras.getString("sujectclass"));
            txt_start1.setText(extras.getString("datetime"));
            txt_shift.setText(extras.getString("shift"));
            event_id=extras.getString("eventID");

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
                Intent intent = new Intent(DiemDanh.this, List_Attendance.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("eventID", event_id);
                intent.putExtra("hasAttendedStudentList", false);
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

    private void anhxa() {
        btn_diemdanh_AI = (Button)findViewById(R.id.button_permission_AI);
        btn_diemdanh = (Button)findViewById(R.id.button_permission);
        anh = (ImageView)findViewById(R.id.imageView);
        toolbar_diemdanh = (Toolbar) findViewById(R.id.toolbar_diemdanh);
        txt_start1 = (TextView)findViewById(R.id.tv_start1);
        txt_classroom1 = (TextView)findViewById(R.id.tv_classroom1);
        txt_shift= (TextView)findViewById(R.id.shift);
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
//            Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            startActivityForResult(intent1, REQUEST_ID_IMAGE_CAPTURE);
            dispatchTakePictureIntent();
        }
    }
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//
//        if (requestCode == REQUEST_ID_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            Intent intent = new Intent(DiemDanh.this,List_Attendance.class);
//            startActivity(intent);
////          Bundle extras = data.getExtras();
////          Bitmap imageBitmap = (Bitmap) extras.get("data");
////          anh.setImageBitmap(imageBitmap);
//
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            anh.setImageBitmap(imageBitmap);
            byte[] byteArr = convertBitmapToByteArr(imageBitmap);
            // Starts writing the bytes in it
            try {
                this.tempFile = File.createTempFile("image", ".jpg");
                FileOutputStream os = new FileOutputStream(tempFile);
                os.write(byteArr);
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            sendImageToServer();
        }
        super.onActivityResult(requestCode, resultCode, data);
    };


    private byte[] convertBitmapToByteArr(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
    private void sendImageToServer() {
        Bundle extras = getIntent().getExtras();
        OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), this.tempFile);
        MultipartBody.Part fbody =  MultipartBody.Part.createFormData("file", this.tempFile.getName(), requestBody);
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://10.0.2.2:64535/api/").client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()).build();
        IdentifyAPI identifyAPI =retrofit.create(IdentifyAPI.class);
        Call<List<StudentDTO>> call = identifyAPI.identifyPerson(fbody);
        call.enqueue(new Callback<List<StudentDTO>>() {

            @Override
            public void onResponse(Call<List<StudentDTO>> call, Response<List<StudentDTO>> response) {

                if(!response.isSuccessful()){

                    try {
                        Log.d("aaa", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }
                ArrayList<StudentDTO> attendedStudentList = (ArrayList<StudentDTO>) response.body();
                HashSet<String> attendedSet = new HashSet<String>();
                for(int i = 0; i< attendedStudentList.size(); i++){
                    if(!attendedSet.contains(attendedStudentList.get(i).getStudentID())){
                        attendedSet.add(attendedStudentList.get(i).getStudentID());
                    }
                }
                Intent intent = new Intent(DiemDanh.this,List_Attendance.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.putExtra("eventID", txt_giangvien.getText().toString());
                String subjectClassID = extras.getString("sujectclass");
                intent.putExtra("eventID", event_id);
                intent.putExtra("studentSet", (Serializable) attendedSet);
                intent.putExtra("subjectClassID", subjectClassID);
                intent.putExtra("hasAttendedStudentList", true);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<List<StudentDTO>> call, Throwable t) {

            }
        });
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
