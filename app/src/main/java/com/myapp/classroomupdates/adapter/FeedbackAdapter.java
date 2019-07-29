package com.myapp.classroomupdates.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myapp.classroomupdates.R;
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
        View view= LayoutInflater.from(context).inflate(R.layout.feedback_adapter_layout, viewGroup, false);
        return new FeedbackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        FeedbackViewHolder h= (FeedbackViewHolder) viewHolder;
        h.teacher.setText(list.get(i).getTeacher_name());
        h.message.setText(list.get(i).getReview());
        String date= list.get(i).getDatetime_created().substring(0, 10);
        h.date.setText(date);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class FeedbackViewHolder extends RecyclerView.ViewHolder{
        TextView teacher, message, date;

        public FeedbackViewHolder(@NonNull View itemView) {
            super(itemView);
            teacher= itemView.findViewById(R.id.tv_feedback_adapter_teacher);
            message= itemView.findViewById(R.id.tv_feedback_adapter_review);
            date= itemView.findViewById(R.id.tv_feedback_adapter_date);
        }
    }
}
