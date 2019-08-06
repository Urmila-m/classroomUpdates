package com.myapp.classroomupdates.fragment;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import com.myapp.classroomupdates.Globals;
import com.myapp.classroomupdates.R;
import com.myapp.classroomupdates.adapter.ScheduleAdapter;
import com.myapp.classroomupdates.adapter.UpdatedScheduleAdapter;
import com.myapp.classroomupdates.model.ScheduleModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.myapp.classroomupdates.Globals.apiInterface;
import static com.myapp.classroomupdates.Globals.getTodaysDay;
import static com.myapp.classroomupdates.Globals.preferences;

public class ScheduleFragment extends Fragment {

    RecyclerView recyclerView;
    TextView tvDay;
    RecyclerView.Adapter adapter;
    ArrayList<ScheduleModel> list;
    LinearLayoutManager linearLayoutManager;
    Bundle bundle;

    public ScheduleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getArguments()!=null){
            Log.e("TAG", "onAttach: "+getArguments().getString("adapter") );
            bundle= getArguments();
            if (bundle.getString("adapter").equals("ScheduleAdapter")) {
                list = (ArrayList<ScheduleModel>) bundle.getSerializable("scheduleList");
            }
            else if (bundle.getString("adapter").equals("UpdatedScheduleAdapter")){
                list = (ArrayList<ScheduleModel>) bundle.getSerializable("updatedRoutineList");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.per_day_schedule, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvDay= view.findViewById(R.id.tv_recycler_view_day);
        recyclerView= view.findViewById(R.id.rv_per_day);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tvDay.setText(getTodaysDay());
        linearLayoutManager= new LinearLayoutManager(getContext());
        if (bundle.getString("adapter").equals("ScheduleAdapter")) {
            adapter = new ScheduleAdapter(getContext(), list);
        }
        else if (bundle.getString("adapter").equals("UpdatedScheduleAdapter")) {
            Log.e("TAG", "onActivityCreated: updated schedule" );
            adapter= new UpdatedScheduleAdapter(getContext(), list);
        }
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }
}
