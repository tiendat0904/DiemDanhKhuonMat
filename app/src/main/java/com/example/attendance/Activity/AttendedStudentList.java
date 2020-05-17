package com.example.attendance.Activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
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

public class AttendedStudentList extends AppCompatActivity {
    Button btn_accecpt;
    RecyclerView recyclerView;
    AttendedStudentAdapter mStudent;
    androidx.appcompat.widget.Toolbar toolbar_diemdanh;
    public static ArrayList<Student> studentArrList=new ArrayList<Student>();
    String eventId ="";
    private HashSet<String> attendedStudentSet;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attended_student_list);
        btn_accecpt = (Button)findViewById(R.id.btn_accept);
        toolbar_diemdanh = (Toolbar)findViewById(R.id.toolbar_list_diemdanh);
        recyclerView = (RecyclerView)findViewById(R.id.rev_list_student);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),DividerItemDecoration.VERTICAL);
        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(),R.drawable.divider_custom);
        dividerItemDecoration.setDrawable(drawable);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        getStudent();
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
//                ArrayList<Student> studentArrayList = new ArrayList<>();
//                for(int i=0;i<studentList.size();i++)
//                {
//                    if(hasAttendedStudentList == true){
//                        studentArrayList.add(new Student(studentList.get(i).getStudentID(),studentList.get(i).getHoTen(),studentList.get(i).getClassName()));
//                    }else{
//
//                    }
//                }
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
