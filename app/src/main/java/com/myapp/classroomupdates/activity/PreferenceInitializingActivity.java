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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

}
