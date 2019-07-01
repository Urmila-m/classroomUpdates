package com.myapp.classroomupdates.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.myapp.classroomupdates.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class StudentHomePageFragment extends Fragment {

    private CircleImageView imageView;
    private TextView tvTeacher, tvSubject, tvLocation, tvDate, message, tvMessage, time, tvTime;
    private RadioButton rbAttend, rbNotAttend;

    public StudentHomePageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.student_home_page, container, false);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //TODO collect data from Activity using getArguments and Bundle
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView= view.findViewById(R.id.profile_image);
        tvTeacher=  view.findViewById(R.id.tv_student_home_teacher);
        tvSubject= view.findViewById(R.id.tv_student_home_subject);
        tvLocation= view.findViewById(R.id.tv_student_home_location);
        tvMessage= view.findViewById(R.id.tv_message);
        tvTime= view.findViewById(R.id.tv_time);
        time= view.findViewById(R.id.time);
        message= view.findViewById(R.id.message_student_home);
        rbAttend= view.findViewById(R.id.rb_attend_student_home);
        tvDate= view.findViewById(R.id.tv_date);
        rbNotAttend= view.findViewById(R.id.rb_not_attend_student_home);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //TODO set all the values
//        rbAttend.setChecked(true);
        rbNotAttend.setChecked(true);
        if (rbAttend.isChecked()){
            message.setVisibility(View.GONE);
            tvMessage.setVisibility(View.GONE);
        }
        else if (rbNotAttend.isChecked()){
            time.setVisibility(View.GONE);
            tvTime.setVisibility(View.GONE);
        }

    }
}
