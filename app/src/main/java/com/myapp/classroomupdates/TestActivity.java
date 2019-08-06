package com.myapp.classroomupdates;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.FrameLayout;

import com.myapp.classroomupdates.adapter.ScheduleAdapter;
import com.myapp.classroomupdates.interfaces.OnDataRetrievedListener;
import com.myapp.classroomupdates.model.ScheduleModel;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.myapp.classroomupdates.Globals.apiInterface;
import static com.myapp.classroomupdates.Globals.getTodaysDateStringFormat;
import static com.myapp.classroomupdates.Globals.preferences;

public class TestActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    ScheduleAdapter adapter;
    LinearLayoutManager manager;
    Handler handler;
    List<ScheduleModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        swipeRefreshLayout= findViewById(R.id.srl);
        recyclerView= findViewById(R.id.rv);
        swipeRefreshLayout.setOnRefreshListener(this);
        manager= new LinearLayoutManager(this);
        adapter= new ScheduleAdapter(this, new ArrayList<ScheduleModel>());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
        handler= new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Log.e("TAG", "handleMessage: ");
                super.handleMessage(msg);
                list= (List<ScheduleModel>) msg.getData().getSerializable("schedulelist");
                adapter = new ScheduleAdapter(TestActivity.this, list);
                recyclerView.setLayoutManager(manager);
                recyclerView.setAdapter(adapter);
            }
        };

        apiInterface.getUserRoutine("Token 8908aa77b7e0f7a58be41c7c59072e39d8a43e22", getTodaysDateStringFormat())
                .enqueue(new Callback<List<ScheduleModel>>() {
                    @Override
                    public void onResponse(Call<List<ScheduleModel>> call, Response<List<ScheduleModel>> response) {
                        if (response.isSuccessful()) {
                            List<ScheduleModel> list = response.body();
                            Bundle bundle= new Bundle();
                            bundle.putSerializable("scheduleList", (Serializable) list);
                            Message message= new Message();
                            message.setData(bundle);
                            handler.sendMessage(message);
                        }
                        else {
                            try {
                                Log.e("TAG", "onResponse: "+response.errorBody().string() );
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ScheduleModel>> call, Throwable t) {
                        Log.e("TAG", "onFailure: ");
                    }
                });


    }

    @Override
    public void onRefresh() {
        Log.e("TAG", "onRefresh: ");
        apiInterface.getUserRoutine("Token "+preferences.getString("token",""), getTodaysDateStringFormat())
                .enqueue(new Callback<List<ScheduleModel>>() {
                    @Override
                    public void onResponse(Call<List<ScheduleModel>> call, Response<List<ScheduleModel>> response) {
                        if (response.isSuccessful()) {
                            List<ScheduleModel> list = response.body();
                            adapter.clearList();
                            adapter.addAll(list);
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ScheduleModel>> call, Throwable t) {

                    }
                });
    }

}
