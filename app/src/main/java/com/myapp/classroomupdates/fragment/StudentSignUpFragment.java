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
import android.widget.Button;
import android.widget.TextView;

import com.myapp.classroomupdates.R;
import com.myapp.classroomupdates.interfaces.OnFragmentClickListener;

public class StudentSignUpFragment extends Fragment {

    private OnFragmentClickListener listener;
    private TextInputLayout tilBatch, tilProgram, tilRoll;
    private Button btnSubmit;

    public StudentSignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.student_sign_up, container, false);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentClickListener){
            listener= (OnFragmentClickListener) context;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tilBatch= view.findViewById(R.id.til_batch_student_signup);
        tilProgram= view.findViewById(R.id.til_program_student_signup);
        tilRoll= view.findViewById(R.id.til_roll_student_signup);
        btnSubmit= view.findViewById(R.id.btn_student_sign_up);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String batch= tilBatch.getEditText().getText().toString();
                String program=tilProgram.getEditText().getText().toString();
                String roll= tilRoll.getEditText().getText().toString();
                Bundle b=new Bundle();
                b.putString("batch", batch);
                b.putString("roll", roll);
                b.putString("program", program);
                listener.onFragmentClicked(b, btnSubmit.getId());
            }
        });

    }
}
