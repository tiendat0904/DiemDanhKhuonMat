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
import android.widget.TextView;

import com.example.attendance.API.Student_API;
import com.example.attendance.Adapter.Student_NoAttendance_Adapter;
import com.example.attendance.R;
import com.example.attendance.Model.Student;
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

public class List_NoAttendance extends AppCompatActivity {
    Button btn_accept;
    TextView a;
    androidx.appcompat.widget.Toolbar toolbar_diemdanh;
    Student_NoAttendance_Adapter mStudent;
    ArrayList<Student> list_no_attendance = new ArrayList<>();
    RecyclerView recyclerView;
    Boolean hasAttendedStudentList;
    String eventId="";
    private HashSet<String> attendedStudentSet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list__no_attendance);
        btn_accept = (Button)findViewById(R.id.btn_accept_noattendance);
        toolbar_diemdanh = (Toolbar)findViewById(R.id.toolbar_list_khongdiemdanh);
//        setSupportActionBar(toolbar_diemdanh);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        if(extras!= null)
        {
            eventId= extras.getString("eventID");
//            hasAttendedStudentList = extras.getBoolean("hasAttendedStudentList");
//            if(hasAttendedStudentList == true){
//                attendedStudentList = (ArrayList<StudentDTO>) extras.getSerializable("studentList");
               // attendedStudentSet = (HashSet<String>) extras.getSerializable("hasAttendedStudentList");
            //}
        }

//        for (Student student : List_Attendance.studentArrList){
////            if (!student.getDihoc()){
////                list_no_attendance.add(student);
////            }
////        }
        OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://10.0.2.2:64535/api/Events/").client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()).build();
        Student_API student_Service =retrofit.create(Student_API.class);
        Call<List<Student>> call = student_Service.getStudent(eventId);
        call.enqueue(new Callback<List<Student>>() {
            @Override
            public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                if(!response.isSuccessful()){
                    try {
                        Log.d("aaa", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }
                recyclerView.setAdapter(null);
                list_no_attendance = (ArrayList<Student>) response.body();
                ArrayList<Student> studentArrList= new ArrayList<Student>();
                for(Student student : List_Attendance.studentArrList)
                {
                    if(!student.isDihoc())
                    {
                        studentArrList.add(student);
                    }
                }
//                for(int i = 0; i<list_no_attendance.size(); i++)
//                {
//                    if(!attendedStudentSet.contains(list_no_attendance.get(i).getStudentID()))
//                    {
//                        studentArrList.add(new Student(list_no_attendance.get(i).getStudentID(),list_no_attendance.get(i).getHoTen(),list_no_attendance.get(i).getClassName()));
//                    }
//                }
                mStudent = new Student_NoAttendance_Adapter(studentArrList,List_NoAttendance.this);
                recyclerView.setAdapter(mStudent);

            }

            @Override
            public void onFailure(Call<List<Student>> call, Throwable t) {
//                Log.d("aaa", t.getCause().getMessage());
            }
        });
        recyclerView = (RecyclerView)findViewById(R.id.recyclerview2);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),DividerItemDecoration.VERTICAL);
        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(),R.drawable.divider_custom);
        dividerItemDecoration.setDrawable(drawable);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mStudent = new Student_NoAttendance_Adapter(list_no_attendance,List_NoAttendance.this);
       // mStudent.notifyDataSetChanged();
        recyclerView.setAdapter(mStudent);
//        toolbar_diemdanh.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(List_NoAttendance.this,List_Attendance.class);
//                startActivity(intent);
//            }
//        });

        btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle extras = getIntent().getExtras();
                Intent intent = new Intent(List_NoAttendance.this, Attendance_Success.class);
                intent.putExtra("eventID",eventId);
                intent.putExtra("subjectClassID", extras.getString("subjectClassID"));
                startActivity(intent);
            }
        });
    }
}
