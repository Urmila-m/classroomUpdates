package com.myapp.classroomupdates.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

    public void clearAllFragmentTransactions(){
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (fragment != null) {
                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            }
        }
    }

}
