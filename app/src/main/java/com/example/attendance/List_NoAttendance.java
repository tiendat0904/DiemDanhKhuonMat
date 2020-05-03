package com.example.attendance;

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
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list__no_attendance);
        btn_accept = (Button)findViewById(R.id.btn_accept_noattendance);
        toolbar_diemdanh = (Toolbar)findViewById(R.id.toolbar_list_khongdiemdanh);
        setSupportActionBar(toolbar_diemdanh);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



//        for (Student student : MainActivity.list_student_danhsachdauvao){
//            if (!student.isDiHoc()){
//                list_no_attendance.add(student);
//            }
//        }

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
        toolbar_diemdanh.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(List_NoAttendance.this,List_Attendance.class);
                startActivity(intent);
            }
        });
        btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(List_NoAttendance.this,Attendance_Success.class);
//                intent.putExtra("songuoikhongdihoc",khongdihoc);
                startActivity(intent);
            }
        });
    }
}
