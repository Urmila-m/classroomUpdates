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
import static com.myapp.classroomupdates.Globals.preferences;

public class BeforeLoginActivity extends PreferenceInitializingActivity{

    private FrameLayout frameLayout;

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
            if (preferences.contains("token") && preferences.getString("user_type", "").equals("Student")){
                startActivity(new Intent(this, AfterLoginActivityStudent.class));
            }
            else if (preferences.contains("token") && preferences.getString("user_type", "").equals("Teacher")){
                startActivity(new Intent(this, AfterLoginTeacherActivity.class));

            }
            else {
                setFragment(frameLayout, new FirstPageFragment(), "0");
            }
        }
    }

    public FrameLayout getFrameLayout() {
        return frameLayout;
    }

    private boolean checkFirstInstall(){
        boolean first= preferences.getBoolean(firstInstall, true);
        return first;
    }
}
