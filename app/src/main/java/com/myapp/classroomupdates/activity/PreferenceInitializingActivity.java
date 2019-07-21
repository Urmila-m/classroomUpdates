package com.myapp.classroomupdates.activity;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;

import com.myapp.classroomupdates.Globals;
import com.myapp.classroomupdates.R;
import com.myapp.classroomupdates.model.StudentModel;
import com.myapp.classroomupdates.model.TeacherModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

public class PreferenceInitializingActivity extends AppCompatActivity {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences= getSharedPreferences("loggedInUser", MODE_PRIVATE);
        editor= preferences.edit();
    }

    public void setFragment(FrameLayout frameLayout, Fragment fragment, String backstack){
        Log.e("TAG", "setFragment: " );
        FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();
        transaction.replace(frameLayout.getId(), fragment);
        if (backstack=="1"){
            transaction.addToBackStack("");
        }
        transaction.commit();
    }

    public void showSnackbar(String msg){
        Snackbar snackbar= Snackbar.make(Globals.view, "No internet connection! "+msg, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(getColor(R.color.grey));
        snackbar.setActionTextColor(getColor(R.color.yellow));
        snackbar.show();
    }

    public String getTodaysDay(){
        Calendar calendar= Calendar.getInstance();
        Date currentDate= calendar.getTime();
        SimpleDateFormat dateFormat= new SimpleDateFormat("EEEE");
        String day= dateFormat.format(currentDate);
        return day;
    }

    public void saveUserToPreference(String token, StudentModel student){
        editor.putString("user_type", "Student");
        editor.putString("name", student.getName());
        editor.putString("password", student.getPassword());
        editor.putString("batch", student.getBatch());
        editor.putString("email", student.getEmail());
        editor.putString("roll", student.getRoll());
        editor.putString("group", student.getGroup());
        editor.putString("program", student.getProgram());
        editor.putString("token", token);
        editor.commit();

    }

    public void saveUserToPreference(String token, TeacherModel teacher){
        editor.putString("user_type", "Teacher");
        editor.putString("name", teacher.getName());
        editor.putString("email", teacher.getEmail());
        editor.putString("password", teacher.getPassword());
        editor.putString("phone", teacher.getPhone());
        editor.putString("subjects", teacher.getSubjects());
        editor.putString("token", token);
        editor.commit();
    }

}
