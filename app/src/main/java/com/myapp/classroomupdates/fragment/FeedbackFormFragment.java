package com.myapp.classroomupdates.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.myapp.classroomupdates.R;
import com.myapp.classroomupdates.interfaces.OnFragmentClickListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeedbackFormFragment extends Fragment {
    private RadioButton question1, question2;
    private TextInputLayout tilQuestion3;
    private Button btnSubmit;
    private OnFragmentClickListener listener;
    private Bundle bundle;

    public FeedbackFormFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentClickListener){
            listener= (OnFragmentClickListener) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= LayoutInflater.from(getContext()).inflate(R.layout.feedback_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        question1= view.findViewById(R.id.rb_question1);
        question2= view.findViewById(R.id.rb_question2);
        tilQuestion3= view.findViewById(R.id.til_question3);
        btnSubmit= view.findViewById(R.id.btn_feedback_submit);
        bundle= new Bundle();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ans1= question1.isChecked()?"Yes":"No";
                String ans2= question2.isChecked()?"Yes":"No";
                String feedbackToTeacher= tilQuestion3.getEditText().getText().toString();
                bundle.putString("ans1", ans1);
                bundle.putString("ans2", ans2);
                bundle.putString("ans3", feedbackToTeacher);
                listener.onFragmentClicked(bundle, btnSubmit.getId());
            }
        });
    }
}
