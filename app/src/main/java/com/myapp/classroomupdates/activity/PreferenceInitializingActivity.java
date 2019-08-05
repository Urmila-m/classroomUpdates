package com.myapp.classroomupdates.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.myapp.classroomupdates.fragment.ScheduleFragment;
import com.myapp.classroomupdates.fragment.ShowFeedbackFragment;
import com.myapp.classroomupdates.interfaces.OnDataRetrievedListener;
import com.myapp.classroomupdates.model.FeedbackModel;
import com.myapp.classroomupdates.model.ScheduleModel;
import com.myapp.classroomupdates.utility.NetworkUtils;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.myapp.classroomupdates.Globals.apiInterface;
import static com.myapp.classroomupdates.Globals.editor;
import static com.myapp.classroomupdates.Globals.getTodaysDateStringFormat;
import static com.myapp.classroomupdates.Globals.preferences;
import static com.myapp.classroomupdates.Globals.showSnackbar;

public class PreferenceInitializingActivity extends AppCompatActivity implements OnDataRetrievedListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setFragment(FrameLayout frameLayout, Fragment fragment, String backstack){
        Log.e("TAG", "setFragment: "+fragment.toString() );
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

    public void sendFeedbackToFragment(final FrameLayout frameLayout, final View view){
        apiInterface.getAllFeedback("Token "+preferences.getString("token", ""))
                .enqueue(new Callback<List<FeedbackModel>>() {
                    @Override
                    public void onResponse(Call<List<FeedbackModel>> call, Response<List<FeedbackModel>> response) {
                        if (response.isSuccessful()){
                            ShowFeedbackFragment fragment= new ShowFeedbackFragment();
                            Bundle bundle= new Bundle();
                            bundle.putSerializable("feedbackList", (Serializable) response.body());
                            fragment.setArguments(bundle);
                            OnDataRetrievedListener listener= PreferenceInitializingActivity.this;
                            listener.onDataRetrieved(fragment, frameLayout, "getAllFeedback");
//                            setFragment(frameLayout, fragment, "0");//TODO cause problem
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
                    public void onFailure(Call<List<FeedbackModel>> call, Throwable t) {
                        Log.e("TAG", "onFailure: "+t.getMessage());
                        showSnackbar(view, "Couldn;t fetch feedback");
                    }
                });
    }

    public void setSchedule(final FrameLayout frameLayout, final TextView noInternet){
        if (NetworkUtils.isNetworkConnected(this)) {
            apiInterface.getUserRoutine("Token " + preferences.getString("token", ""), getTodaysDateStringFormat())
                    .enqueue(new Callback<List<ScheduleModel>>() {
                        @Override
                        public void onResponse(Call<List<ScheduleModel>> call, Response<List<ScheduleModel>> response) {
                            noInternet.setVisibility(View.GONE);
                            if (response.isSuccessful()) {
                                ArrayList<ScheduleModel> list = (ArrayList<ScheduleModel>) response.body();
                                Bundle b = new Bundle();
                                ScheduleFragment fragment= new ScheduleFragment();
                                b.putSerializable("scheduleList", list);
                                b.putString("adapter", "ScheduleAdapter");
                                fragment.setArguments(b);
                                OnDataRetrievedListener listener= PreferenceInitializingActivity.this;  //listener required because commiting a transaction inside an async thread
                                listener.onDataRetrieved(fragment, frameLayout, "getUserRoutine"); //throws exception as the thread has no knowledge of the activity state
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
            noInternet.setVisibility(View.VISIBLE);
            showSnackbar(frameLayout, "Unable to fetch routine!!");
        }
    }

    public void logout(String userType){
        editor.remove("token");
        editor.remove(userType);
        editor.remove("user_type");
        editor.remove("id");
        editor.remove("image");
        editor.commit();
        finish();
    }

    @Override
    public void onDataRetrieved(Fragment fragment, FrameLayout frameLayout, String source) {
        if (source.equals("getAllFeedback")) {
            setFragment(frameLayout, fragment, "0");//TODO cause problem
        }
        else if (source.equals("getUserRoutine")){
            setFragment(frameLayout, fragment, "0");
        }
    }
}
