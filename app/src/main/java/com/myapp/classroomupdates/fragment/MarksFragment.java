package com.myapp.classroomupdates.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myapp.classroomupdates.R;
import com.myapp.classroomupdates.adapter.MarksAdapter;
import com.myapp.classroomupdates.model.MarksResponseModel;

import java.util.List;

public class MarksFragment extends BaseFragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MarksAdapter adapter;
    private List<MarksResponseModel> list;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getArguments()!=null){
            list= (List<MarksResponseModel>) getArguments().getSerializable("marksList");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.recycler_view_container, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView= view.findViewById(R.id.rv_container);
        layoutManager= new LinearLayoutManager(getContext());
        adapter= new MarksAdapter(list, getContext());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
