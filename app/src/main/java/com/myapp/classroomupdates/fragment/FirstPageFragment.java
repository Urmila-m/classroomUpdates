package com.myapp.classroomupdates.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.myapp.classroomupdates.activity.BeforeLoginActivity;
import com.myapp.classroomupdates.interfaces.OnFragmentClickListener;
import com.myapp.classroomupdates.R;
import com.myapp.classroomupdates.utility.NetworkUtils;

public class FirstPageFragment extends Fragment implements View.OnClickListener {

    private Button btnLogin, btnSignUp;
    private FrameLayout frameLayout;

    public FirstPageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.first_page_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnLogin= view.findViewById(R.id.btn_login_first);
        btnSignUp= view.findViewById(R.id.btn_sign_up_first);
        frameLayout= ((BeforeLoginActivity)getContext()).getFrameLayout();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btnSignUp.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnLogin) {
            ((BeforeLoginActivity) getContext()).setFragment(frameLayout, new LoginFragment(), "0");
        } else if (v == btnSignUp) {
            ((BeforeLoginActivity) getContext()).setFragment(frameLayout, new SignUpFragment(), "0");
        }
    }
}
