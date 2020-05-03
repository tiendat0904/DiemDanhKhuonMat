package com.example.attendance.API;

import com.example.attendance.Model.Event_Details;
import com.example.attendance.Student;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Student_API {
    @GET("Students/{id}")
    Call<List<Student>> getStudent(@Path("id") String id);
}
