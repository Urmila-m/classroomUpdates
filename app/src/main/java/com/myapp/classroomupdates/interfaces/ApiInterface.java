package com.myapp.classroomupdates.interfaces;

import com.google.gson.JsonElement;
import com.myapp.classroomupdates.model.AttendanceModel;
import com.myapp.classroomupdates.model.CanGiveFeedbackModel;
import com.myapp.classroomupdates.model.FeedbackModel;
import com.myapp.classroomupdates.model.LoginResponseModel;
import com.myapp.classroomupdates.model.PostResponse;
import com.myapp.classroomupdates.model.ScheduleModel;
import com.myapp.classroomupdates.model.StudentModel;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("feedback/")
    Call<List<FeedbackModel>> getAllFeedback(@Header("Authorization")String token);

    @GET("get_list_of_teachers/")
    Call<List<LoginResponseModel>> getListOfTeachers(@Header("Authorization") String token);

    @POST("feedback/")
    @FormUrlEncoded
    Call<FeedbackModel> giveFeedback(@Header("Authorization") String token,
                                     @Field("teacher") int teacherId,
                                     @Field("feedback_by") int studentId,
                                     @Field("review") String review);

    @GET("can_send_feedback/")
    Call<CanGiveFeedbackModel> canGiveFeedback(@Header("Authorization") String token);

    @POST("login/")
    @FormUrlEncoded
    Call<LoginResponseModel> getUserDetails(@Field("username") String email,
                                            @Field("password") String password);

    @GET("get_routine/")
    Call<List<ScheduleModel>> getUserRoutine(@Header("Authorization")String token,
                                             @Query("date") String date);

    @POST("rest-auth/password/change/")
    @FormUrlEncoded
    Call<PostResponse> changeUserPassword(@Header("Authorization") String token,
                                         @Field("new_password1") String new_password1,
                                         @Field("new_password2") String new_password2);


}
