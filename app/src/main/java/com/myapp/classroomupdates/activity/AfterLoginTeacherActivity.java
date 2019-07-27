package com.myapp.classroomupdates.activity;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.myapp.classroomupdates.R;
import com.myapp.classroomupdates.adapter.StudentHomeViewPagerAdapter;
import com.myapp.classroomupdates.fragment.ChangePasswordFragment;
import com.myapp.classroomupdates.fragment.ScheduleFragment;
import com.myapp.classroomupdates.fragment.TeacherAttendFragment;
import com.myapp.classroomupdates.fragment.TeacherProfileFragment;
import com.myapp.classroomupdates.model.ScheduleModel;
import com.myapp.classroomupdates.model.TeacherModel;
import com.myapp.classroomupdates.utility.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.myapp.classroomupdates.Globals.GET_DAILY_TEACHER_SCHEDULE;
import static com.myapp.classroomupdates.Globals.apiInterface;
import static com.myapp.classroomupdates.Globals.editor;
import static com.myapp.classroomupdates.Globals.fromJsonToTeacher;
import static com.myapp.classroomupdates.Globals.getTodaysDateStringFormat;
import static com.myapp.classroomupdates.Globals.preferences;
import static com.myapp.classroomupdates.Globals.showSnackbar;

public class AfterLoginTeacherActivity extends PreferenceInitializingActivity implements NavigationView.OnNavigationItemSelectedListener{

    private FrameLayout frameLayout;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private StudentHomeViewPagerAdapter adapter;
    private TextView headerEmail;
    private CircleImageView headerImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login_teacher);

        setViews();
        setUserDetails();
        setSupportActionBar(toolbar);
//        setFragment(frameLayout, new TeacherAttendFragment(), "0");

        TeacherAttendFragment fragment= new TeacherAttendFragment();
        Bundle b= new Bundle();
        b.putString("subject", "Software Engineering");
        b.putString("startingTime", "12:00");
        fragment.setArguments(b);

        TeacherAttendFragment fragment2= new TeacherAttendFragment();
        Bundle b2= new Bundle();
        b2.putString("subject", "Artificial Engineering");
        b2.putString("startingTime", "14:00");
        fragment2.setArguments(b2);

        List<TeacherAttendFragment> list= new ArrayList<>();

        list.add(fragment);
        list.add(fragment2);
        StudentHomeViewPagerAdapter adapter= new StudentHomeViewPagerAdapter(getSupportFragmentManager(), this, list);
        viewPager.setVisibility(View.VISIBLE);
        tabLayout.setVisibility(View.VISIBLE);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
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
        int id = item.getItemId();

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
//        item.setChecked(true);
        if (id!= R.id.nav_home){
            tabLayout.setVisibility(View.GONE);
            viewPager.setVisibility(View.GONE);
        }

        if (id == R.id.nav_home) {
//            startActivity(new Intent(this, AfterLoginTeacherActivity.class));
//            finish();
            clearAllFragmentTransactions();
            this.recreate();

        } else if (id == R.id.nav_profile) {
            setFragment(frameLayout, new TeacherProfileFragment(), "0");

        } else if (id == R.id.nav_schedule) {
            if(NetworkUtils.isNetworkConnected(this)) {
                apiInterface.getUserRoutine("Token " + preferences.getString("token", ""), getTodaysDateStringFormat())
                        .enqueue(new Callback<List<ScheduleModel>>() {
                            @Override
                            public void onResponse(Call<List<ScheduleModel>> call, Response<List<ScheduleModel>> response) {
                                if (response.isSuccessful()) {
                                    Log.e("TAG", "onResponse: successful");
                                    sendRoutineResponseToFragment(response, new ScheduleFragment(), frameLayout);
                                } else {
                                    Log.e("TAG", "onResponse:unsuccessful " + response.message());
                                }
                            }

                            @Override
                            public void onFailure(Call<List<ScheduleModel>> call, Throwable t) {
                                Log.e("TAG", "onFailure: " + t.getMessage());
                            }
                        });
            }
            else {
                showSnackbar(navigationView, "Couldn't fetch routine");
            }
        } else if (id == R.id.nav_feedback) {

        } else if (id == R.id.nav_change_password) {
            setFragment(frameLayout, new ChangePasswordFragment(), "0");

        } else if (id == R.id.nav_log_out) {
            editor.remove("token");
            editor.remove("Teacher");
            editor.remove("user_type");
            editor.commit();
            startActivity(new Intent(this, BeforeLoginActivity.class));
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setViews(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        CoordinatorLayout cl= findViewById(R.id.include_app_bar);
        ConstraintLayout col= cl.findViewById(R.id.include_content);
        frameLayout= col.findViewById(R.id.fl_after_login);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        headerImage= navigationView.getHeaderView(0).findViewById(R.id.header_imageView);
        headerEmail= navigationView.getHeaderView(0).findViewById(R.id.header_email);
        tabLayout= frameLayout.findViewById(R.id.tab_layout_schedule);
        viewPager= frameLayout.findViewById(R.id.viewpager_schedule);
    }

//    @Override
//    public void onDataRetrieved(Bundle bundle, String source_id) {
//        if (source_id.equals(GET_DAILY_TEACHER_SCHEDULE)){
//            List<TeacherScheduleModel> list= (List<TeacherScheduleModel>) bundle.getSerializable("DailyTeacherSchedule");
//            List<TeacherAttendFragment> fragmentList= new ArrayList<>();
//            ArrayList<String> subjectList= new ArrayList<>();
//            ArrayList<String> startingTimeList= new ArrayList<>();
//            for (TeacherScheduleModel s: list
//                 ) {
//               subjectList.add(s.getSubject());
//               startingTimeList.add(s.getStartTime());
//            }
//
//            for (int i=0; i<subjectList.size(); i++){
//                TeacherAttendFragment fragment= new TeacherAttendFragment();
//                Bundle b= new Bundle();
//                b.putString("subject", subjectList.get(i));
//                b.putString("startingTime", startingTimeList.get(i));
//                fragment.setArguments(b);
//                fragmentList.add(fragment);
//            }
//            adapter= new StudentHomeViewPagerAdapter(getSupportFragmentManager(), this, fragmentList);
//            viewPager.setVisibility(View.VISIBLE);
//            tabLayout.setVisibility(View.VISIBLE);
//            tabLayout.setupWithViewPager(viewPager);
//
//            viewPager.setAdapter(adapter);
//
//         }
//    }

    private void setUserDetails(){
        headerImage.setImageDrawable(getDrawable(R.drawable.portrait));
//        Picasso.get().load("").placeholder(R.drawable.portrait).into(headerImage);
        TeacherModel teacher= fromJsonToTeacher(preferences.getString("Teacher", ""));
        headerEmail.setText(teacher.getEmail());
    }

    public FrameLayout getFrameLayout() {
        return frameLayout;
    }
}
