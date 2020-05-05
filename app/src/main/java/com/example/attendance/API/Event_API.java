package com.example.attendance.API;

import com.example.attendance.Model.Event_Details;
import com.example.attendance.Model.Event_Post;
import com.example.attendance.Student;
import com.google.api.client.util.DateTime;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Event_API {
//    @FormUrlEncoded
//    @POST("Events")
//    Call<Event_Post> createEvent(@Field("shiftID") String shiftID, @Field("subjectClassID") String subjectClassID, @Field("dateTime") String dateTime);
    @POST("Events")
    Call<Event_Post> Postdata(@Body RequestBody requestBody);
}
