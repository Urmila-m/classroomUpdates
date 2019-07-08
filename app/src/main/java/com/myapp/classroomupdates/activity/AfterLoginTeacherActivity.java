package com.myapp.classroomupdates.activity;

import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.myapp.classroomupdates.ApiBackgroundTask;
import com.myapp.classroomupdates.R;
import com.myapp.classroomupdates.adapter.StudentHomeViewPagerAdapter;
import com.myapp.classroomupdates.fragment.ChangePasswordFragment;
import com.myapp.classroomupdates.fragment.StudentHomePageFragment;
import com.myapp.classroomupdates.fragment.StudentProfileFragment;
import com.myapp.classroomupdates.fragment.TeacherAttendFragment;
import com.myapp.classroomupdates.fragment.TeacherProfileFragment;
import com.myapp.classroomupdates.interfaces.OnDataRetrivedListener;
import com.myapp.classroomupdates.model.TeacherAttendModel;
import com.myapp.classroomupdates.model.TeacherScheduleModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.Subject;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.myapp.classroomupdates.Globals.GET_DAILY_TEACHER_SCHEDULE;

public class AfterLoginTeacherActivity extends PreferenceInitializingActivity implements NavigationView.OnNavigationItemSelectedListener, OnDataRetrivedListener {

    private FrameLayout frameLayout;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private StudentHomeViewPagerAdapter adapter;
    private ApiBackgroundTask task;
    private TextView headerEmail;
    private CircleImageView headerImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login_teacher);

        task= new ApiBackgroundTask();
        setViews();
        setSupportActionBar(toolbar);
//        setFragment(frameLayout, new TeacherAttendFragment(), "0");

        task.getTeacherSchedule(getTodaysDay(), this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        Picasso.get().load("").placeholder(R.drawable.portrait).into(headerImage);
        headerEmail.setText("123abc@gmail.com");//TODO extract from preference
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
            setFragment(frameLayout, new TeacherAttendFragment(), "0");

        } else if (id == R.id.nav_profile) {
            Fragment fragment= new TeacherProfileFragment();

            //TODO retreive from database and add to bundle
            ArrayList<String> subjectList= new ArrayList<String>();
            subjectList.add("Basic Electronics");
            subjectList.add("Basic Electrical");
            subjectList.add("Artificial Intelligence");
            subjectList.add("Embedded System");

            Bundle bundle= new Bundle();
            bundle.putStringArrayList("subjectList", subjectList);
            fragment.setArguments(bundle);
            setFragment(frameLayout, fragment, "0");

        } else if (id == R.id.nav_schedule) {
            //TODO same as student schedule, just replace teacher with student program+sem

        } else if (id == R.id.nav_feedback) {

        } else if (id == R.id.nav_change_password) {
            setFragment(frameLayout, new ChangePasswordFragment(), "0");

        } else if (id == R.id.nav_log_out) {

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

    @Override
    public void onDataRetrieved(Bundle bundle, String source_id) {
        if (source_id.equals(GET_DAILY_TEACHER_SCHEDULE)){
            List<TeacherScheduleModel> list= (List<TeacherScheduleModel>) bundle.getSerializable("DailyTeacherSchedule");
            List<TeacherAttendFragment> fragmentList= new ArrayList<>();
            ArrayList<String> subjectList= new ArrayList<>();
            ArrayList<String> startingTimeList= new ArrayList<>();
            for (TeacherScheduleModel s: list
                 ) {
               subjectList.add(s.getSubject());
               startingTimeList.add(s.getStartTime());
            }

            for (int i=0; i<subjectList.size(); i++){
                TeacherAttendFragment fragment= new TeacherAttendFragment();
                Bundle b= new Bundle();
                b.putString("subject", subjectList.get(i));
                b.putString("startingTime", startingTimeList.get(i));
                fragment.setArguments(b);
                fragmentList.add(fragment);
            }
            adapter= new StudentHomeViewPagerAdapter(getSupportFragmentManager(), this, fragmentList);
            viewPager.setVisibility(View.VISIBLE);
            tabLayout.setVisibility(View.VISIBLE);
            tabLayout.setupWithViewPager(viewPager);

            viewPager.setAdapter(adapter);

         }
    }
}
