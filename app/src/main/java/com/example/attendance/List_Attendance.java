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
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;

import com.example.attendance.ui.home.HomeFragment;

import java.io.Serializable;
import java.util.ArrayList;

public class List_Attendance extends AppCompatActivity {
    Button btn_accecpt;
    RecyclerView recyclerView;
    StudentAdapter mStudent;

    androidx.appcompat.widget.Toolbar toolbar_diemdanh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list__attendance);

        btn_accecpt = (Button)findViewById(R.id.btn_accept);
        toolbar_diemdanh = (Toolbar)findViewById(R.id.toolbar_list_diemdanh);
        recyclerView = (RecyclerView)findViewById(R.id.rev_list_student);

        mStudent = new StudentAdapter(MainActivity.list_student_danhsachdauvao,List_Attendance.this);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),DividerItemDecoration.VERTICAL);
        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(),R.drawable.divider_custom);
        dividerItemDecoration.setDrawable(drawable);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mStudent);
        setSupportActionBar(toolbar_diemdanh);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar_diemdanh.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_accecpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(List_Attendance.this,List_NoAttendance.class);
                startActivity(intent);
            }
        });
    }
}
