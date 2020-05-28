package com.example.attendance.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.attendance.API.AttendanceDetailsService;
import com.example.attendance.Common.Const;
import com.example.attendance.Model.AttendanceDetail;
import com.example.attendance.R;
import com.example.attendance.Model.Student;
import com.example.attendance.ui.Other.UnsafeOkHttpClient;

import java.util.ArrayList;

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
    Integer subjectClassID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance__success);
        siso = (TextView)findViewById(R.id.textView_result);
        btn_thoat = (Button)findViewById(R.id.button_thoat);
        Bundle extras = getIntent().getExtras();
        this.eventId = extras.getString("eventID");
        this.subjectClassID = extras.getInt("subjectClassID");

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
                createAttendanceDetails(eventId, subjectClassID);
                Intent intent = new Intent(Attendance_Success.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void createAttendanceDetails(String eventId, Integer subjectClassID) {
        ArrayList<Student> attendedStudentList = new  ArrayList<Student>();
        for(int i = 0; i<List_Attendance.studentArrList.size(); i++){
            if(List_Attendance.studentArrList.get(i).isDihoc() == true){
                Student student = List_Attendance.studentArrList.get(i);
                attendedStudentList.add(student);
            }
        }
        AttendanceDetail attendanceDetail = new AttendanceDetail();
        attendanceDetail.setEventID(Integer.parseInt(eventId));
//        attendanceDetail.setSubjectClassID(Integer.parseInt(subjectClassID));
        attendanceDetail.setSubjectClassID(subjectClassID);
        attendanceDetail.setStudentList(attendedStudentList);

        OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Const.DOMAIN_NAME + "AttendanceDetails/").client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()).build();
        AttendanceDetailsService attendanceDetailsService =retrofit.create(AttendanceDetailsService.class);
        attendanceDetailsService.Create(attendanceDetail).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });

    }
}
