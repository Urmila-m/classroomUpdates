package com.myapp.classroomupdates.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myapp.classroomupdates.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class StudentProfileFragment extends Fragment {

    private TextView tvName, tvRoll, tvSemester, tvEmail;
    private CircleImageView ivStudent;
    private Bundle bundle;

    public StudentProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.student_profile, container, false);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        bundle= getArguments();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvEmail= view.findViewById(R.id.tv_student_email);
        tvName= view.findViewById(R.id.tv_student_name);
        tvRoll= view.findViewById(R.id.tv_roll);
        tvSemester= view.findViewById(R.id.tv_semester);
        ivStudent= view.findViewById(R.id.iv_student_profile);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (bundle!=null){
            tvName.setText(bundle.getString("name"));
            tvEmail.setText(bundle.getString("email"));
            tvSemester.setText(bundle.getString("batch"));//TODO calculate sem from batch and display
            tvRoll.setText(bundle.getString("roll"));

            //TODO to set image from server, use picasso
            Picasso.get().load("").placeholder(R.drawable.portrait).into(ivStudent);
        }

        ivStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO let user upload photo
            }
        });
    }
}
