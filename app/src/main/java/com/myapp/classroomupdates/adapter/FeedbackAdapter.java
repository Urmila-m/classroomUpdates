package com.myapp.classroomupdates.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myapp.classroomupdates.R;
import com.myapp.classroomupdates.activity.AfterLoginActivityStudent;
import com.myapp.classroomupdates.activity.AfterLoginTeacherActivity;
import com.myapp.classroomupdates.model.FeedbackModel;

import java.util.List;

public class FeedbackAdapter extends RecyclerView.Adapter {
    private List<FeedbackModel> list;
    private Context context;

    public FeedbackAdapter(Context context, List<FeedbackModel> list) {
        this.list = list;
        this.context= context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (list.size()==0) {
            View view = LayoutInflater.from(context).inflate(R.layout.just_a_text_view_layout, viewGroup, false);
            return new EmptyViewHolder(view);
        }
        else {
            View view = LayoutInflater.from(context).inflate(R.layout.feedback_adapter_layout, viewGroup, false);
            return new FeedbackViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof FeedbackViewHolder) {
            FeedbackViewHolder h = (FeedbackViewHolder) viewHolder;
            if (context instanceof AfterLoginActivityStudent) {
                h.teacher.setText(list.get(i).getTeacher_name());
            } else if (context instanceof AfterLoginTeacherActivity) {
                h.teacher.setVisibility(View.GONE);
                h.to.setVisibility(View.GONE);
                h.message.setPadding(0, 10, 0, 0);
            }
            h.message.setText(list.get(i).getReview());
            String date = list.get(i).getDatetime_created().substring(0, 10);
            h.date.setText(date);
        }
        else if(viewHolder instanceof EmptyViewHolder){
            EmptyViewHolder h= (EmptyViewHolder) viewHolder;
            h.textView.setText("No feedbacks yet!!");
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

    class FeedbackViewHolder extends RecyclerView.ViewHolder{
        TextView teacher, message, date, to;

        public FeedbackViewHolder(@NonNull View itemView) {
            super(itemView);
            to= itemView.findViewById(R.id.to);
            teacher= itemView.findViewById(R.id.tv_feedback_adapter_teacher);
            message= itemView.findViewById(R.id.tv_feedback_adapter_review);
            date= itemView.findViewById(R.id.tv_feedback_adapter_date);
        }
    }

    class EmptyViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public EmptyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView= itemView.findViewById(R.id.tv_just_text_view);
        }
    }
}
