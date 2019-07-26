package com.myapp.classroomupdates.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.myapp.classroomupdates.R;
import com.myapp.classroomupdates.activity.AfterLoginActivityStudent;
import com.myapp.classroomupdates.activity.BeforeLoginActivity;
import com.myapp.classroomupdates.interfaces.MultipleEditTextWatcher;
import com.myapp.classroomupdates.interfaces.OnFragmentClickListener;
import com.myapp.classroomupdates.utility.NetworkUtils;

import static com.myapp.classroomupdates.Globals.getStringFromTIL;
import static com.myapp.classroomupdates.Globals.isEmpty;
import static com.myapp.classroomupdates.Globals.showSnackbar;

public class StudentSignUpFragment extends Fragment {

    private TextInputLayout tilBatch, tilRoll, tilGroup;
    private Spinner program;
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tilBatch= view.findViewById(R.id.til_batch_student_signup);
        tilGroup= view.findViewById(R.id.til_group_student_signup);
        program= view.findViewById(R.id.sp_program);
        tilRoll= view.findViewById(R.id.til_roll_student_signup);
        btnSubmit= view.findViewById(R.id.btn_student_sign_up);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tilRoll.getEditText().addTextChangedListener(new MultipleEditTextWatcher(tilRoll));
        tilBatch.getEditText().addTextChangedListener(new MultipleEditTextWatcher(tilBatch));
        tilGroup.getEditText().addTextChangedListener(new MultipleEditTextWatcher(tilGroup));

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (StudentSignUpFragment.this.isValidData()) {
                        String batch = tilBatch.getEditText().getText().toString();
                        int position = program.getSelectedItemPosition();
                        String programOptions[] = {"Computer", "Electrical", "Electronics", "Civil", "Mechanical"};
                        String program = programOptions[position];
                        String roll = tilRoll.getEditText().getText().toString();
                        String group= getStringFromTIL(tilGroup);
                        if (NetworkUtils.isNetworkConnected(getContext())) {
                            //TODO upload to server
                            startActivity(new Intent(getContext(), AfterLoginActivityStudent.class));
                        }
                        else {
                            showSnackbar(v, "Registration failed!");
                        }
                    }
                    else {
                        Toast.makeText(getContext(), "Make sure that there are no errors and Roll or batch can't be empty!!", Toast.LENGTH_LONG).show();
                    }
                }
        });

    }

    private boolean isValidData(){
        if (tilBatch.getError()== null && tilRoll.getError()== null && tilGroup.getError()== null &&
                !isEmpty(tilBatch.getEditText().getText().toString())&&
                !isEmpty(tilRoll.getEditText().getText().toString())&&
                !isEmpty(getStringFromTIL(tilGroup))){
            return true;
        }
        else {
            return false;
        }
    }
}
