package com.myapp.classroomupdates.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.FrameLayout;

import com.myapp.classroomupdates.fragment.FirstPageFragment;
import com.myapp.classroomupdates.fragment.ForgotPasswordFragment;
import com.myapp.classroomupdates.fragment.LoginFragment;
import com.myapp.classroomupdates.fragment.StudentSignUpFragment;
import com.myapp.classroomupdates.interfaces.OnFragmentClickListener;
import com.myapp.classroomupdates.R;
import com.myapp.classroomupdates.fragment.SignUpFragment;

import static com.myapp.classroomupdates.Globals.firstInstall;

public class BeforeLoginActivity extends PreferenceInitializingActivity implements OnFragmentClickListener {

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

        setFragment(frameLayout, new FirstPageFragment(),"0");
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
            //TODO bundle bata aako name, password database ma check garne
            if(bundle.getString("teacherStudent").equals("student")){
                startActivity(new Intent(BeforeLoginActivity.this, AfterLoginActivityStudent.class));
            }
            else {
                startActivity(new Intent(BeforeLoginActivity.this, AfterLoginTeacherActivity.class));
            }
        }
        else if (source_id== R.id.tv_already_have_account|| source_id==R.id.btn_reset_password|| source_id==R.id.btn_login_first){
            setFragment(frameLayout, new LoginFragment(), "0");
        }

        else if (source_id==R.id.btn_sign_up){
            if (bundle.getString("teacherStudent").equals("student")){
                setFragment(frameLayout, new StudentSignUpFragment(), "0");
            }
            else {
                startActivity(new Intent(BeforeLoginActivity.this, AfterLoginTeacherActivity.class));
            }
        }
        else if (source_id==R.id.btn_student_sign_up){
            startActivity(new Intent(BeforeLoginActivity.this, AfterLoginActivityStudent.class));
        }
        else{

        }
    }

    private boolean checkFirstInstall(){
        boolean first= preferences.getBoolean(firstInstall, true);
        return first;
    }
}
