package com.myapp.classroomupdates.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myapp.classroomupdates.R;
import com.myapp.classroomupdates.model.RoutineOfModel;
import com.myapp.classroomupdates.model.ScheduleModel;

import java.util.List;

import static com.myapp.classroomupdates.Globals.fromJsonToTeacher;
import static com.myapp.classroomupdates.Globals.preferences;

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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof ScheduleViewHolder) {
            ScheduleViewHolder h = (ScheduleViewHolder) viewHolder;
            h.startTime.setText(list.get(i).getFrom_time());
            h.endTime.setText(list.get(i).getTo_time());
            h.location.setText(list.get(i).getRoom());
            if (preferences.getString("user_type", "").equals("Student")) {
                h.teacherOrBatch.setText(list.get(i).getTeachers());
                h.subject.setText(list.get(i).getSubject());
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

        private TextView startTime, endTime, subject, teacherOrBatch, location;

        private ScheduleViewHolder(@NonNull View itemView) {
            super(itemView);
//            Log.e("TAG", "StudentScheduleViewHolder: " );
            startTime= itemView.findViewById(R.id.tv_starting_time);
            endTime= itemView.findViewById(R.id.tv_ending_time);
            subject= itemView.findViewById(R.id.tv_subject_student_schedule);
            teacherOrBatch= itemView.findViewById(R.id.tv_teacher_student_schedule);
            location= itemView.findViewById(R.id.tv_location_student_schedule);
        }
    }

    public class EmptyViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;

        EmptyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textView = itemView.findViewById(R.id.tv_just_text_view);
        }
    }
}
