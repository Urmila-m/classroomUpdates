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

import com.myapp.classroomupdates.interfaces.OnFragmentClickListener;
import com.myapp.classroomupdates.R;

public class LoginFragment extends Fragment implements View.OnClickListener{

    private TextInputLayout tilName, tilPassword;
    private RadioButton teacher, student;
    private Button login;
    private OnFragmentClickListener listener;
    private TextView forgotPassword, noAccountYet;
    private Bundle bundle;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.login_layout, container, false);
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
        tilName= view.findViewById(R.id.til_name_login);
        tilPassword= view.findViewById(R.id.til_password_login);
        login= view.findViewById(R.id.btn_login);
        teacher= view.findViewById(R.id.rb_teacher_login);
        student= view.findViewById(R.id.rb_student_login);
        forgotPassword= view.findViewById(R.id.tv_forgot_password);
        noAccountYet= view.findViewById(R.id.tv_no_account_yet);

        bundle= new Bundle();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        login.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
        noAccountYet.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (listener!=null) {
            if (v == login) {
                String name = tilName.getEditText().getText().toString();
                String password = tilPassword.getEditText().getText().toString();
                String teacherStudent = teacher.isChecked() ? "teacher" : "student";
                bundle.putString("name", name);
                bundle.putString("password", password);
                bundle.putString("teacherStudent", teacherStudent);
                listener.onFragmentClicked(bundle, login.getId());//TODO activity ma gayera arkai activity ma jaanxa

            } else if (v == forgotPassword) {
                listener.onFragmentClicked(new Bundle(), forgotPassword.getId());

            } else {
                listener.onFragmentClicked(new Bundle(), noAccountYet.getId());
            }
        }
    }
}
