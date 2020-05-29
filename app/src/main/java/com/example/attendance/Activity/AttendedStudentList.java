package com.example.attendance.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendance.API.Student_API;
import com.example.attendance.Adapter.AttendedStudentAdapter;
import com.example.attendance.Adapter.StudentAdapter;
import com.example.attendance.Common.Const;
import com.example.attendance.Model.Student;
import com.example.attendance.R;
import com.example.attendance.ui.Other.UnsafeOkHttpClient;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AttendedStudentList extends AppCompatActivity {
    Button btn_re_check_attendance;
    RecyclerView recyclerView;
    AttendedStudentAdapter mStudent;
    androidx.appcompat.widget.Toolbar toolbar_diemdanh;
    public static ArrayList<Student> studentArrList=new ArrayList<Student>();
    String eventId ="";
    private HashSet<String> attendedStudentSet;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        setContentView(R.layout.activity_attended_student_list);
        btn_re_check_attendance = (Button)findViewById(R.id.btn_re_check_attendance);
        toolbar_diemdanh = (Toolbar)findViewById(R.id.toolbar_list_diemdanh);
        recyclerView = (RecyclerView)findViewById(R.id.attended_student_recycle_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),DividerItemDecoration.VERTICAL);
        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(),R.drawable.divider_custom);
        dividerItemDecoration.setDrawable(drawable);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        getStudent();
        btn_re_check_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialAlertDialogBuilder(AttendedStudentList.this)
                        .setTitle("CẢNH BÁO")
                        .setMessage("Buổi học này đã được điểm danh, bạn có muốn điểm danh lại buổi học?")
                        .setIcon(R.drawable.ic_warning_black_24dp)
                        .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Integer subjectClassID = extras.getInt("subjectClassID");
                                Intent intent = new Intent(AttendedStudentList.this, RecheckAttendance.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("subjectClassID", subjectClassID);
                                intent.putExtra("eventID", eventId);
                                intent.putExtra("hasAttendedStudentList", false);
                                intent.putExtra("studentList", (Serializable)studentArrList);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Hủy bỏ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
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
                attendedStudentSet = (HashSet<String>) extras.getSerializable("studentSet");
            
        }
        OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Const.DOMAIN_NAME + "Students/").client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()).build();
        Student_API student_Service =retrofit.create(Student_API.class);
        Call<List<Student>> call = student_Service.getAttendedStudentList(eventId);
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
                studentArrList = (ArrayList<Student>) response.body();
                mStudent = new AttendedStudentAdapter(studentArrList, attendedStudentSet, AttendedStudentList.this);
                recyclerView.setAdapter(mStudent);

            }

            @Override
            public void onFailure(Call<List<Student>> call, Throwable t) {
//                Log.d("aaa", t.getCause().getMessage());
            }
        });
    }
}
