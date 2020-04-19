package com.example.attendance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.attendance.ui.home.HomeFragment;

import java.util.ArrayList;

public class List_NoAttendance extends AppCompatActivity {
    Button btn_accept;
    TextView a;
    androidx.appcompat.widget.Toolbar toolbar_diemdanh;
    Student_NoAttendance_Adapter mStudent;
    ArrayList<Student> list_no_attendance = new ArrayList<>();
    RecyclerView recyclerView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list__no_attendance);
        btn_accept = (Button)findViewById(R.id.btn_accept_noattendance);
        toolbar_diemdanh = (Toolbar)findViewById(R.id.toolbar_list_khongdiemdanh);
        setSupportActionBar(toolbar_diemdanh);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        for (Student student : MainActivity.list_student_danhsachdauvao){
            if (!student.isDiHoc()){
                list_no_attendance.add(student);
            }
        }

        recyclerView2 = (RecyclerView)findViewById(R.id.recyclerview2);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(mLayoutManager);
        recyclerView2.setItemAnimator(new DefaultItemAnimator());
        mStudent = new Student_NoAttendance_Adapter(list_no_attendance,List_NoAttendance.this);
       // mStudent.notifyDataSetChanged();
        recyclerView2.setAdapter(mStudent);
        toolbar_diemdanh.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(List_NoAttendance.this,Attendance_Success.class);
                startActivity(intent);
            }
        });
    }
}
