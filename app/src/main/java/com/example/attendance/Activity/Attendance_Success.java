package com.example.attendance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.attendance.API.Student_API;
import com.example.attendance.ui.home.HomeFragment;
import com.example.attendance.ui.home.HomeViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Attendance_Success extends AppCompatActivity {
    TextView siso;
    Button btn_thoat;
    ArrayList<Student> list_no_attendance = new ArrayList<>();
    int dihoc=0,khongdihoc=0;
    String eventId="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance__success);
        siso = (TextView)findViewById(R.id.textView_result);
        btn_thoat = (Button)findViewById(R.id.button_thoat);
        for (Student student : List_Attendance.studentArrList){
            if (!student.isDihoc()){
               // list_no_attendance.add(student);
                khongdihoc++;
            }
        }
        dihoc =List_Attendance.studentArrList.size()-khongdihoc;
        //int dihoc = intent.getIntExtra("songuoikhongdihoc",0);
        siso.setText(dihoc+"/"+List_Attendance.studentArrList.size()+" người");
        btn_thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Attendance_Success.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
