package com.myapp.classroomupdates.interfaces;

import android.util.Log;

import com.myapp.classroomupdates.R;
import com.myapp.classroomupdates.model.AttendanceModel;
import com.myapp.classroomupdates.model.FeedbackModel;
import com.myapp.classroomupdates.model.LoginResponseModel;
import com.myapp.classroomupdates.model.PostResponse;
import com.myapp.classroomupdates.model.StudentHomeModel;
import com.myapp.classroomupdates.model.StudentModel;
import com.myapp.classroomupdates.model.StudentScheduleModel;
import com.myapp.classroomupdates.model.TeacherAttendModel;
import com.myapp.classroomupdates.model.TeacherModel;
import com.myapp.classroomupdates.model.TeacherScheduleModel;

import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("login/")
    @FormUrlEncoded
    Call<LoginResponseModel> getUserDetails(@Field("username") String email,
                                            @Field("password") String password);

    @GET("")//api url
    @FormUrlEncoded
    Call<List<StudentScheduleModel>> getStudentDailySchedule(@Field("day") String day,
                                                      @Field("semester") String semester,
                                                      @Field("program") String program,
                                                      @Field("group") String group);

    @GET("")
    Call<List<TeacherScheduleModel>> getteacherDailySchedule(@Field("day") String day,
                                                             @Field("email") String teacherEmail);

    @GET("")
    @FormUrlEncoded
    Call<List<StudentHomeModel>> getStudentHome(@Field("day") String day,
                                                @Field("semester") String semester,
                                                @Field("program") String program,
                                                @Field("group") String group);

    @GET("")
    @FormUrlEncoded
    Call<LoginResponseModel> getStudentDetails(@Field("email") String email,
                                               @Field("password") String password);

//    @Headers({"Content-Type: application/json"})
//    @POST("login/")
//    @FormUrlEncoded
//    Call<LoginResponseModel> getTeacherDetails(@Field("username") String email,
//                                               @Field("password") String password);


    @POST("login/")
    @FormUrlEncoded
    Call<ResponseBody> getTeacherDetails(@Field("username") String email,
                                         @Field("password") String password);


    @POST("login/")
    @Headers("Content-Type: application/json")
    Call<LoginResponseModel> getTeacherDetailsBody(@Body TeacherModel requestBody);

    @POST("")
    @FormUrlEncoded
    Call<PostResponse> changePassword(@Field("profession") String teacherOrStudent,
                                      @Field("email") String email,
                                      @Field("newPassword") String newPassword);
//
//    @POST("")
//    @FormUrlEncoded
//    Call<PostResponse> postTeacherAttend(@Field("attend") Boolean attend,
//                                         @Field("time") String time,
//                                         @Field("message") String message,
//                                         @Field("email") String teacherEmail,
//                                         @Field("subject") String subject,
//                                         @Field("date") String date,
//                                         @Field("semester") String semester,
//                                         @Field("program") String program);


    @POST("")
    @FormUrlEncoded
    Call<PostResponse> postTeacherAttend(@Body TeacherAttendModel teacherAttendModel);

    @GET("")
    @FormUrlEncoded
    Call<List<FeedbackModel>> getFeedback(@Field("email") String teacherEmail);//Filter using date may be, or subjects

    @GET("")
    @FormUrlEncoded
    Call<AttendanceModel> getAttendance(@Field("email") String studentEmail,
                                        @Field("subject") String subject);

    @POST("")
    @FormUrlEncoded
    @Headers("Content-Type: application/json")
    Call<PostResponse> sendFeedback(@Body FeedbackModel feedbackModelObject,
                                    @Field("teacher") String email);

    @POST("")
    @FormUrlEncoded
    Call<PostResponse> changePicture(@Field("encodedImageString") String encodedImageString,
                                     @Field("email") String email,
                                     @Field("profession") String teacherOrStudent);

    @POST("")
    @FormUrlEncoded
    @Headers("Content-Type: application/json")
    Call<PostResponse> registerStudent(@Body StudentModel studentObject);

    @POST("")
    @FormUrlEncoded
    @Headers("Content-Type: application/json")
    Call<PostResponse> registerTeacher(@Body TeacherModel teacherObject);







}
