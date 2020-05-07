package com.example.attendance;

import com.example.attendance.Model.Shift;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ShiftService {
    @GET("Shifts")
    Call<List<Shift>> getShift();
}
