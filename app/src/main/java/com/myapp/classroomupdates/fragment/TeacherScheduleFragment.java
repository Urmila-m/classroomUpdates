package com.myapp.classroomupdates.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myapp.classroomupdates.R;
import com.myapp.classroomupdates.adapter.ScheduleAdapter;
import com.myapp.classroomupdates.model.StudentScheduleModel;
import com.myapp.classroomupdates.model.TeacherScheduleModel;

import java.util.ArrayList;
import java.util.List;

public class TeacherScheduleFragment extends Fragment {

    RecyclerView recyclerView;
    ScheduleAdapter adapter;
    ArrayList<TeacherScheduleModel> list;
    LinearLayoutManager linearLayoutManager;

    public TeacherScheduleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getArguments()!=null){
            Bundle b= getArguments();
            list= (ArrayList<TeacherScheduleModel>) b.getSerializable("scheduleList");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e("TAG", "onCreateView: ");
        View view= inflater.inflate(R.layout.per_day_schedule, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.e("TAG", "onViewCreated: ");
        super.onViewCreated(view, savedInstanceState);
        recyclerView= view.findViewById(R.id.rv_per_day);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.e("TAG", "onActivityCreated: ");
        super.onActivityCreated(savedInstanceState);
        linearLayoutManager= new LinearLayoutManager(getContext());
        adapter=new ScheduleAdapter(list, getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }
}