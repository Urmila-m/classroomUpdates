package com.myapp.classroomupdates.activity;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.myapp.classroomupdates.R;
import com.myapp.classroomupdates.adapter.ScheduleAdapter;
import com.myapp.classroomupdates.adapter.ViewPagerAdapter;
import com.myapp.classroomupdates.fragment.ChangePasswordFragment;
import com.myapp.classroomupdates.fragment.ScheduleFragment;
import com.myapp.classroomupdates.fragment.StudentHomePageFragment;
import com.myapp.classroomupdates.fragment.StudentProfileFragment;
import com.myapp.classroomupdates.fragment.StudentSignUpFragment;
import com.myapp.classroomupdates.model.StudentScheduleModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AfterLoginActivityStudent extends PreferenceInitializingActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FrameLayout frameLayout;
    private List<StudentScheduleModel> sundayList, mondayList;
    private ArrayList<ScheduleFragment> fragmentList;
    View myView;
    ScheduleFragment scheduleFragment1;
    TextView textView;
    TabLayout tabLayout;
    ViewPager viewPager;
    LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login_student);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sundayList= new ArrayList<>();
        mondayList= new ArrayList<>();

        StudentScheduleModel schedule1= new StudentScheduleModel("10:15", "11:55", "Software Engineering(Theory)", "Lecture 3", "Aman Shakya");
        StudentScheduleModel schedule2= new StudentScheduleModel("11:55", "01:35", "DBMS(Theory)", "Lecture 4", "Ranjita Gurung");
        sundayList.add(schedule1);
        sundayList.add(schedule2);
        sundayList.add(schedule1);
        sundayList.add(schedule2);

        mondayList.add(schedule2);
        mondayList.add(schedule1);
        mondayList.add(schedule1);
        mondayList.add(schedule1);

        scheduleFragment1= new ScheduleFragment();
        Bundle b= new Bundle();
        b.putSerializable("scheduleList", (Serializable) sundayList);
        scheduleFragment1.setArguments(b);

        ScheduleFragment scheduleFragment3= new ScheduleFragment();
        Bundle b3= new Bundle();
        b3.putSerializable("scheduleList", (Serializable) sundayList);
        scheduleFragment3.setArguments(b3);

        ScheduleFragment scheduleFragment2= new ScheduleFragment();
        Bundle b2= new Bundle();
        b2.putSerializable("scheduleList", (Serializable) mondayList);
        scheduleFragment2.setArguments(b2);

        fragmentList= new ArrayList<ScheduleFragment>();

        fragmentList.add(scheduleFragment1);
        fragmentList.add(scheduleFragment2);
        fragmentList.add(scheduleFragment3);

        CoordinatorLayout cl= findViewById(R.id.include_app_bar);
        ConstraintLayout col= cl.findViewById(R.id.include_content);
        frameLayout= col.findViewById(R.id.fl_after_login);
        tabLayout= frameLayout.findViewById(R.id.tab_layout_schedule);
        viewPager= frameLayout.findViewById(R.id.viewpager_schedule);

       setFragment(frameLayout, new StudentHomePageFragment(), "0");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.after_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            tabLayout.setVisibility(View.GONE);
            viewPager.setVisibility(View.GONE);
            setFragment(frameLayout, new StudentHomePageFragment(), "0");


        } else if (id == R.id.nav_profile) {
            tabLayout.setVisibility(View.GONE);
            viewPager.setVisibility(View.GONE);
                setFragment(frameLayout, new StudentProfileFragment(), "0");
                //TODO check student or teacher and then load appropriate fragment

        } else if (id == R.id.nav_schedule) {
            Log.e("TAG", "onNavigationItemSelected: schedule");

            for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                if (fragment != null) {
                    getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                }
            }

            tabLayout.setVisibility(View.VISIBLE);
            viewPager.setVisibility(View.VISIBLE);

            ViewPagerAdapter adapter= new ViewPagerAdapter(getSupportFragmentManager(), fragmentList);
            viewPager.setAdapter(adapter);
            tabLayout.setupWithViewPager(viewPager);

        } else if (id == R.id.nav_change_password) {
            tabLayout.setVisibility(View.GONE);
            viewPager.setVisibility(View.GONE);
            setFragment(frameLayout, new ChangePasswordFragment(), "0");

        } else if (id == R.id.nav_log_out) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
