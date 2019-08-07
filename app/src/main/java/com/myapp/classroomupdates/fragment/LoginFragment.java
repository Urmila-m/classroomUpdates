package com.myapp.classroomupdates.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.myapp.classroomupdates.activity.AfterLoginActivityStudent;
import com.myapp.classroomupdates.activity.AfterLoginTeacherActivity;
import com.myapp.classroomupdates.activity.BeforeLoginActivity;
import com.myapp.classroomupdates.activity.PreferenceInitializingActivity;
import com.myapp.classroomupdates.interfaces.MultipleEditTextWatcher;
import com.myapp.classroomupdates.R;
import com.myapp.classroomupdates.model.LoginResponseModel;
import com.myapp.classroomupdates.utility.NetworkUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.myapp.classroomupdates.Globals.isEmpty;
import static com.myapp.classroomupdates.Globals.apiInterface;
import static com.myapp.classroomupdates.Globals.saveUserToPreference;
import static com.myapp.classroomupdates.Globals.showSnackbar;

public class LoginFragment extends Fragment implements View.OnClickListener {

    private TextInputLayout tilEmail, tilPassword;
    private Button login;
    private TextView forgotPassword, noAccountYet;
    private FrameLayout frameLayout;

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tilEmail= view.findViewById(R.id.til_name_login);
        tilPassword= view.findViewById(R.id.til_password_login);
        login= view.findViewById(R.id.btn_login);
        forgotPassword= view.findViewById(R.id.tv_forgot_password);
        noAccountYet= view.findViewById(R.id.tv_no_account_yet);
        frameLayout= ((BeforeLoginActivity)getContext()).getFrameLayout();
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
            if (v == login) {
                if (isValidData()) {
                    if (NetworkUtils.isNetworkConnected(getContext())) {
                        Log.e("TAG", "onClick: valid data");
                        String email = tilEmail.getEditText().getText().toString();
                        String password = tilPassword.getEditText().getText().toString();
                        ((PreferenceInitializingActivity)getActivity()).dialog.show();
                        apiInterface.getUserDetails(email, password).enqueue(new Callback<LoginResponseModel>() {
                            @Override
                            public void onResponse(Call<LoginResponseModel> call, Response<LoginResponseModel> response) {
                                if (response.isSuccessful()) {
                                    ((PreferenceInitializingActivity)getActivity()).dialog.dismiss();
                                    if (response.body().getUser_type().equals("Student")){
                                        saveUserToPreference(response.body().getStudent_detail(), response.body().getToken(), response.body().getId(), response.body().getImage());
                                        startActivity(new Intent(getContext(), AfterLoginActivityStudent.class));
                                    }
                                    else if (response.body().getUser_type().equals("Teacher")){
                                        Log.e("TAG", "onResponse: teacher");
                                        saveUserToPreference(response.body().getTeacher_detail(), response.body().getToken(), response.body().getId(), response.body().getImage());
                                        startActivity(new Intent(getContext(), AfterLoginTeacherActivity.class));
                                    }
                                    ((BeforeLoginActivity)getContext()).finish();
                                }
                                else {
                                    ((PreferenceInitializingActivity)getActivity()).dialog.dismiss();
                                    Toast.makeText(getContext(), "Incorrect email or Password!!", Toast.LENGTH_LONG).show();
                                }
                            }
                            @Override
                            public void onFailure(Call<LoginResponseModel> call, Throwable t) {
                                Log.e("TAG", "onFailure: "+t.getMessage());
                                ((PreferenceInitializingActivity)getActivity()).dialog.dismiss();
                            }
                        });
                    }
                    else {
                        showSnackbar(v, "Couldn't log in");
                    }
                }
                else if(isEmpty(tilEmail.getEditText().getText().toString())){
                    Toast.makeText(getContext(), "email can't be empty!!", Toast.LENGTH_SHORT).show();
                }
                else if(isEmpty(tilPassword.getEditText().getText().toString())){
                    Toast.makeText(getContext(), "Password can't be empty!!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getContext(), "Make sure there is no error!!", Toast.LENGTH_SHORT).show();
                }

            } else if (v == forgotPassword) {
                Log.e("TAG", "onClick: forgot password" );
                ((BeforeLoginActivity)getContext()).setFragment(frameLayout, new ForgotPasswordFragment(), "0");

            } else {
                Log.e("TAG", "onClick: No account yet" );
                ((BeforeLoginActivity)getContext()).setFragment(frameLayout, new SignUpFragment(), "0");
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


