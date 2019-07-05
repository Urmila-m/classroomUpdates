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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.myapp.classroomupdates.R;
import com.myapp.classroomupdates.interfaces.MultipleEditTextWatcher;
import com.myapp.classroomupdates.interfaces.OnFragmentClickListener;

import static com.myapp.classroomupdates.Globals.isEmpty;

public class StudentSignUpFragment extends Fragment {

    private OnFragmentClickListener listener;
    private TextInputLayout tilBatch, tilRoll;
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
        program= view.findViewById(R.id.sp_program);
        tilRoll= view.findViewById(R.id.til_roll_student_signup);
        btnSubmit= view.findViewById(R.id.btn_student_sign_up);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tilRoll.getEditText().addTextChangedListener(new MultipleEditTextWatcher(tilRoll));
        tilBatch.getEditText().addTextChangedListener(new MultipleEditTextWatcher(tilBatch));

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tilBatch.getError()==null && tilRoll.getError()==null) {
                    if (!isEmpty(tilBatch.getEditText().getText().toString())&&!isEmpty(tilRoll.getEditText().getText().toString())) {
                        String batch = tilBatch.getEditText().getText().toString();
                        int position = program.getSelectedItemPosition();
                        String programOptions[] = {"Computer", "Electrical", "Electronics", "Civil", "Mechanical"};
                        String program = programOptions[position];
                        String roll = tilRoll.getEditText().getText().toString();
                        Bundle b = new Bundle();
                        b.putString("batch", batch);
                        b.putString("roll", roll);
                        b.putString("program", program);
                        listener.onFragmentClicked(b, btnSubmit.getId());
                    }
                    else {
                        Toast.makeText(getContext(), "Roll or batch can't be empty!!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }
}
