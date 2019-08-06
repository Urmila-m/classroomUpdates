package com.myapp.classroomupdates.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.myapp.classroomupdates.R;
import com.myapp.classroomupdates.activity.AfterLoginTeacherActivity;
import com.myapp.classroomupdates.model.TeacherModel;
import com.squareup.picasso.Picasso;

import static com.myapp.classroomupdates.Globals.fromJsonToTeacher;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static com.myapp.classroomupdates.Globals.preferences;

public class TeacherProfileFragment extends BaseFragment {

    private TextView tvName, tvEmail, tvSubjects, tvTime, tvDepartment;
    private CircleImageView iv_teacher;
    private FrameLayout frameLayout;

    public TeacherProfileFragment() {
        // Required empty public constructor
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
        tvSubjects= view.findViewById(R.id.tv_teacher_subjects);
        tvTime= view.findViewById(R.id.tv_teacher_time);
        tvDepartment= view.findViewById(R.id.tv_teacher_department);
        frameLayout= ((AfterLoginTeacherActivity)getContext()).getFrameLayout();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TeacherModel teacher= fromJsonToTeacher(preferences.getString("Teacher", ""));
        String name= teacher.getShort_name().equals("")?teacher.getName():teacher.getName()+" ( "+teacher.getShort_name()+" )";
        String time= teacher.getIs_full_timer()?"Full time Teacher":"Part Time Teacher";
        tvName.setText(name);
        tvEmail.setText(teacher.getEmail());
        tvSubjects.setText(teacher.getSubjects());
        tvTime.setText(time);
        tvDepartment.setText(teacher.getDepartment());
        Picasso.get().load(preferences.getString("image", "http://")).placeholder(R.drawable.portrait).into(iv_teacher);

        iv_teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buildAlertDialog(new AlertDialog.Builder(getContext()));
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1 && resultCode== RESULT_OK && data!=null){
            Uri path= data.getData();
            try {
                ImageDisplayFragment fragment= convertPathToFragment(path);
                ((AfterLoginTeacherActivity)getContext()).setFragment(frameLayout, fragment, "0");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if (requestCode==2 && resultCode== RESULT_OK && data!=null){
            ImageDisplayFragment fragment= convertIntentDataToFragment(data);
            ((AfterLoginTeacherActivity) getContext()).setFragment(frameLayout, fragment, "0");
        }
    }
}
