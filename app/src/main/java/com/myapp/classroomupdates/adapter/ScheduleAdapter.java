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
import com.myapp.classroomupdates.model.StudentScheduleModel;

import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<StudentScheduleModel> list;
    private Context context;

    public ScheduleAdapter(Context context, List<StudentScheduleModel> list) {
        this.context=context;
        this.list= list;
        Log.e("TAG", "ScheduleAdapter: ");
//        for (StudentScheduleModel s:list
//             ) {
//            Log.e("TAG", s.toString() );
//        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.e("TAG", "onCreateViewHolder: ");
        View view= LayoutInflater.from(context).inflate(R.layout.student_schedule_adapter, viewGroup, false);
        return new StudentScheduleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        StudentScheduleViewHolder h= (StudentScheduleViewHolder) viewHolder;
        Log.e("TAG", "onBindViewHolder: "+i);
        h.startTime.setText(list.get(i).getStartTime());
        h.endTime.setText(list.get(i).getEndTime());
        h.location.setText(list.get(i).getLocation());
        h.teacher.setText(list.get(i).getTeacher());
        h.subject.setText(list.get(i).getSubject());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class StudentScheduleViewHolder extends RecyclerView.ViewHolder {

        public TextView startTime, endTime, subject, teacher, location;

        public StudentScheduleViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.e("TAG", "StudentScheduleViewHolder: " );
            startTime= itemView.findViewById(R.id.tv_starting_time);
            endTime= itemView.findViewById(R.id.tv_ending_time);
            subject= itemView.findViewById(R.id.tv_subject_student_schedule);
            teacher= itemView.findViewById(R.id.tv_teacher_student_schedule);
            location= itemView.findViewById(R.id.tv_location_student_schedule);
        }
    }
//
//    public class TeacherScheduleViewHolder extends RecyclerView.ViewHolder {
//
//        public TextView startTime, endTime, subject, teacherOrBatch, location;
//
//        public TeacherScheduleViewHolder(@NonNull View itemView) {
//            super(itemView);
//            startTime= itemView.findViewById(R.id.tv_starting_time);
//            endTime= itemView.findViewById(R.id.tv_ending_time);
//            subject= itemView.findViewById(R.id.tv_subject_student_schedule);
//            teacherOrBatch= itemView.findViewById(R.id.tv_teacher_student_schedule);
//            location= itemView.findViewById(R.id.tv_location_student_schedule);
//        }
//    }
}
