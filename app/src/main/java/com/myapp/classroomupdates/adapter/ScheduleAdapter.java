package com.myapp.classroomupdates.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myapp.classroomupdates.R;
import com.myapp.classroomupdates.activity.AfterLoginActivityStudent;
import com.myapp.classroomupdates.activity.AfterLoginTeacherActivity;
import com.myapp.classroomupdates.model.ScheduleModel;

import java.util.List;

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
        if (context instanceof AfterLoginActivityStudent){
            if (list.size()!=0) {
                i=1;
            }
            else 
                i=0;
        }
        else if (context instanceof AfterLoginTeacherActivity){
            if (list.size()!=0) {
                i=3;
            }
            else 
                i=0;
        }
        return i;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        if (i==1){
            View view= LayoutInflater.from(context).inflate(R.layout.student_schedule_adapter, viewGroup, false);
            return new StudentScheduleViewHolder(view);
        }
        else if (i==3){
            View view= LayoutInflater.from(context).inflate(R.layout.student_schedule_adapter, viewGroup, false);
            return new TeacherScheduleViewHolder(view);
        }
        else {
            View view= LayoutInflater.from(context).inflate(R.layout.just_a_text_view_layout, viewGroup, false);
            return new EmptyViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof StudentScheduleViewHolder) {
            StudentScheduleViewHolder h = (StudentScheduleViewHolder) viewHolder;
            h.startTime.setText(list.get(i).getCorrected_from_time());
            h.endTime.setText(list.get(i).getCorrected_to_time());
            h.location.setText(list.get(i).getRoom());
            h.teacher.setText(list.get(i).getTeachers());
            h.subject.setText(list.get(i).getSubject());
        }
        else if (viewHolder instanceof TeacherScheduleViewHolder){
            TeacherScheduleViewHolder h= (TeacherScheduleViewHolder) viewHolder;
//            h.startTime.setText(list2.get(i).getStartTime());
//            h.endTime.setText(list2.get(i).getEndTime());
//            h.location.setText(list2.get(i).getLocation());
//            h.batch.setText(list2.get(i).getBatch());
//            h.subject.setText(list2.get(i).getSubject());
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

    class StudentScheduleViewHolder extends RecyclerView.ViewHolder {

        private TextView startTime, endTime, subject, teacher, location;

        private StudentScheduleViewHolder(@NonNull View itemView) {
            super(itemView);
//            Log.e("TAG", "StudentScheduleViewHolder: " );
            startTime= itemView.findViewById(R.id.tv_starting_time);
            endTime= itemView.findViewById(R.id.tv_ending_time);
            subject= itemView.findViewById(R.id.tv_subject_student_schedule);
            teacher= itemView.findViewById(R.id.tv_teacher_student_schedule);
            location= itemView.findViewById(R.id.tv_location_student_schedule);
        }
    }

    public class TeacherScheduleViewHolder extends RecyclerView.ViewHolder {

        private TextView startTime, endTime, subject, batch, location;

        public TeacherScheduleViewHolder(@NonNull View itemView) {
            super(itemView);
            startTime= itemView.findViewById(R.id.tv_starting_time);
            endTime= itemView.findViewById(R.id.tv_ending_time);
            subject= itemView.findViewById(R.id.tv_subject_student_schedule);
            batch= itemView.findViewById(R.id.tv_teacher_student_schedule);
            location= itemView.findViewById(R.id.tv_location_student_schedule);
        }
    }

    public class EmptyViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;

        public EmptyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textView = itemView.findViewById(R.id.tv_just_text_view);
        }
    }
}
