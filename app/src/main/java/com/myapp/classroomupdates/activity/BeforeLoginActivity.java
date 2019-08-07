package com.myapp.classroomupdates.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.FrameLayout;

import com.myapp.classroomupdates.fragment.FirstPageFragment;
import com.myapp.classroomupdates.R;

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
                finish();
            }
            else if (preferences.contains("token") && preferences.getString("user_type", "").equals("Teacher")){
                startActivity(new Intent(this, AfterLoginTeacherActivity.class));
                finish();
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
