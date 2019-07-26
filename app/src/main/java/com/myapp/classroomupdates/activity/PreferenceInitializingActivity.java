package com.myapp.classroomupdates.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setFragment(FrameLayout frameLayout, Fragment fragment, String backstack){
        Log.e("TAG", "setFragment: " );
        FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();
        transaction.replace(frameLayout.getId(), fragment);
        if (backstack.equals("1")){
            transaction.addToBackStack("");
        }
        transaction.commit();
    }

    public String getTodaysDay(){
        Calendar calendar= Calendar.getInstance();
        Date currentDate= calendar.getTime();
        SimpleDateFormat dateFormat= new SimpleDateFormat("EEEE");
        String day= dateFormat.format(currentDate);
        return day;
    }

//    public void saveUserToPreference(String token, TeacherModel teacher){
//        editor.putString("user_type", "Teacher");
//        editor.putString("name", teacher.getName());
//        editor.putString("email", teacher.getEmail());
//        editor.putString("password", teacher.getPassword());
//        editor.putString("phone", teacher.getPhone());
//        editor.putString("subjects", teacher.getSubjects());
//        editor.putString("token", token);
//        editor.commit();
//    }

    public void clearAllFragmentTransactions(){
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (fragment != null) {
                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            }
        }
    }

}
