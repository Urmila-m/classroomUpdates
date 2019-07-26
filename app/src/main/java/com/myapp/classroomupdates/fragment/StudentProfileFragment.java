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

import com.google.gson.Gson;
import com.myapp.classroomupdates.R;
import com.myapp.classroomupdates.activity.AfterLoginActivityStudent;
import com.myapp.classroomupdates.model.StudentModel;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static com.myapp.classroomupdates.Globals.fromJsonToStudent;
import static com.myapp.classroomupdates.Globals.preferences;

public class StudentProfileFragment extends BaseFragment {

    private TextView tvName, tvRoll, tvSemester, tvEmail, tvGroup, tvProgram;
    private CircleImageView ivStudent;
    private FrameLayout frameLayout;
    private AlertDialog.Builder builder;

    public StudentProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.student_profile, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvEmail= view.findViewById(R.id.tv_student_email);
        tvName= view.findViewById(R.id.tv_student_name);
        tvRoll= view.findViewById(R.id.tv_roll);
        tvSemester= view.findViewById(R.id.tv_semester);
        ivStudent= view.findViewById(R.id.iv_student_profile);
        tvGroup= view.findViewById(R.id.tv_group);
        tvProgram= view.findViewById(R.id.tv_program);
        builder= new AlertDialog.Builder(getContext());
        frameLayout= ((AfterLoginActivityStudent)getContext()).getFrameLayout();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        StudentModel student= fromJsonToStudent(preferences.getString("Student", ""));
        String name= student.getIs_class_representative()?student.getName()+"( Class Representative)":student.getName();
        tvName.setText(name);
        tvEmail.setText(student.getEmail());
        tvSemester.setText(student.getBatch());
        tvRoll.setText(student.getRoll_number());
        tvProgram.setText(student.getProgramme_name());
        tvGroup.setText(student.getGroup());

            //TODO to set image from server, use picasso
//        Picasso.get().load("").placeholder(R.drawable.portrait).into(ivStudent);
        ivStudent.setImageDrawable(getActivity().getDrawable(R.drawable.portrait));
        ivStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buildAlertDialog(builder);
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
                ((AfterLoginActivityStudent)getContext()).setFragment(frameLayout, fragment, "0");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if (requestCode==2 && resultCode== RESULT_OK && data!=null){
            ImageDisplayFragment fragment= convertIntentDataToFragment(data);
            ((AfterLoginActivityStudent) getContext()).setFragment(frameLayout, fragment, "0");
        }
    }

}
