package com.example.attendance.API;

import com.example.attendance.Model.SubjectClass;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIService {
    @GET("SubjectClasses")
    Call<List<SubjectClass>> getCSubjectClass();


//    @GET("/api/SubjectClasses")
//    Call<List<SubjectClass>> getStudent(@Query("subjectClassID") String id);
}
