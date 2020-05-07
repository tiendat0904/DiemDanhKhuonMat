package com.example.attendance;

import com.example.attendance.Model.Event_Details;
import com.example.attendance.Model.Shift;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {
    @GET("SubjectClasses")
    Call<List<SubjectClass>> getCSubjectClass();


//    @GET("/api/SubjectClasses")
//    Call<List<SubjectClass>> getStudent(@Query("subjectClassID") String id);
}
