package com.myapp.classroomupdates.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
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
import android.widget.Toast;

import com.myapp.classroomupdates.R;
import com.myapp.classroomupdates.fragment.ChangePasswordFragment;
import com.myapp.classroomupdates.fragment.FeedbackFormFragment;
import com.myapp.classroomupdates.fragment.MarksFragment;
import com.myapp.classroomupdates.fragment.StudentProfileFragment;
import com.myapp.classroomupdates.interfaces.OnDataRetrievedListener;
import com.myapp.classroomupdates.model.CanGiveFeedbackModel;
import com.myapp.classroomupdates.model.LoginResponseModel;
import com.myapp.classroomupdates.model.MarksResponseModel;
import com.myapp.classroomupdates.model.StudentModel;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.myapp.classroomupdates.Globals.apiInterface;
import static com.myapp.classroomupdates.Globals.fromJsonToStudent;
import static com.myapp.classroomupdates.Globals.preferences;
import static com.myapp.classroomupdates.Globals.showSnackbar;
import static com.myapp.classroomupdates.Globals.showSthWentWrong;

public class AfterLoginActivityStudent extends PreferenceInitializingActivity implements NavigationView.OnNavigationItemSelectedListener{

    private FrameLayout frameLayout;
    private DrawerLayout drawer;
    private View feedbackTextView;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private TextView headerEmail, noInternet;
    private CircleImageView headerImage;
    private boolean IS_SEMESTER_END;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("TAG", "onCreate: ");
        setContentView(R.layout.activity_after_login_student);

        findView();
        setUserDetails();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);

        setSchedule(frameLayout, noInternet);
    }

    public CircleImageView getHeaderImage() {
        return headerImage;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.after_login, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        if (id == R.id.action_refresh) {
//            //TODO refresh
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
//        item.setChecked(true);
        if (id != R.id.nav_home){
            noInternet.setVisibility(View.GONE);
        }
        if (feedbackTextView.getParent()!=null){
            ((ViewGroup)feedbackTextView.getParent()).removeView(feedbackTextView);
        }

        if (id == R.id.nav_home) {
            setSchedule(frameLayout, noInternet);

        } else if (id == R.id.nav_profile) {
            setFragment(frameLayout, new StudentProfileFragment(), "0");

        }
        else if (id == R.id.nav_my_feedback){
            sendFeedbackToFragment(frameLayout, headerEmail);

        }
        else if (id == R.id.nav_change_password) {
            setFragment(frameLayout, new ChangePasswordFragment(), "0");

        }
        else if (id == R.id.nav_marks){
            getMyMarks();
        }
        else if (id== R.id.nav_provide_feedback){
            handleFeedback();
        }
        else if (id == R.id.nav_log_out) {
           logout("Student");
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
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        headerImage= navigationView.getHeaderView(0).findViewById(R.id.header_imageView);
        headerEmail= navigationView.getHeaderView(0).findViewById(R.id.header_email);
        noInternet= frameLayout.findViewById(R.id.tv_no_internet);
        feedbackTextView= LayoutInflater.from(this).inflate(R.layout.just_a_text_view_layout, null);
    }

    private void setUserDetails(){
        StudentModel student= fromJsonToStudent(preferences.getString("Student", ""));
        headerEmail.setText(student.getEmail());
        Picasso.get().load(preferences.getString("image", "http://")).placeholder(R.drawable.portrait).into(headerImage);
    }

    private void getTeacherListAndStartFragment(){
        apiInterface.getListOfTeachers("Token "+preferences.getString("token", ""))
                .enqueue(new Callback<List<LoginResponseModel>>() {
                    @Override
                    public void onResponse(Call<List<LoginResponseModel>> call, Response<List<LoginResponseModel>> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()){
                            HashMap<Integer, String> teacherMap= new HashMap<>();
                            for (LoginResponseModel m:response.body()
                            ) {
                                if (m.getTeacher_detail()!=null) {
                                    teacherMap.put(m.getTeacher_detail().getUser(), m.getTeacher_detail().getName());
                                }
                            }
                            Bundle bundle= new Bundle();
                            bundle.putSerializable("hashMap", teacherMap);
                            FeedbackFormFragment fragment= new FeedbackFormFragment();
                            fragment.setArguments(bundle);
                            OnDataRetrievedListener listener= AfterLoginActivityStudent.this;
                            listener.onDataRetrieved(fragment, frameLayout, "getListOfTeachers");
                        }
                        else {
                            try {
                                Log.e("TAG", "onResponse: "+response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<LoginResponseModel>> call, Throwable t) {
                        dialog.dismiss();
                        showSnackbar(headerEmail, "");
                        Log.e("TAG", "onFailure: "+t.getMessage());
                    }
                });
    }

    private void handleFeedback(){
        dialog.show();
        apiInterface.canGiveFeedback("Token "+preferences.getString("token", ""))
                .enqueue(new Callback<CanGiveFeedbackModel>() {
                    @Override
                    public void onResponse(Call<CanGiveFeedbackModel> call, Response<CanGiveFeedbackModel> response) {
                        if (response.isSuccessful()) {
                            IS_SEMESTER_END= response.body().isCan_give_feedback();
                            if (IS_SEMESTER_END) {
                                OnDataRetrievedListener listener= AfterLoginActivityStudent.this;
                                listener.onDataRetrieved(new Fragment(), frameLayout, "canGiveFeedback");
                            }
                            else {
                                dialog.dismiss();
                                clearAllFragmentTransactions();
                                frameLayout.addView(feedbackTextView);//for displaying message that you cant give feedback if its not sem end
                            }
                        }
                        else {
                            dialog.dismiss();
                            try {
                                String s= response.errorBody().string();
                                Log.e("TAG", "onResponse: "+s);
                                Toast.makeText(AfterLoginActivityStudent.this, "Something went wrong :(", Toast.LENGTH_LONG).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<CanGiveFeedbackModel> call, Throwable t) {
                        Log.e("TAG", "onFailure: "+t.getMessage());
                        dialog.dismiss();
                        showSnackbar(headerEmail, "");//should actually check what error, but considering the cause to be no internet connection.
                    }
                });
    }

    private void getMyMarks(){
        dialog.show();
        apiInterface.getMarks("Token "+preferences.getString("token", ""))
                .enqueue(new Callback<List<MarksResponseModel>>() {
                    @Override
                    public void onResponse(Call<List<MarksResponseModel>> call, Response<List<MarksResponseModel>> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()){
                            Bundle bundle= new Bundle();
                            bundle.putSerializable("marksList", (Serializable) response.body());
                            MarksFragment fragment= new MarksFragment();
                            fragment.setArguments(bundle);
                            OnDataRetrievedListener listener= AfterLoginActivityStudent.this;
                            listener.onDataRetrieved(fragment, frameLayout, "getMarks");
                        }
                        else {
                            try {
                                Log.e("TAG", "onResponse: "+response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            showSthWentWrong(AfterLoginActivityStudent.this);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<MarksResponseModel>> call, Throwable t) {
                        dialog.dismiss();
                        Log.e("TAG", "onFailure: "+t.getMessage() );
                        showSnackbar(headerEmail, "");
                    }
                });
    }

    public FrameLayout getFrameLayout() {
        return frameLayout;
    }

    @Override
    public void onDataRetrieved(Fragment fragment, FrameLayout frameLayout, String source) {
        super.onDataRetrieved(fragment, frameLayout, source);
        if (source.equals("canGiveFeedback")){
            getTeacherListAndStartFragment();

        }
        else if (source.equals("getListOfTeachers")||source.equals("getMarks")) {
            setFragment(frameLayout, fragment, "0");
        }
    }


}
