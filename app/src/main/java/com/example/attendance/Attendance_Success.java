package com.example.attendance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.attendance.ui.home.HomeFragment;
import com.example.attendance.ui.home.HomeViewModel;

import java.util.ArrayList;

public class Attendance_Success extends AppCompatActivity {
    TextView siso;
    Button btn_thoat;
    ArrayList<Student> list_no_attendance = new ArrayList<>();
    int dihoc=0,khongdihoc=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance__success);
        siso = (TextView)findViewById(R.id.textView_result);
        btn_thoat = (Button)findViewById(R.id.button_thoat);
        for (Student student : MainActivity.list_student_danhsachdauvao){
            if (!student.isDiHoc()){
                list_no_attendance.add(student);
                khongdihoc++;
            }
        }
        dihoc =MainActivity.list_student_danhsachdauvao.size()-khongdihoc;
        //int dihoc = intent.getIntExtra("songuoikhongdihoc",0);
        siso.setText(dihoc+"/"+MainActivity.list_student_danhsachdauvao.size()+" người");
        btn_thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Attendance_Success.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
