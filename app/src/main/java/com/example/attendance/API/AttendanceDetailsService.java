package com.example.attendance.API;

import com.example.attendance.Model.AttendanceDetail;
import com.example.attendance.Model.Event_Post;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AttendanceDetailsService {
    @POST("List")
    Call Create(@Body AttendanceDetail attendanceDetail);
}
