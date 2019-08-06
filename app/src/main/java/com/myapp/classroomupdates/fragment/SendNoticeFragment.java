package com.myapp.classroomupdates.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import static com.myapp.classroomupdates.Globals.apiInterface;
import static com.myapp.classroomupdates.Globals.fromJsonToTeacher;
import static com.myapp.classroomupdates.Globals.getStringFromTIL;
import static com.myapp.classroomupdates.Globals.preferences;
import static com.myapp.classroomupdates.Globals.showSnackbar;

import com.myapp.classroomupdates.R;
import com.myapp.classroomupdates.activity.PreferenceInitializingActivity;
import com.myapp.classroomupdates.model.NoticeModel;
import com.myapp.classroomupdates.model.ProgrammeModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendNoticeFragment extends BaseFragment {
    private HashMap<Integer, String> hashMap;
    
    private Spinner spYear;
    private Spinner spPart;
    private Spinner spProgram;
    private TextInputLayout tilNoticeDetail;
    private CheckBox sendSms;
    private Button sendNotice;

    private String selectedProgramName;
    private int selectedProgramId;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getArguments()!=null){
            hashMap= (HashMap<Integer, String>) getArguments().getSerializable("programList");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.send_notice_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spYear= view.findViewById(R.id.sp_notice_year);
        spPart= view.findViewById(R.id.sp_notice_part);
        tilNoticeDetail= view.findViewById(R.id.til_notice_details);
        sendSms= view.findViewById(R.id.chek_notice_send_sms);
        spProgram= view.findViewById(R.id.sp_notice_program);
        sendNotice= view.findViewById(R.id.btn_send_notice);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        List<String> programList= new ArrayList<>();
        for (Map.Entry<Integer, String> m:hashMap.entrySet()
        ) {
            programList.add("id:"+m.getKey()+"  "+m.getValue());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, programList);
        spProgram.setAdapter(adapter);
        spProgram.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected= spProgram.getItemAtPosition(position).toString();
                String[] firstSplit= selected.split("  ", 2);
                String[] secondSplit= firstSplit[0].split(":", 2);
                selectedProgramName= firstSplit[1];
                selectedProgramId= Integer.parseInt(secondSplit[1]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sendNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                ((PreferenceInitializingActivity)getContext()).dialog.show();
                NoticeModel noticeModel= new NoticeModel();
                noticeModel.setNoticeBy(fromJsonToTeacher(preferences.getString("Teacher", "")).getUser());
                noticeModel.setMessage(getStringFromTIL(tilNoticeDetail));
                noticeModel.setPart(spPart.getSelectedItem().toString());
                noticeModel.setYear(spPart.getSelectedItem().toString());
                noticeModel.setSendEmail(true);
                noticeModel.setSendSms(sendSms.isChecked());
                noticeModel.setProgramme(selectedProgramId);
                apiInterface.sendNotice("Token "+preferences.getString("token", ""), noticeModel)
                        .enqueue(new Callback<NoticeModel>() {
                            @Override
                            public void onResponse(Call<NoticeModel> call, Response<NoticeModel> response) {
                                ((PreferenceInitializingActivity)getContext()).dialog.dismiss();
                                if (response.isSuccessful()){
                                    String msg= sendSms.isChecked()?"Notice is sent via email and sms.":"Notice is sent via email.";
                                    Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
                                    getActivity().recreate();
                                }
                                else {
                                    try {
                                        JSONObject jsonObject= new JSONObject(response.errorBody().string());
                                        JSONArray array= jsonObject.getJSONArray("review");
                                        String message="";
                                        for (int i=0; i<array.length(); i++){
                                            message+= (array.getString(i)+"\n");
                                        }
                                        Log.e("TAG", "onResponse: "+message);
//                                        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    Toast.makeText(getContext(), "Something went wrong :(", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<NoticeModel> call, Throwable t) {
                                ((PreferenceInitializingActivity)getContext()).dialog.dismiss();
                                Log.e("TAG", "onFailure: "+t.getMessage());
                                showSnackbar(v, "Couldn't post the notice!");
                            }
                        });
            }
        });
    }
}
