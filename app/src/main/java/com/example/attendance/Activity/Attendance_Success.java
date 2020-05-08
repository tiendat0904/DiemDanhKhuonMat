package com.example.attendance.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.attendance.R;
import com.example.attendance.Model.Student;

import java.util.ArrayList;

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
