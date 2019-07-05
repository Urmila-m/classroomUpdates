package com.myapp.classroomupdates.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.myapp.classroomupdates.interfaces.MultipleEditTextWatcher;
import com.myapp.classroomupdates.interfaces.OnFragmentClickListener;
import com.myapp.classroomupdates.R;

import static com.myapp.classroomupdates.Globals.isEmpty;

public class ForgotPasswordFragment extends Fragment {

    private TextInputLayout tilEmail;
    private TextInputEditText etEmail;
    private Button resetPassword;
    private OnFragmentClickListener listener;
    private Bundle bundle;

    public ForgotPasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentClickListener){
            listener= (OnFragmentClickListener) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.forgot_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tilEmail= view.findViewById(R.id.til_email_forgot_pass);
        etEmail= view.findViewById(R.id.et_email_forgot_pass);
        resetPassword= view.findViewById(R.id.btn_reset_password);

        bundle=new Bundle();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tilEmail.getEditText().addTextChangedListener(new MultipleEditTextWatcher(tilEmail));
        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    if (tilEmail.getError()==null) {
                        if (!isEmpty(tilEmail.getEditText().getText().toString())) {
                            String email = etEmail.getText().toString();
                            bundle.putString("email", email);
                            listener.onFragmentClicked(bundle, resetPassword.getId());
                        }
                        else {
                            Toast.makeText(getContext(), "Email cant be empty!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }
}