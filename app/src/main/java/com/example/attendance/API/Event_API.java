package com.example.attendance.API;

import com.example.attendance.Model.AttendanceDetail;
import com.example.attendance.Model.Event_Post;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Event_API {
//    @FormUrlEncoded
//    @POST("Events")
//    Call<Event_Post> createEvent(@Field("shiftID") String shiftID, @Field("subjectClassID") String subjectClassID, @Field("dateTime") String dateTime);
    @POST("Events")
    Call<Event_Post> Postdata(@Body RequestBody requestBody);

    @PUT("Events/{id}")
    Call<Event_API> Update(@Path("id") Integer id, @Body RequestBody requestBody);

    @DELETE("Events/{id}")
    Call<Event_API> Delete(@Path("id") Integer id);

}
