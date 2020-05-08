package com.example.attendance.API;

import com.example.attendance.Model.Event_Details;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Event_details {
    @GET("Details")
    Call<List<Event_Details>> getEvent_Details();
}
