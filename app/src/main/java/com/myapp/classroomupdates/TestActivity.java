package com.myapp.classroomupdates;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;

import com.myapp.classroomupdates.adapter.ScheduleAdapter;
import com.myapp.classroomupdates.interfaces.OnDataRetrievedListener;
import com.myapp.classroomupdates.model.ScheduleModel;

import java.io.Serializable;
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
        handler= new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                list= (List<ScheduleModel>) msg.getData().getSerializable("schedulelist");
                adapter = new ScheduleAdapter(TestActivity.this, list);
                recyclerView.setLayoutManager(manager);
                recyclerView.setAdapter(adapter);
            }
        };

        apiInterface.getUserRoutine("Token "+preferences.getString("token",""), getTodaysDateStringFormat())
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
                    }

                    @Override
                    public void onFailure(Call<List<ScheduleModel>> call, Throwable t) {

                    }
                });


    }

    @Override
    public void onRefresh() {
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
