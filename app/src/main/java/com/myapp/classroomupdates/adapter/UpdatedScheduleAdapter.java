package com.myapp.classroomupdates.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.myapp.classroomupdates.R;
import com.myapp.classroomupdates.model.ScheduleModel;

import java.util.List;

public class UpdatedScheduleAdapter extends RecyclerView.Adapter {
    private List<ScheduleModel> list;
    private Context context;
    private String date;

    public UpdatedScheduleAdapter(Context context, List<ScheduleModel> list, String date) {
        this.list = list;
        this.context = context;
        this.date= date;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder viewHolder = null;
        if (list.size()==0){
            View view= LayoutInflater.from(context).inflate(R.layout.just_a_text_view_layout, viewGroup, false);
            viewHolder= new EmptyViewHolder(view);
        }
        else {
            View view= LayoutInflater.from(context).inflate(R.layout.updated_rotine_adapter_layout, viewGroup, false);
            viewHolder= new UpdatedScheduleViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof EmptyViewHolder){
            EmptyViewHolder h= (EmptyViewHolder) viewHolder;
            h.textView.setText("Not updated any class yet.");
        }
        else {
            UpdatedScheduleViewHolder h= (UpdatedScheduleViewHolder) viewHolder;
            h.expectedStartTime.setText(list.get(i).getFrom_time().substring(0, 5));
            h.expectedEndTime.setText(list.get(i).getTo_time().substring(0, 5));
            h.updatedStartTime.setText(list.get(i).getCorrected_from_time().substring(0, 5));
            h.updatedEndTime.setText(list.get(i).getCorrected_to_time().substring(0, 5));
            h.subject.setText(list.get(i).getSubject());
            h.room.setText(list.get(i).getRoom());
            String batch= list.get(i).getRoutine_of().getProgramme().getShort_form()+" year:"+ list.get(i).getRoutine_of().getYear()+" part:"+list.get(i).getRoutine_of().getPart()+" group:"+list.get(i).getRoutine_of().getGroup();
            h.batch.setText(batch);
            h.chkAttend.setChecked(list.get(i).getIs_attending());
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

    class UpdatedScheduleViewHolder extends RecyclerView.ViewHolder {
        TextView expectedStartTime, expectedEndTime, updatedStartTime, updatedEndTime, subject, batch, room;
        CheckBox chkAttend;
        public UpdatedScheduleViewHolder(@NonNull View itemView) {
            super(itemView);
            expectedEndTime= itemView.findViewById(R.id.tv_expected_end_adapter);
            expectedStartTime= itemView.findViewById(R.id.tv_expected_start_adapter);
            updatedStartTime= itemView.findViewById(R.id.tv_updated_start_adapter);
            updatedEndTime= itemView.findViewById(R.id.tv_updated_end_adapter);
            subject= itemView.findViewById(R.id.tv_subject_updated);
            batch= itemView.findViewById(R.id.tv_batch_updated);
            room= itemView.findViewById(R.id.tv_room_updated);
            chkAttend= itemView.findViewById(R.id.chk_updated_routine_adapter);
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
