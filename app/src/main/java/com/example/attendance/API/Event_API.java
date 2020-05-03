package com.example.attendance.API;

import com.example.attendance.Model.Event_Details;
import com.example.attendance.Model.Event_Post;
import com.example.attendance.Student;
import com.google.api.client.util.DateTime;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Event_API {
    @FormUrlEncoded
    @POST("Events")
    Call<Event_Post> createEvent(@Field("shiftID") int shiftid, @Field("subjectClassID") int subjectClassid, @Field("dateTime") DateTime date);
}
