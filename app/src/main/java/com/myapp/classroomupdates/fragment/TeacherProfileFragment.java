package com.myapp.classroomupdates.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.myapp.classroomupdates.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TeacherProfileFragment extends Fragment {

    private ListView listView;
    private TextView tvName, tvEmail;
    private CircleImageView iv_teacher;
    private Bundle bundle;
    private List<String> subjectList;
    private Adapter adapter;

    public TeacherProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        bundle= getArguments();
        if (bundle!=null){
            subjectList= bundle.getStringArrayList("subjectList");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.teacher_profile, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvName= view.findViewById(R.id.tv_teacher_name);
        tvEmail= view.findViewById(R.id.tv_teacher_email);
        iv_teacher= view.findViewById(R.id.iv_teacher_profile);
        listView= view.findViewById(R.id.lv_teacher_subjects);
        adapter= new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, subjectList);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (bundle!=null){
            tvEmail.setText(bundle.getString("email"));
            tvName.setText(bundle.getString("name"));
            listView.setAdapter((ArrayAdapter) adapter);

            iv_teacher.setImageResource(R.drawable.portrait);
//            Picasso.get().load("").placeholder(R.drawable.portrait).into(iv_teacher);
        }
        iv_teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO let user upload photo
            }
        });
    }
}
