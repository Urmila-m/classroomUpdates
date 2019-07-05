package com.myapp.classroomupdates.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.myapp.classroomupdates.Globals;
import com.myapp.classroomupdates.interfaces.MultipleEditTextWatcher;
import com.myapp.classroomupdates.interfaces.OnFragmentClickListener;
import com.myapp.classroomupdates.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.myapp.classroomupdates.Globals.isEmpty;

public class LoginFragment extends Fragment implements View.OnClickListener {

    private TextInputLayout tilEmail, tilPassword;
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
        tilEmail= view.findViewById(R.id.til_name_login);
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
        tilEmail.getEditText().addTextChangedListener(new MultipleEditTextWatcher(tilEmail));
        tilPassword.getEditText().addTextChangedListener(new MultipleEditTextWatcher(tilPassword));
        login.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
        noAccountYet.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (listener!=null) {
            if (v == login) {
                if (isValidData()) {
                    String email = tilEmail.getEditText().getText().toString();
                    String password = tilPassword.getEditText().getText().toString();
                    String teacherStudent = teacher.isChecked() ? "teacher" : "student";
                    bundle.putString("email", email);
                    bundle.putString("password", password);
                    bundle.putString("teacherStudent", teacherStudent);
                    listener.onFragmentClicked(bundle, login.getId());//TODO activity ma gayera arkai activity ma jaanxa
                }
                else if(isEmpty(tilEmail.getEditText().getText().toString())){
                    Toast.makeText(getContext(), "email can't be empty!!", Toast.LENGTH_SHORT).show();
                }
                else if(isEmpty(tilPassword.getEditText().getText().toString())){
                    Toast.makeText(getContext(), "Password can't be empty!!", Toast.LENGTH_SHORT).show();
                }

            } else if (v == forgotPassword) {
                listener.onFragmentClicked(new Bundle(), forgotPassword.getId());

            } else {
                listener.onFragmentClicked(new Bundle(), noAccountYet.getId());
            }
        }
    }

    private boolean isValidData(){
        if (!isEmpty(tilPassword.getEditText().getText().toString())
                &&!isEmpty(tilEmail.getEditText().getText().toString())
                && tilEmail.getError()==null
                && tilPassword.getError()== null){
            return true;
        }
        else {
            return false;
        }
    }

}


