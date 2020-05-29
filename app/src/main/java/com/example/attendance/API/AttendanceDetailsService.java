package com.example.attendance.API;

import com.example.attendance.Model.AttendanceDetail;
import com.example.attendance.Model.Event_Post;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AttendanceDetailsService {
    @Headers("Content-Type: application/json")
    @POST("List")
    Call<AttendanceDetail> Create(@Body AttendanceDetail attendanceDetail);

    @Headers("Content-Type: application/json")
    @PUT("Event/{id}")
    Call<AttendanceDetail> Update(@Path ("id") Integer id, @Body AttendanceDetail attendanceDetail);
}
