package com.myapp.classroomupdates;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.FrameLayout;

import com.myapp.classroomupdates.adapter.ViewPagerAdapter;
import com.myapp.classroomupdates.fragment.ChangePasswordFragment;

public class MainActivity extends AppCompatActivity {

    private FrameLayout frameLayout;
    ViewPager viewPager;
    ViewPagerAdapter adapter;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_login);

//        frameLayout= findViewById(R.id.fl_before_login);
//        viewPager= findViewById(R.id.viewPager_test);
//        tabLayout= findViewById(R.id.test_tab);
//
//        adapter= new ViewPagerAdapter(getSupportFragmentManager());
//        viewPager.setAdapter(adapter);
//        tabLayout.setupWithViewPager(viewPager);

    }

    public void setFragment(Fragment fragment, String backstack){
        FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();
        transaction.replace(frameLayout.getId(), fragment);
        if (backstack=="1"){
            transaction.addToBackStack("");
        }
        transaction.commit();
    }

}
