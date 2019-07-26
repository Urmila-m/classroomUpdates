package com.myapp.classroomupdates.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
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
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.myapp.classroomupdates.R;
import com.myapp.classroomupdates.adapter.StudentHomeViewPagerAdapter;
import com.myapp.classroomupdates.fragment.ChangePasswordFragment;
import com.myapp.classroomupdates.fragment.FeedbackFormFragment;
import com.myapp.classroomupdates.fragment.ScheduleFragment;
import com.myapp.classroomupdates.fragment.StudentHomePageFragment;
import com.myapp.classroomupdates.fragment.StudentProfileFragment;
import com.myapp.classroomupdates.model.ScheduleModel;
import com.myapp.classroomupdates.model.StudentModel;
import com.myapp.classroomupdates.utility.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.myapp.classroomupdates.Globals.IS_SEMESTER_END;
import static com.myapp.classroomupdates.Globals.apiInterface;
import static com.myapp.classroomupdates.Globals.editor;
import static com.myapp.classroomupdates.Globals.fromJsonToStudent;
import static com.myapp.classroomupdates.Globals.getTodaysDateStringFormat;
import static com.myapp.classroomupdates.Globals.preferences;
import static com.myapp.classroomupdates.Globals.showSnackbar;

public class AfterLoginActivityStudent extends PreferenceInitializingActivity implements NavigationView.OnNavigationItemSelectedListener{

    private FrameLayout frameLayout;
    private ArrayList<ScheduleFragment> fragmentList;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private DrawerLayout drawer;
    private View feedbackTextView;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private TextView headerEmail;
    private CircleImageView headerImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login_student);

        findView();
        setUserDetails();
//        headerEmail.setText(student.getEmail());
        setSupportActionBar(toolbar);

        List<StudentHomePageFragment> list= new ArrayList<>();
        StudentHomePageFragment fragment1= new StudentHomePageFragment();
        StudentHomePageFragment fragment2= new StudentHomePageFragment();
        StudentHomePageFragment fragment3= new StudentHomePageFragment();
        list.add(fragment1);
        list.add(fragment2);
        list.add(fragment3);

        StudentHomeViewPagerAdapter adapter= new StudentHomeViewPagerAdapter(getSupportFragmentManager(), list, this);
        viewPager.setVisibility(View.VISIBLE);
        tabLayout.setVisibility(View.VISIBLE);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        //All for the schedule. Hardcoded values
//        sundayList= new ArrayList<>();
//        mondayList= new ArrayList<>();
//
//        StudentScheduleModel schedule1= new StudentScheduleModel("10:15", "11:55", "Software Engineering(Theory)", "Lecture 3", "Aman Shakya");
//        StudentScheduleModel schedule2= new StudentScheduleModel("11:55", "01:35", "DBMS(Theory)", "Lecture 4", "Ranjita Gurung");
//        sundayList.add(schedule1);
//        sundayList.add(schedule2);
//        sundayList.add(schedule1);
//        sundayList.add(schedule2);
//
//        mondayList.add(schedule2);
//        mondayList.add(schedule1);
//        mondayList.add(schedule1);
//        mondayList.add(schedule1);
//
//        ScheduleFragment scheduleFragment1= new ScheduleFragment();
//        Bundle b= new Bundle();
//        b.putSerializable("scheduleList", (Serializable) sundayList);
//        scheduleFragment1.setArguments(b);
//
//        ScheduleFragment scheduleFragment3= new ScheduleFragment();
//        Bundle b3= new Bundle();
//        b3.putSerializable("scheduleList", (Serializable) sundayList);
//        scheduleFragment3.setArguments(b3);
//
//        ScheduleFragment scheduleFragment2= new ScheduleFragment();
//        Bundle b2= new Bundle();
//        b2.putSerializable("scheduleList", (Serializable) mondayList);
//        scheduleFragment2.setArguments(b2);
//
//        fragmentList= new ArrayList<ScheduleFragment>();
//
//        fragmentList.add(scheduleFragment1);
//        fragmentList.add(scheduleFragment2);
//        fragmentList.add(scheduleFragment3);

    }

    @Override
    public void onBackPressed() {
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

        if (feedbackTextView.getParent()!=null){
            ((ViewGroup)feedbackTextView.getParent()).removeView(feedbackTextView);
        }

        if (id!= R.id.nav_home){
            tabLayout.setVisibility(View.GONE);
            viewPager.setVisibility(View.GONE);
        }

        if (id == R.id.nav_home) {
            finish();
            startActivity(new Intent(this, AfterLoginActivityStudent.class));

        } else if (id == R.id.nav_profile) {
            Log.e("TAG", "onNavigationItemSelected: profile");
                setFragment(frameLayout, new StudentProfileFragment(), "0");

        } else if (id == R.id.nav_schedule) {
            //its a frame layout and to avoid the previous loaded fragment be displayed in background
            clearAllFragmentTransactions();
            if (NetworkUtils.isNetworkConnected(this)) {
                apiInterface.getUserRoutine("Token " + preferences.getString("token", ""), getTodaysDateStringFormat())
                        .enqueue(new Callback<List<ScheduleModel>>() {
                            @Override
                            public void onResponse(Call<List<ScheduleModel>> call, Response<List<ScheduleModel>> response) {
                                if (response.isSuccessful()) {
                                    sendRoutineResponseToFragment(response, new ScheduleFragment(), frameLayout);
                                } else {
                                    Log.e("TAG", "onResponse: " + response.message());
                                }
                            }

                            @Override
                            public void onFailure(Call<List<ScheduleModel>> call, Throwable t) {
                                Log.e("TAG", "onFailure: " + t.getMessage());
                            }
                        });
            }
            else {
                showSnackbar(headerEmail, "Unable to fetch routine!!");
            }

        } else if (id == R.id.nav_change_password) {
            setFragment(frameLayout, new ChangePasswordFragment(), "0");

        }
        else if (id== R.id.nav_provide_feedback){
            if (IS_SEMESTER_END) {
                setFragment(frameLayout, new FeedbackFormFragment(), "0");
            }
            else {
                clearAllFragmentTransactions();

                //for displaying message that you cant give feedback if its not sem end
                frameLayout.addView(feedbackTextView);
            }
        }
        else if (id == R.id.nav_log_out) {
            editor.remove("token");
            editor.remove("Student");
            editor.remove("user_type");
            editor.commit();
            startActivity(new Intent(this, BeforeLoginActivity.class));
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void findView(){
        toolbar = findViewById(R.id.toolbar);
        CoordinatorLayout cl= findViewById(R.id.include_app_bar);
        ConstraintLayout col= cl.findViewById(R.id.include_content);
        frameLayout= col.findViewById(R.id.fl_after_login);
        tabLayout= frameLayout.findViewById(R.id.tab_layout_schedule);
        viewPager= frameLayout.findViewById(R.id.viewpager_schedule);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        headerImage= navigationView.getHeaderView(0).findViewById(R.id.header_imageView);
        headerEmail= navigationView.getHeaderView(0).findViewById(R.id.header_email);
        feedbackTextView= LayoutInflater.from(this).inflate(R.layout.just_a_text_view_layout, null);
    }

//    @Override
//    public void onDataRetrieved(Bundle bundle, String source_id) {
//        if (source_id.equals(GET_STUDENT_HOME)){
//            List<StudentHomeModel> list= (List<StudentHomeModel>) bundle.getSerializable("StudentHomeList");
//            List<StudentHomePageFragment> fragmentList= new ArrayList<>();
//
//            for (StudentHomeModel s: list
//                 ) {
//                StudentHomePageFragment fragment= new StudentHomePageFragment();
//                Bundle b= new Bundle();
//                b.putSerializable("StudentHomeModel", (Serializable) s);
//                fragment.setArguments(b);
//                fragmentList.add(fragment);
//            }
//            StudentHomeViewPagerAdapter adapter= new StudentHomeViewPagerAdapter(getSupportFragmentManager(), fragmentList, this);
//            viewPager.setVisibility(View.VISIBLE);
//            tabLayout.setVisibility(View.VISIBLE);
//            viewPager.setAdapter(adapter);
//            tabLayout.setupWithViewPager(viewPager);
//        }
//
//        else if(source_id.equals(GET_DAILY_STUDENT_SCHEDULE)){
//
//        }
//
//    }

    private void setUserDetails(){
        StudentModel student= fromJsonToStudent(preferences.getString("Student", ""));
        headerEmail.setText(student.getEmail());
        headerImage.setImageResource(R.drawable.portrait);
//        Picasso.get().load("jkdsjfk").placeholder(R.drawable.portrait).into(headerImage);
    }

    public FrameLayout getFrameLayout() {
        return frameLayout;
    }
}
