package com.myapp.classroomupdates.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.myapp.classroomupdates.ApiBackgroundTask;
import com.myapp.classroomupdates.fragment.FirstPageFragment;
import com.myapp.classroomupdates.fragment.ForgotPasswordFragment;
import com.myapp.classroomupdates.fragment.LoginFragment;
import com.myapp.classroomupdates.fragment.StudentSignUpFragment;
import com.myapp.classroomupdates.interfaces.OnDataRetrivedListener;
import com.myapp.classroomupdates.interfaces.OnFragmentClickListener;
import com.myapp.classroomupdates.R;
import com.myapp.classroomupdates.fragment.SignUpFragment;
import com.myapp.classroomupdates.model.TeacherModel;
import com.myapp.classroomupdates.utility.NetworkUtils;

import static com.myapp.classroomupdates.Globals.GET_TEACHER;
import static com.myapp.classroomupdates.Globals.firstInstall;

public class BeforeLoginActivity extends PreferenceInitializingActivity implements OnFragmentClickListener, OnDataRetrivedListener {

    private FrameLayout frameLayout;
    private ApiBackgroundTask task;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_login);
        frameLayout= findViewById(R.id.fl_before_login);


        if (checkFirstInstall()){
            startActivity(new Intent(BeforeLoginActivity.this, IntroSlideActivity.class));
            finish();
        }
        else {
            task= new ApiBackgroundTask();
            setFragment(frameLayout, new FirstPageFragment(), "0");
        }
    }

    @Override
    public void onFragmentClicked(Bundle bundle, int source_id) {
        if (source_id == R.id.tv_forgot_password){
            Fragment fragment= new ForgotPasswordFragment();
            setFragment(frameLayout, fragment, "0");

        }
        else if (source_id== R.id.tv_no_account_yet||source_id==R.id.btn_sign_up_first){
            Fragment fragment= new SignUpFragment();
            setFragment(frameLayout, fragment, "0");

        }
        else if (source_id== R.id.btn_login){
//            if (NetworkUtils.isNetworkConnected(this)) {

                //TODO bundle bata aako name, password database ma check garne
                if (bundle.getString("teacherStudent").equals("student")) {
                    startActivity(new Intent(BeforeLoginActivity.this, AfterLoginActivityStudent.class));
                } else {
                    String email=  bundle.getString("email");
                    String password= bundle.getString("password");

//                    task.getTeacherDetails(email, password, this);
//                    task.getTeacherDetailsBody();
                    startActivity(new Intent(BeforeLoginActivity.this, AfterLoginTeacherActivity.class));

                }
//            }
//            else {
//               showSnackbar("Couldn't log in");
//            }

        }
        else if (source_id== R.id.tv_already_have_account|| source_id==R.id.btn_reset_password|| source_id==R.id.btn_login_first){
           if (source_id== R.id.btn_reset_password){
               if (NetworkUtils.isNetworkConnected(this)){
                   //TODO set new password to server
               }
               else {
                    showSnackbar("password reset failed");
               }
           }
           setFragment(frameLayout, new LoginFragment(), "0");

        }
        else if (source_id==R.id.btn_sign_up){
            if (bundle.getString("teacherStudent").equals("student")){
                setFragment(frameLayout, new StudentSignUpFragment(), "0");
            }
            else {
                //TODO database ma teacher register garne
//                if (NetworkUtils.isNetworkConnected(this)) {
                    startActivity(new Intent(BeforeLoginActivity.this, AfterLoginTeacherActivity.class));
//                }
//                else {
//                    showSnackbar("Account registration failed!!");
//                }
            }

        }
        else if (source_id==R.id.btn_student_sign_up){
            //TODO database ma student register garne
//            if (NetworkUtils.isNetworkConnected(this)) {
                startActivity(new Intent(BeforeLoginActivity.this, AfterLoginActivityStudent.class));
//            }
//            else {
//                showSnackbar("Account registration failed!!");
//            }
        }
    }

    private boolean checkFirstInstall(){
        boolean first= preferences.getBoolean(firstInstall, true);
        return first;
    }

    @Override
    public void onDataRetrieved(Bundle bundle, String source_id) {
        if (source_id.equals(GET_TEACHER)){
            TeacherModel teacher= (TeacherModel) bundle.getSerializable("teacher");
            if (teacher!= null){
                saveUserToPreference(bundle.getString("token"), teacher);
                startActivity(new Intent(BeforeLoginActivity.this, AfterLoginTeacherActivity.class));
            }
            else {
                Toast.makeText(this, "fjeakjh", Toast.LENGTH_LONG).show();
            }
        }
    }
}
