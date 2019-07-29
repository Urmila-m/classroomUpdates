package com.myapp.classroomupdates.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.myapp.classroomupdates.fragment.ShowFeedbackFragment;
import com.myapp.classroomupdates.model.FeedbackModel;
import com.myapp.classroomupdates.model.ScheduleModel;

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
import static com.myapp.classroomupdates.Globals.preferences;
import static com.myapp.classroomupdates.Globals.showSnackbar;

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
                            setFragment(frameLayout, fragment, "0");
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

    public void sendRoutineResponseToFragment(Response<List<ScheduleModel>> response, Fragment fragment, FrameLayout frameLayout){
        ArrayList<ScheduleModel> list = (ArrayList<ScheduleModel>) response.body();
        Bundle b = new Bundle();
        b.putSerializable("scheduleList", list);
        fragment.setArguments(b);
        setFragment(frameLayout, fragment, "0");
    }

}
