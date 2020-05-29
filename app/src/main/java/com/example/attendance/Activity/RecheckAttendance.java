package com.example.attendance.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.attendance.API.AttendanceDetailsService;
import com.example.attendance.API.Student_API;
import com.example.attendance.Adapter.AttendedStudentAdapter;
import com.example.attendance.Adapter.RecheckAttendanceAdapter;
import com.example.attendance.Common.Const;
import com.example.attendance.Model.AttendanceDetail;
import com.example.attendance.Model.Student;
import com.example.attendance.R;
import com.example.attendance.ui.Other.UnsafeOkHttpClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecheckAttendance extends AppCompatActivity {
    Button btn_update_attendance;
    RecyclerView recyclerView;
    RecheckAttendanceAdapter mStudent;
    androidx.appcompat.widget.Toolbar toolbar_diemdanh;
    public static ArrayList<Student> studentArrList= new ArrayList<Student>();
    String eventId ="";
    Integer subjectClassID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recheck_attendance);
        Bundle extras = getIntent().getExtras();
        btn_update_attendance = (Button)findViewById(R.id.btn_update_attendance);
        toolbar_diemdanh = (Toolbar)findViewById(R.id.toolbar_list_diemdanh);
        recyclerView = (RecyclerView)findViewById(R.id.recheck_attendance_recycle_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),DividerItemDecoration.VERTICAL);
        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(),R.drawable.divider_custom);
        dividerItemDecoration.setDrawable(drawable);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        this.subjectClassID = extras.getInt("subjectClassID");
        getStudent();
        btn_update_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Student> attendedStudentList = new  ArrayList<Student>();
                studentArrList = mStudent.getStudentList();
                for(int i = 0; i< studentArrList.size(); i++){
                    if(studentArrList.get(i).isDihoc() == true){
                        Student student = studentArrList.get(i);
                        attendedStudentList.add(student);
                    }
                }
                AttendanceDetail attendanceDetail = new AttendanceDetail();
                attendanceDetail.setEventID(Integer.parseInt(eventId));
                attendanceDetail.setSubjectClassID(subjectClassID);
                attendanceDetail.setStudentList(attendedStudentList);

                OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();
                Retrofit retrofit = new Retrofit.Builder().baseUrl(Const.DOMAIN_NAME + "AttendanceDetails/").client(okHttpClient)
                        .addConverterFactory(GsonConverterFactory.create()).build();
                AttendanceDetailsService attendanceDetailsService =retrofit.create(AttendanceDetailsService.class);
                attendanceDetailsService.Update(attendanceDetail.getEventID(), attendanceDetail).enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        Intent intent = new Intent(RecheckAttendance.this, MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {

                    }
                });
            }
        });
    }
    private void getStudent() {


        Bundle extras = getIntent().getExtras();
        if(extras!= null)
        {
            eventId= extras.getString("eventID");
            ArrayList<Student> list;
//            for(int i = 0; i< list.size(); i++){
//                if(attendedStudentSet.contains(list.get(i).getStudentID())){
//                    list.get(i).isDihoc() == true;
//                }
//            }

//                attendedStudentList = (ArrayList<StudentDTO>) extras.getSerializable("studentList");

        }
        OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Const.DOMAIN_NAME + "Students/").client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()).build();
        Student_API student_Service =retrofit.create(Student_API.class);
        Call<List<Student>> call = student_Service.getAttendedStudentList(eventId);
        call.enqueue(new Callback<List<Student>>() {
            @Override
            public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                if(!response.isSuccessful()) {
                    try {
                        Log.d("aaa", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }
                recyclerView.setAdapter(null);
                studentArrList = (ArrayList<Student>) response.body();
                mStudent = new RecheckAttendanceAdapter(studentArrList, RecheckAttendance.this);
                recyclerView.setAdapter(mStudent);

            }

            @Override
            public void onFailure(Call<List<Student>> call, Throwable t) {
//                Log.d("aaa", t.getCause().getMessage());
            }
        });
    }
}
