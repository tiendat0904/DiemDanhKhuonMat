package com.example.attendance.API;

import com.example.attendance.Model.StudentDTO;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface IdentifyAPI {
    @Multipart
    @POST("Identify")
    Call<List<StudentDTO>> identifyPerson(@Part MultipartBody.Part imageFile);
}
