package com.myapp.classroomupdates;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.JsonObject;
import com.myapp.classroomupdates.interfaces.ApiInterface;
import com.myapp.classroomupdates.interfaces.OnDataRetrivedListener;
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

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.myapp.classroomupdates.Globals.CHANGE_PASSWORD;
import static com.myapp.classroomupdates.Globals.CHANGE_PICTURE;
import static com.myapp.classroomupdates.Globals.GET_ATTENDANCE;
import static com.myapp.classroomupdates.Globals.GET_DAILY_STUDENT_SCHEDULE;
import static com.myapp.classroomupdates.Globals.GET_DAILY_TEACHER_SCHEDULE;
import static com.myapp.classroomupdates.Globals.GET_FEEDBACK;
import static com.myapp.classroomupdates.Globals.GET_STUDENT;
import static com.myapp.classroomupdates.Globals.GET_STUDENT_HOME;
import static com.myapp.classroomupdates.Globals.GET_TEACHER;
import static com.myapp.classroomupdates.Globals.POST_TEACHER_ATTEND;
import static com.myapp.classroomupdates.Globals.REGISTER_STUDENT;
import static com.myapp.classroomupdates.Globals.REGISTER_TEACHER;
import static com.myapp.classroomupdates.Globals.SEND_FEEDBACK;
import static com.myapp.classroomupdates.Globals.isEmailValid;

public class ApiBackgroundTask {
    private ApiInterface apiInterface;

    public ApiBackgroundTask(){
        apiInterface= RetrofitClient.getRetrofitObj().create(ApiInterface.class);
    }

    public void getStudentHome(String day, final OnDataRetrivedListener listener){
        apiInterface.getStudentHome(day, "6", "Computer", "A").enqueue(new Callback<List<StudentHomeModel>>() {//TODO get value from pref
            @Override
            public void onResponse(Call<List<StudentHomeModel>> call, Response<List<StudentHomeModel>> response) {
                List<StudentHomeModel> list= response.body();
                Bundle b= new Bundle();
                b.putSerializable("StudentHomeList", (Serializable) list);
                if (listener!= null){
                    listener.onDataRetrieved(b, GET_STUDENT_HOME);
                }

            }

            @Override
            public void onFailure(Call<List<StudentHomeModel>> call, Throwable t) {
                Log.e("TAG", "onFailure: "+t.getMessage());
            }
        });
    }

    public void getStudentSchedule(String day, final OnDataRetrivedListener listener){
        apiInterface.getStudentDailySchedule(day, "6", "Computer", "B").enqueue(new Callback<List<StudentScheduleModel>>() {//TODO get the values from preference
            @Override
            public void onResponse(Call<List<StudentScheduleModel>> call, Response<List<StudentScheduleModel>> response) {
                List<StudentScheduleModel> list= response.body();
                Bundle b= new Bundle();
                b.putSerializable("DailyStudentSchedule", (Serializable) list);
                if (listener!=null){
                    listener.onDataRetrieved(b, GET_DAILY_STUDENT_SCHEDULE);
                }
            }

            @Override
            public void onFailure(Call<List<StudentScheduleModel>> call, Throwable t) {
                Log.e("TAG", "onFailure: "+ t.getMessage());
            }
        });
    }

    public void getTeacherSchedule(String day, final OnDataRetrivedListener listener){
        apiInterface.getteacherDailySchedule(day, "os@gmail.com").enqueue(new Callback<List<TeacherScheduleModel>>() {
            @Override
            public void onResponse(Call<List<TeacherScheduleModel>> call, Response<List<TeacherScheduleModel>> response) {
                List<TeacherScheduleModel> list= response.body();
                Bundle b= new Bundle();
                b.putSerializable("DailyTeacherSchedule", (Serializable) list);
                if (listener!=null){
                    listener.onDataRetrieved(b, GET_DAILY_TEACHER_SCHEDULE);
                }
            }

            @Override
            public void onFailure(Call<List<TeacherScheduleModel>> call, Throwable t) {
                Log.e("TAG", "onFailure: "+ t.getMessage());

            }
        });
    }

    public void getStudentDetails(String email, String password, final OnDataRetrivedListener listener){
        apiInterface.getStudentDetails(email, password).enqueue(new Callback<LoginResponseModel>() {
            @Override
            public void onResponse(Call<LoginResponseModel> call, Response<LoginResponseModel> response) {
                StudentModel student= response.body().getStudent_detail();
                Bundle b= new Bundle();
                b.putSerializable("student", (Serializable) student);
                b.putString("token", response.body().getToken());
                if (listener!=null){
                    listener.onDataRetrieved(b, GET_STUDENT);
                }
            }

            @Override
            public void onFailure(Call<LoginResponseModel> call, Throwable t) {
                Log.e("TAG", "onFailure: "+ t.getMessage());

            }
        });
    }

//    public void getTeacherDetails(String email, String password, final OnDataRetrivedListener listener){
////        HashMap<String, String> hashMap= new HashMap<>();
////        hashMap.put("username", email);
////        hashMap.put("password", password);
//        apiInterface.getTeacherDetails(email, password).enqueue(new Callback<LoginResponseModel>() {
//            Bundle b = new Bundle();
//            @Override
//            public void onResponse(Call<LoginResponseModel> call, Response<LoginResponseModel> response) {
//                if (response.isSuccessful()) {
//                    TeacherModel teacher = response.body().getTeacher_detail();
//                    b.putSerializable("teacher", (Serializable) teacher);
//                    b.putSerializable("token", response.body().getToken());
//                    Log.e("TAG", "onResponse: successful");
//                }
//                else {
//                    Log.e("TAG", "onResponse: "+response.message());
////                    Log.e("TAG", "onResponse: on not succesful"+response.errorBody().toString() );
//                    b.putString("errorMsg", "Email or Password didn't match.");
//                }
//                if (listener != null) {
//                    listener.onDataRetrieved(b, GET_TEACHER);
//                }
//            }

////            @Override
////            public void onFailure(Call<LoginResponseModel> call, Throwable t) {
////                Log.e("TAG", "onFailure: "+ t.getMessage());
////
////            }
////        });
//    }

    public void getTeacherDetails(String email, String password, OnDataRetrivedListener listener){
        Log.e("TAG", "getTeacherDetails: "+email+ " "+ password);
        apiInterface.getTeacherDetails(email, password).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("OOPS",response.raw().toString());
                try {
                    if (response.body()!=null) {
                        Log.e("TAG", "onResponse: " + response.body().string());
                    }
                    else {
                        Log.e("TAG", "onResponse: "+response.code());
                        Log.e("TAG", "onResponse: null response" );
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public  void getTeacherDetailsBody()
    {
        TeacherModel model=new TeacherModel();
//        model.username="ooad@gmail.com";
        model.setPassword("687569");
        apiInterface.getTeacherDetailsBody(model)
                .enqueue(
                        new Callback<LoginResponseModel>() {
                            @Override
                            public void onResponse(Call<LoginResponseModel> call, Response<LoginResponseModel> response) {

                                Log.e("OOPS",response.body().getTeacher_detail().getName());
                            }

                            @Override
                            public void onFailure(Call<LoginResponseModel> call, Throwable t) {

                            }
                        }
                );
    }

    public void changePassword(String profession, String email, String newPassword, final OnDataRetrivedListener listener){
        apiInterface.changePassword(profession, email, newPassword).enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                PostResponse response1= response.body();
                Bundle b= new Bundle();
                b.putSerializable("response", response1);
                listener.onDataRetrieved(b, CHANGE_PASSWORD);
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                Log.e("TAG", "onFailure: "+ t.getMessage());

            }
        });
    }

    public void getFeedback(String email, final OnDataRetrivedListener listener){
        apiInterface.getFeedback(email).enqueue(new Callback<List<FeedbackModel>>() {
            @Override
            public void onResponse(Call<List<FeedbackModel>> call, Response<List<FeedbackModel>> response) {
                Bundle b= new Bundle();
                b.putSerializable("feedbackList", (Serializable) response.body());
                listener.onDataRetrieved(b, GET_FEEDBACK);
            }

            @Override
            public void onFailure(Call<List<FeedbackModel>> call, Throwable t) {
                Log.e("TAG", "onFailure: "+ t.getMessage());

            }
        });

    }

    public void sendFeedback(String teacherEmail, FeedbackModel feedbackModel, final OnDataRetrivedListener listener){
        apiInterface.sendFeedback(feedbackModel, teacherEmail).enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                Bundle b= new Bundle();
                b.putSerializable("response", response.body());
                listener.onDataRetrieved(b, SEND_FEEDBACK);
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                Log.e("TAG", "onFailure: "+ t.getMessage());

            }
        });
    }

    public void registerStudent(StudentModel studentModel, final OnDataRetrivedListener listener){
        apiInterface.registerStudent(studentModel).enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                Bundle b= new Bundle();
                b.putSerializable("response", response.body());
                listener.onDataRetrieved(b, REGISTER_STUDENT);
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                Log.e("TAG", "onFailure: "+ t.getMessage());

            }
        });
    }

    public void registerTeacher(TeacherModel teacherModel, final OnDataRetrivedListener listener){
        apiInterface.registerTeacher(teacherModel).enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                Bundle b= new Bundle();
                b.putSerializable("response", response.body());
                listener.onDataRetrieved(b, REGISTER_TEACHER);
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                Log.e("TAG", "onFailure: "+ t.getMessage());

            }
        });
    }

    public void changePicture(String encodedImageString, String email, String profession, final OnDataRetrivedListener listener){
        apiInterface.changePicture(encodedImageString, email, profession).enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                Bundle b= new Bundle();
                b.putSerializable("response", response.body());
                listener.onDataRetrieved(b, CHANGE_PICTURE);
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                Log.e("TAG", "onFailure: "+ t.getMessage());

            }
        });
    }

    public void getAttendance(String email, String subject, final OnDataRetrivedListener listener){
        apiInterface.getAttendance(email, subject).enqueue(new Callback<AttendanceModel>() {
            @Override
            public void onResponse(Call<AttendanceModel> call, Response<AttendanceModel> response) {
                Bundle b= new Bundle();
                b.putSerializable("attendance", response.body());
                listener.onDataRetrieved(b, GET_ATTENDANCE);
            }

            @Override
            public void onFailure(Call<AttendanceModel> call, Throwable t) {
                Log.e("TAG", "onFailure: "+ t.getMessage());
            }
        });
    }

    public void postTeacherAttend(TeacherAttendModel teacherAttendModel, final OnDataRetrivedListener listener){
        apiInterface.postTeacherAttend(teacherAttendModel).enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                Bundle b= new Bundle();
                b.putSerializable("response", response.body());
                listener.onDataRetrieved(b, POST_TEACHER_ATTEND);
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                Log.e("TAG", "onFailure: "+ t.getMessage());
            }
        });
    }


}
