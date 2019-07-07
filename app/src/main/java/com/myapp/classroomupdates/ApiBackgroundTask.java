package com.myapp.classroomupdates;

import android.os.Bundle;
import android.util.Log;

import com.myapp.classroomupdates.interfaces.ApiInterface;
import com.myapp.classroomupdates.interfaces.OnDataRetrivedListener;
import com.myapp.classroomupdates.model.StudentHomeModel;
import com.myapp.classroomupdates.model.StudentModel;
import com.myapp.classroomupdates.model.StudentScheduleModel;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.myapp.classroomupdates.Globals.GET_DAILY_STUDENT_SCHEDULE;
import static com.myapp.classroomupdates.Globals.GET_STUDENT;
import static com.myapp.classroomupdates.Globals.GET_STUDENT_HOME;

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
        apiInterface.getDailySchedule(day, "6", "Computer", "B").enqueue(new Callback<List<StudentScheduleModel>>() {//TODO get the values from preference
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

    public void getStudentDetails(String email, String password, final OnDataRetrivedListener listener){
        apiInterface.getStudentDetails(email, password).enqueue(new Callback<StudentModel>() {
            @Override
            public void onResponse(Call<StudentModel> call, Response<StudentModel> response) {
                StudentModel student= response.body();
                Bundle b= new Bundle();
                b.putSerializable("student", (Serializable) student);
                if (listener!=null){
                    listener.onDataRetrieved(b, GET_STUDENT);
                }
            }

            @Override
            public void onFailure(Call<StudentModel> call, Throwable t) {
                Log.e("TAG", "onFailure: "+ t.getMessage());

            }
        });

    }


}
