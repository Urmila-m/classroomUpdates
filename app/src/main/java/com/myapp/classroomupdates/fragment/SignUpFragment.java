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
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.myapp.classroomupdates.interfaces.MultipleEditTextWatcher;
import com.myapp.classroomupdates.interfaces.OnFragmentClickListener;
import com.myapp.classroomupdates.R;

import static com.myapp.classroomupdates.Globals.getStringFromTIL;
import static com.myapp.classroomupdates.Globals.isEmpty;

public class SignUpFragment extends Fragment implements View.OnClickListener {

    private TextInputLayout tilEmail, tilName, tilPassword, tilConfirmPassword;
    private RadioButton rbTeacher, rbStudent;
    private Button signUp;
    private TextView tvAlreadyHaveAcc;
    private OnFragmentClickListener listener;
    private Bundle bundle;

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.sign_up_layout, container, false);
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
        tilEmail= view.findViewById(R.id.til_email_sign_up);
        tilName= view.findViewById(R.id.til_name_sign_up);
        tilPassword= view.findViewById(R.id.til_password_sign_up);
        tilConfirmPassword= view.findViewById(R.id.til_confirm_pass_sign_up);
        rbTeacher= view.findViewById(R.id.rb_teacher_sign_up);
        rbStudent= view.findViewById(R.id.rb_student_sign_up);
        tvAlreadyHaveAcc= view.findViewById(R.id.tv_already_have_account);
        signUp= view.findViewById(R.id.btn_sign_up);

        bundle= new Bundle();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tilEmail.getEditText().addTextChangedListener(new MultipleEditTextWatcher(tilEmail));
        tilName.getEditText().addTextChangedListener(new MultipleEditTextWatcher(tilName));
        tilPassword.getEditText().addTextChangedListener(new MultipleEditTextWatcher(tilPassword));
        tilConfirmPassword.getEditText().addTextChangedListener(new MultipleEditTextWatcher(tilConfirmPassword));
        signUp.setOnClickListener(this);
        tvAlreadyHaveAcc.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v==signUp){
            if (tilName.getError()== null &&
                    tilEmail.getError()== null
                    && tilPassword.getError()== null
                    && tilConfirmPassword.getError()== null) {
                if (!isEmpty(getStringFromTIL(tilName))&&
                        !isEmpty(getStringFromTIL(tilConfirmPassword))&&
                        !isEmpty(getStringFromTIL(tilPassword)) &&
                        !isEmpty(getStringFromTIL(tilEmail))){

                    if (!getStringFromTIL(tilPassword).equals(getStringFromTIL(tilConfirmPassword))) {
                        Toast.makeText(getContext(), "passwords dont match!!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        String email = tilEmail.getEditText().getText().toString();
                        String name = tilName.getEditText().getText().toString();
                        String password = tilPassword.getEditText().getText().toString();
                        String confirmPassword = tilConfirmPassword.getEditText().toString();
                        String teacherStudent = rbTeacher.isChecked() ? "teacher" : "student";
                        bundle.putString("email", email);
                        bundle.putString("name", name);
                        bundle.putString("password", password);
                        bundle.putString("teacherStudent", teacherStudent);
                        listener.onFragmentClicked(bundle, signUp.getId());
                    }
                }
                else {
                    Toast.makeText(getContext(), "Fields can't be empty", Toast.LENGTH_LONG).show();
                }

            }
        }
        else {
            listener.onFragmentClicked(new Bundle(), tvAlreadyHaveAcc.getId());
        }
    }
}