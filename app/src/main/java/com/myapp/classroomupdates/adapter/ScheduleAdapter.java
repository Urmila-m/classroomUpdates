package com.myapp.classroomupdates.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.myapp.classroomupdates.R;
import com.myapp.classroomupdates.activity.ChangeAttendingDetailsActivity;
import com.myapp.classroomupdates.model.RoutineOfModel;
import com.myapp.classroomupdates.model.ScheduleModel;
import com.myapp.classroomupdates.model.SetArrivalTimeModel;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;
import static com.myapp.classroomupdates.Globals.apiInterface;
import static com.myapp.classroomupdates.Globals.fromJsonToStudent;
import static com.myapp.classroomupdates.Globals.fromJsonToTeacher;
import static com.myapp.classroomupdates.Globals.getTodaysDateStringFormat;
import static com.myapp.classroomupdates.Globals.getTodaysDay;
import static com.myapp.classroomupdates.Globals.preferences;
import static com.myapp.classroomupdates.Globals.showSnackbar;
import static com.myapp.classroomupdates.Globals.showSthWentWrong;

public class ScheduleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<ScheduleModel> list;
    private Context context;

    public ScheduleAdapter(Context context, List<ScheduleModel> list) {
        this.context=context;
        this.list= list;
    }

    @Override
    public int getItemViewType(int position) {
        int i = 0;
        if (list.size()!=0){
            i=1;
        }
        return i;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i==1){
            View view= LayoutInflater.from(context).inflate(R.layout.schedule_adapter_layout, viewGroup, false);
            return new ScheduleViewHolder(view);
        }
        else {
            View view= LayoutInflater.from(context).inflate(R.layout.just_a_text_view_layout, viewGroup, false);
            return new EmptyViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        if (viewHolder instanceof ScheduleViewHolder) {
            ScheduleViewHolder h = (ScheduleViewHolder) viewHolder;
            h.startTime.setText(list.get(i).getCorrected_from_time());
            h.endTime.setText(list.get(i).getCorrected_to_time());
            h.location.setText(list.get(i).getRoom());
            if (preferences.getString("user_type", "").equals("Student")) {
                h.teacherOrBatch.setText(list.get(i).getTeachers());
                h.subject.setText(list.get(i).getSubject());
                if(!isCR()){
                    h.updateDate.setVisibility(View.GONE);
                    h.tvUpdatedDate.setVisibility(View.GONE);
                    h.btnUpdate.setVisibility(View.GONE);
                    h.viewBar.setVisibility(GONE);
                }
                else {
                    h.btnUpdate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(final View v) {
                            final View view= LayoutInflater.from(context).inflate(R.layout.set_arrival_time_layout, null);
                            final TimePicker timePicker= view.findViewById(R.id.tp_set_arrival_time);
                            String[] firstSplit= list.get(i).getCorrected_from_time().split(":", 2);
                            String[] secondSplit= firstSplit[1].split(":", 2);
                            timePicker.setHour(Integer.parseInt(firstSplit[0]));
                            timePicker.setMinute(Integer.parseInt(secondSplit[0]));
                            AlertDialog.Builder builder= new AlertDialog.Builder(context)
                                    .setView(view)
                                    .setTitle("Set arrival time")
                                    .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            int hr= timePicker.getHour();
                                            int min= timePicker.getMinute();
                                            String arrivalTime= String.format("%02d", hr)+ ":"+String.format("%02d", min)+":00";
                                            SetArrivalTimeModel arrivalTimeModel= new SetArrivalTimeModel(list.get(i).getId(), getTodaysDateStringFormat(), arrivalTime);
                                            apiInterface.setArrivalTime("Token "+preferences.getString("token", ""), arrivalTimeModel)
                                                    .enqueue(new Callback<SetArrivalTimeModel>() {
                                                        @Override
                                                        public void onResponse(Call<SetArrivalTimeModel> call, Response<SetArrivalTimeModel> response) {
                                                            if (response.isSuccessful()){
                                                                Toast.makeText(context, "Arrival time saved!", Toast.LENGTH_SHORT).show();
                                                            }
                                                            else {
                                                                try {
                                                                    Log.e("TAG", "onResponse: "+response.errorBody().string());
                                                                } catch (IOException e) {
                                                                    e.printStackTrace();
                                                                }
                                                                showSthWentWrong(context);
                                                            }
                                                        }

                                                        @Override
                                                        public void onFailure(Call<SetArrivalTimeModel> call, Throwable t) {
                                                            showSnackbar(v, "Couldn't save.");
                                                        }
                                                    });
                                        }
                                    });
                            builder.show();
                        }
                    });
                }
            }
            else if (preferences.getString("user_type", "").equals("Teacher")){
                RoutineOfModel routine= list.get(i).getRoutine_of();
                String myName= fromJsonToTeacher(preferences.getString("Teacher", "")).getName();
                String year= routine.getYear();
                String part= routine.getPart();
                String group= routine.getGroup();
                String program= routine.getProgramme().getShort_form();
                String batch= program+" year:"+year+" part:"+part+" group:"+group;
                h.teacherOrBatch.setText(batch);
                if (myName.equals(list.get(i).getTeachers())) {
                    h.subject.setText(list.get(i).getSubject());
                }
                else {
                    h.subject.setText(list.get(i).getSubject()+"  ("+list.get(i).getTeachers()+")");
                }
                h.btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent= new Intent(context, ChangeAttendingDetailsActivity.class);
                        Bundle b=new Bundle();
                        b.putSerializable("schedule", list.get(i));
                        intent.putExtra("schedule", b);
                        context.startActivity(intent);
                    }
                });
            }
        }
        else {
            EmptyViewHolder h= (EmptyViewHolder) viewHolder;
            h.textView.setText("No classes today! Have fun.");
        }
    }

    @Override
    public int getItemCount() {
            if (list.size()==0){
                return 1;
            }
            else {
                return list.size();
            }
    }

    class ScheduleViewHolder extends RecyclerView.ViewHolder {

        private TextView startTime, endTime, subject, teacherOrBatch, location, tvUpdatedDate, updateDate;
        private Button btnUpdate;
        private View viewBar;

        private ScheduleViewHolder(@NonNull View itemView) {
            super(itemView);
            startTime= itemView.findViewById(R.id.tv_starting_time);
            endTime= itemView.findViewById(R.id.tv_ending_time);
            subject= itemView.findViewById(R.id.tv_subject_student_schedule);
            teacherOrBatch= itemView.findViewById(R.id.tv_teacher_student_schedule);
            location= itemView.findViewById(R.id.tv_location_student_schedule);
            updateDate= itemView.findViewById(R.id.last_updated);
            tvUpdatedDate= itemView.findViewById(R.id.tv_last_updated_date);
            viewBar= itemView.findViewById(R.id.view_bar);
            btnUpdate= itemView.findViewById(R.id.btn_update_routine);

        }
    }

    public class EmptyViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;

        EmptyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textView = itemView.findViewById(R.id.tv_just_text_view);
        }
    }

    private boolean isCR(){
        return fromJsonToStudent(preferences.getString("Student", "")).isIs_class_representative();
    }
}
