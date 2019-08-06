package com.myapp.classroomupdates.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myapp.classroomupdates.R;
import com.myapp.classroomupdates.model.MarksResponseModel;

import java.util.List;

public class MarksAdapter extends RecyclerView.Adapter {
    private List<MarksResponseModel> list;
    private Context context;

    public MarksAdapter(List<MarksResponseModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder viewHolder= null;
        if (list.size()==0){
            View view = LayoutInflater.from(context).inflate(R.layout.just_a_text_view_layout, viewGroup, false);
            viewHolder= new EmptyViewHolder(view);
        }
        else {
            View view = LayoutInflater.from(context).inflate(R.layout.marks_adapter_layout, viewGroup, false);
            viewHolder= new MarksViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof EmptyViewHolder) {
            EmptyViewHolder h= (EmptyViewHolder) viewHolder;
            h.textView.setText("No marks records yet.");
        }
        else if (viewHolder instanceof MarksViewHolder){
            MarksViewHolder h= (MarksViewHolder) viewHolder;
            h.year.setText(list.get(i).getYear());
            h.part.setText(list.get(i).getPart());
            String thOrPr= list.get(i).getThPr().equals("th")?"( Theory )":"( Practical )";
            String fullMarks= list.get(i).getFullMarks()+"";
            h.thPr.setText(thOrPr);
//            h.passMarks.setText(list.get(i).getPassMarks());
            h.totalMarks.setText(fullMarks);
            h.marksObtained.setText(list.get(i).getMarks());
            h.subject.setText(list.get(i).getSubject());
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

    public class EmptyViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;

        EmptyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textView = itemView.findViewById(R.id.tv_just_text_view);
        }
    }

    class MarksViewHolder extends RecyclerView.ViewHolder{

        TextView year, part, thPr, passMarks, totalMarks, marksObtained, subject;
        public MarksViewHolder(@NonNull View itemView) {
            super(itemView);
            year= itemView.findViewById(R.id.tv_year_marks);
            part= itemView.findViewById(R.id.tv_part_marks);
            thPr= itemView.findViewById(R.id.tv_th_pr_marks);
            passMarks= itemView.findViewById(R.id.tv_pass_marks);
            totalMarks= itemView.findViewById(R.id.tv_total_marks);
            marksObtained= itemView.findViewById(R.id.tv_marks_obtained);
            subject= itemView.findViewById(R.id.tv_subject_marks);
        }
    }
}
