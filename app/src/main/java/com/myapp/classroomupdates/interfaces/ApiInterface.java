package com.myapp.classroomupdates.interfaces;

import com.myapp.classroomupdates.model.AttendClassRequestModel;
import com.myapp.classroomupdates.model.CanGiveFeedbackModel;
import com.myapp.classroomupdates.model.ClassResponseModel;
import com.myapp.classroomupdates.model.FeedbackModel;
import com.myapp.classroomupdates.model.ImageUploadResponseModel;
import com.myapp.classroomupdates.model.LoginResponseModel;
import com.myapp.classroomupdates.model.MarksResponseModel;
import com.myapp.classroomupdates.model.NoticeModel;
import com.myapp.classroomupdates.model.PostResponse;
import com.myapp.classroomupdates.model.ProgrammeModel;
import com.myapp.classroomupdates.model.ScheduleModel;
import com.myapp.classroomupdates.model.SetArrivalTimeModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("get_internal_marks/")
    Call<List<MarksResponseModel>> getMarks(@Header("Authorization") String token);

    @GET("get_routine_update_detail/")
    Call<List<ScheduleModel>> getUpdatedRoutine(@Header("Authorization") String token);

    @POST("upload_image/")
    @FormUrlEncoded
    Call<ImageUploadResponseModel> uploadImage(@Header("Authorization") String token,
                                               @Field("image") String base64EncodedImageString);

    @POST("class-attending-detail/")
    Call<ClassResponseModel> sendAttendDetails(@Header("Authorization") String token,
                                               @Body AttendClassRequestModel requestModel);

    @POST("arrival-time-detail/")
    Call<SetArrivalTimeModel> setArrivalTime(@Header("Authorization") String token,
                                             @Body SetArrivalTimeModel arrivalTimeModel);

    @GET("programme/")
    Call<List<ProgrammeModel>> getAllPrograms(@Header("Authorization")String token);

    @POST("notice/")
    Call<NoticeModel> sendNotice(@Header("Authorization") String token,
                                 @Body NoticeModel model);

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
