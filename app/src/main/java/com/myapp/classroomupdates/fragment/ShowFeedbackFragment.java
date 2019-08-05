package com.myapp.classroomupdates.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myapp.classroomupdates.R;
import com.myapp.classroomupdates.adapter.FeedbackAdapter;
import com.myapp.classroomupdates.model.FeedbackModel;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.myapp.classroomupdates.Globals.apiInterface;
import static com.myapp.classroomupdates.Globals.preferences;
import static com.myapp.classroomupdates.Globals.showSnackbar;

public class ShowFeedbackFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView;
    private FeedbackAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView.LayoutManager layoutManager;
    private List<FeedbackModel> list;
    private Handler handler;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getArguments()!=null){
            list= (List<FeedbackModel>) getArguments().getSerializable("feedbackList");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter= new FeedbackAdapter(getContext(), list);
        layoutManager= new LinearLayoutManager(getContext());
        handler= new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Log.e("TAG", "handleMessage: ");
                super.handleMessage(msg);
                list= (List<FeedbackModel>) msg.getData().getSerializable("feedBackList");
                adapter.notifyDataSetChanged();
            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.recycler_view_container, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView= view.findViewById(R.id.rv_container);
        swipeRefreshLayout= view.findViewById(R.id.srl_feedback);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
//        swipeRefreshLayout.setEnabled(true);
        swipeRefreshLayout.setColorSchemeColors(getContext().getColor(android.R.color.holo_green_dark),
                getContext().getColor(android.R.color.holo_red_dark),
                getContext().getColor(android.R.color.holo_blue_dark),
                getContext().getColor(R.color.yellow)
                );
        swipeRefreshLayout.setOnRefreshListener(this);
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                Log.e("TAG", "onRefresh: " );
//                new Handler().postDelayed(new Runnable() {
//                    @Override public void run() {
//                        // Stop animation (This will be after 3 seconds)
//                        swipeRefreshLayout.setRefreshing(false);
//                    }
//                }, 4000);
//                apiInterface.getAllFeedback("Token "+preferences.getString("token", ""))
//                        .enqueue(new Callback<List<FeedbackModel>>() {
//                            @Override
//                            public void onResponse(Call<List<FeedbackModel>> call, Response<List<FeedbackModel>> response) {
//                                if (response.isSuccessful()){
//                                    Log.e("TAG", "onResponse: successful" );
//                                    Bundle b= new Bundle();
//                                    b.putSerializable("feedbackList", (Serializable) response.body());
//                                    Message msg= new Message();
//                                    msg.setData(b);
//                                    handler.sendMessage(msg);
//                                }
//                                else {
//                                    try {
//                                        Log.e("TAG", "onResponse: "+response.errorBody().string());
//                                    } catch (IOException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<List<FeedbackModel>> call, Throwable t) {
//                                Log.e("TAG", "onFailure: "+t.getMessage());
//                                showSnackbar(recyclerView, "Couldn;t fetch feedback");
//                            }
//                        });
//            }
//        });
    }

    @Override
    public void onRefresh() {
        Log.e("TAG", "onRefresh: " );
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                // Stop animation (This will be after 3 seconds)
                swipeRefreshLayout.setRefreshing(false);
            }
            }, 4000);
    }
}
