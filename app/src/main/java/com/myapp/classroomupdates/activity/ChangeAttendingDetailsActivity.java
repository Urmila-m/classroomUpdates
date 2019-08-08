package com.myapp.classroomupdates.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.myapp.classroomupdates.R;
import com.myapp.classroomupdates.model.AttendClassRequestModel;
import com.myapp.classroomupdates.model.ClassResponseModel;
import com.myapp.classroomupdates.model.ScheduleModel;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;
import static com.myapp.classroomupdates.Globals.apiInterface;
import static com.myapp.classroomupdates.Globals.getStringFromTIL;
import static com.myapp.classroomupdates.Globals.getTodaysDateStringFormat;
import static com.myapp.classroomupdates.Globals.isEmpty;
import static com.myapp.classroomupdates.Globals.preferences;
import static com.myapp.classroomupdates.Globals.showSnackbar;
import static com.myapp.classroomupdates.Globals.showSthWentWrong;

public class ChangeAttendingDetailsActivity extends PreferenceInitializingActivity {
    private TextView message;
    private TextView setStart;
    private TextView setEnd;
    private RadioButton rbAttend;
    private TextInputLayout tilMessage;
    private TimePicker startTimePicker, endTimePicker;
    private ScheduleModel schedule;
    private AttendClassRequestModel requestModel;
    private String date;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_attend);

        if (getIntent().getExtras()!=null){
            date= getIntent().getBundleExtra("schedule").getString("scheduleDate");
            schedule= (ScheduleModel) getIntent().getBundleExtra("schedule").getSerializable("schedule");
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView startTime = findViewById(R.id.tv_expected_start_time);
        TextView endTime = findViewById(R.id.tv_expected_end_time);
        setEnd= findViewById(R.id.start_time);
        setStart= findViewById(R.id.end_time);
        startTimePicker= findViewById(R.id.tp_start_teacher);
        endTimePicker= findViewById(R.id.tp_end_teacher);
        message= findViewById(R.id.message_teacher);
        TextView tvSubject = findViewById(R.id.tv_subject);
        Button btnSubmit = findViewById(R.id.btn_submit_teacher_attend);
        rbAttend= findViewById(R.id.rb_attend_teacher);
        tilMessage= findViewById(R.id.til_message_teacher);

        startTime.setText(schedule.getFrom_time());
        endTime.setText(schedule.getTo_time());
        tvSubject.setText(schedule.getSubject());
        String[] startFirstSplit= schedule.getFrom_time().split(":", 2);
        String[] startSecondSplit= startFirstSplit[1].split(":", 2);

        String[] endFirstSplit= schedule.getTo_time().split(":", 2);
        String[] endSecondSplit= startFirstSplit[1].split(":", 2);

        startTimePicker.setHour(Integer.parseInt(startFirstSplit[0]));
        startTimePicker.setMinute(Integer.parseInt(startSecondSplit[0]));
        endTimePicker.setHour(Integer.parseInt(endFirstSplit[0]));
        endTimePicker.setMinute(Integer.parseInt(endSecondSplit[0]));

        rbAttend.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    tilMessage.setVisibility(View.GONE);
                    message.setVisibility(GONE);
                    setStart.setVisibility(View.VISIBLE);
                    setEnd.setVisibility(View.VISIBLE);
                    startTimePicker.setVisibility(View.VISIBLE);
                    endTimePicker.setVisibility(View.VISIBLE);
                }
                else {
                    tilMessage.setVisibility(View.VISIBLE);
                    message.setVisibility(View.VISIBLE);
                    setEnd.setVisibility(View.GONE);
                    setStart.setVisibility(View.GONE);
                    startTimePicker.setVisibility(View.GONE);
                    endTimePicker.setVisibility(View.GONE);
                }
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Bundle b= new Bundle();
                String attendOrNotAttend= rbAttend.isChecked()?"attend":"notAttend";
                if (attendOrNotAttend.equals("attend")){
                    int startHr= startTimePicker.getHour();
                    int startMin= startTimePicker.getMinute();
                    String startTime= String.format("%02d", startHr)+ ":"+String.format("%02d", startMin)+":00";

                    int endHr= endTimePicker.getHour();
                    int endMin= endTimePicker.getMinute();
                    String endTime= String.format("%02d", endHr)+":"+ String.format("%02d", endMin)+":00";
                    Log.e("TAG", "onClick: starttime:"+startTime+"endTime:"+endTime);
                    requestModel= new AttendClassRequestModel(schedule.getId(), date,  true, startTime, endTime, "", false);
                }

                else {
                    String message= isEmpty(getStringFromTIL(tilMessage))?"Can't attend today.":getStringFromTIL(tilMessage);
                    Log.e("TAG", "onClick: "+message);
                    requestModel= new AttendClassRequestModel(schedule.getId(), date,  false, schedule.getFrom_time(), schedule.getTo_time(), message, false);

                }
                dialog.show();
                apiInterface.sendAttendDetails("Token "+preferences.getString("token", ""), requestModel)
                        .enqueue(new Callback<ClassResponseModel>() {
                            @Override
                            public void onResponse(Call<ClassResponseModel> call, Response<ClassResponseModel> response) {
                                dialog.dismiss();
                                if (response.isSuccessful()){
                                    Toast.makeText(ChangeAttendingDetailsActivity.this, "Routine updated!!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(ChangeAttendingDetailsActivity.this, AfterLoginTeacherActivity.class));
                                    finish();
                                }
                                else {
                                    showSthWentWrong(ChangeAttendingDetailsActivity.this);
                                    try {
                                        Log.e("TAG", "onResponse: "+response.errorBody().string());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<ClassResponseModel> call, Throwable t) {
                                dialog.dismiss();
                                Log.e("TAG", "onFailure: "+t.getMessage());
                                showSnackbar(v, "Couldn't update routine.");
                            }
                        });
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, AfterLoginTeacherActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
