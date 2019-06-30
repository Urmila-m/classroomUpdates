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
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.myapp.classroomupdates.R;
import com.myapp.classroomupdates.interfaces.OnFragmentClickListener;

import static android.view.View.GONE;

public class TeacherAttendFragment extends Fragment {

    private TextView tvExpectedTime, message, tvsubject;
    private RadioButton rbAttend, rbNotAttend;
    private TextInputLayout tilMessage;
    private TimePicker timePicker;
    private Button btnSubmit;
    private OnFragmentClickListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentClickListener){
            listener= (OnFragmentClickListener) context;
        }
    }

    public TeacherAttendFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.teacher_attend, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvExpectedTime= view.findViewById(R.id.tv_expected_time);
        rbAttend= view.findViewById(R.id.rb_attend_teacher);
        rbNotAttend= view.findViewById(R.id.rb_no_attend_teacher);
        tilMessage= view.findViewById(R.id.til_message_teacher);
        timePicker= view.findViewById(R.id.tp_teacher);
        message= view.findViewById(R.id.message_teacher);
        tvsubject= view.findViewById(R.id.tv_subject);
        btnSubmit= view.findViewById(R.id.btn_submit_teacher_attend);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tvsubject.setText("Software Engineering");
        tvExpectedTime.setText("12:00 pm");//TODO server bata time extract garera show garne

        rbNotAttend.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    message.setVisibility(View.VISIBLE);
                    tilMessage.setVisibility(View.VISIBLE);
                    timePicker.setVisibility(GONE);
                }
                else {
                    message.setVisibility(GONE);
                    tilMessage.setVisibility(GONE);
                    timePicker.setVisibility(View.VISIBLE);
                }
            }
        });

//        rbAttend.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked){
//                    timePicker.setVisibility(GONE);
//                }
//                else {
//                    timePicker.setVisibility(View.VISIBLE);
//                }
//            }
//        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b= new Bundle();
                String attendOrNotAttend= rbAttend.isSelected()?"attend":"notAttend";
                if (attendOrNotAttend.equals("attend")){
                    int hr= timePicker.getHour();
                    int min= timePicker.getMinute();
                    String time= String.format("%02d", hr)+ String.format("%02d", min);
                    b.putString("time", time);
                }

                else {
                    String message= tilMessage.getEditText().getText().toString();
                    b.putString("message", message);
                }
                listener.onFragmentClicked(b, btnSubmit.getId());
            }
        });
    }
}
