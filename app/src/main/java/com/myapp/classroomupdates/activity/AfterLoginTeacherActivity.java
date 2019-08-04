package com.myapp.classroomupdates.activity;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
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
import com.myapp.classroomupdates.fragment.ChangePasswordFragment;
import com.myapp.classroomupdates.fragment.ScheduleFragment;
import com.myapp.classroomupdates.fragment.SendNoticeFragment;
import com.myapp.classroomupdates.fragment.TeacherProfileFragment;
import com.myapp.classroomupdates.model.ProgrammeModel;
import com.myapp.classroomupdates.model.ScheduleModel;
import com.myapp.classroomupdates.model.TeacherModel;
import com.myapp.classroomupdates.utility.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.myapp.classroomupdates.Globals.apiInterface;
import static com.myapp.classroomupdates.Globals.editor;
import static com.myapp.classroomupdates.Globals.fromJsonToTeacher;
import static com.myapp.classroomupdates.Globals.getTodaysDateStringFormat;
import static com.myapp.classroomupdates.Globals.preferences;
import static com.myapp.classroomupdates.Globals.showSnackbar;
import static com.myapp.classroomupdates.Globals.showSthWentWrong;

public class AfterLoginTeacherActivity extends PreferenceInitializingActivity implements NavigationView.OnNavigationItemSelectedListener{

    private FrameLayout frameLayout;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TextView headerEmail, noInternet;
    private CircleImageView headerImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login_teacher);

        setViews();
        setUserDetails();
        setSupportActionBar(toolbar);

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
            noInternet.setVisibility(View.VISIBLE);
            showSnackbar(navigationView, "Couldn't fetch routine");
        }

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
//            tabLayout.setVisibility(View.GONE);
//            viewPager.setVisibility(View.GONE);
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
            sendFeedbackToFragment(frameLayout, headerEmail);

        } else if(id== R.id.nav_send_notice){
            getProgramsToNoticeFragment();

        }else if (id == R.id.nav_change_password) {
            setFragment(frameLayout, new ChangePasswordFragment(), "0");

        } else if (id == R.id.nav_log_out) {
            editor.remove("token");
            editor.remove("Teacher");
            editor.remove("user_type");
            editor.remove("id");
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
        noInternet= frameLayout.findViewById(R.id.tv_no_internet);

//        tabLayout= frameLayout.findViewById(R.id.tab_layout_schedule);
//        viewPager= frameLayout.findViewById(R.id.viewpager_schedule);
    }

    private void setUserDetails(){
        headerImage.setImageDrawable(getDrawable(R.drawable.portrait));
//        Picasso.get().load("").placeholder(R.drawable.portrait).into(headerImage);
        TeacherModel teacher= fromJsonToTeacher(preferences.getString("Teacher", ""));
        headerEmail.setText(teacher.getEmail());
    }

    public FrameLayout getFrameLayout() {
        return frameLayout;
    }

    private void getProgramsToNoticeFragment(){
        apiInterface.getAllPrograms("Token "+preferences.getString("token", ""))
                .enqueue(new Callback<List<ProgrammeModel>>() {
                    @Override
                    public void onResponse(Call<List<ProgrammeModel>> call, Response<List<ProgrammeModel>> response) {
                        if (response.isSuccessful()){
                            HashMap<Integer, String> hashMap= new HashMap<>();
                            for (ProgrammeModel m:response.body()
                                 ) {
                                hashMap.put(m.getId(), m.getName());
                            }
                            Bundle b= new Bundle();
                            b.putSerializable("programList", hashMap);
                            SendNoticeFragment fragment= new SendNoticeFragment();
                            fragment.setArguments(b);
                            setFragment(frameLayout, fragment, "0");
                        }
                        else {
                            try {
                                JSONObject jsonObject= new JSONObject(response.errorBody().string());
                                JSONArray array= jsonObject.getJSONArray("review");
                                String message="";
                                for (int i=0; i<array.length(); i++){
                                    message+= (array.getString(i)+"\n");
                                }
                                Log.e("TAG", "onResponse: "+message);
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            showSthWentWrong(AfterLoginTeacherActivity.this);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ProgrammeModel>> call, Throwable t) {
                        Log.e("TAG", "onFailure: "+t.getMessage());
                        showSnackbar(headerEmail, "");
                    }
                });
    }
}
