package com.myapp.classroomupdates.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.myapp.classroomupdates.R;
import com.myapp.classroomupdates.activity.AfterLoginActivityStudent;
import com.myapp.classroomupdates.model.FeedbackModel;

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

import static com.myapp.classroomupdates.Globals.apiInterface;
import static com.myapp.classroomupdates.Globals.getStringFromTIL;
import static com.myapp.classroomupdates.Globals.preferences;
import static com.myapp.classroomupdates.Globals.showSnackbar;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeedbackFormFragment extends Fragment {
    private RadioButton question1, question2;
    private TextInputLayout tilQuestion3;
    private Button btnSubmit;
    private FrameLayout frameLayout;
    private Spinner spinner;
    private HashMap<Integer, String> hashMap;
    private int selectedTeacherId;
    private String selectedTeacherName;

    public FeedbackFormFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(getArguments()!=null){
            hashMap= (HashMap<Integer, String>) getArguments().getSerializable("hashMap");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        spinner= view.findViewById(R.id.sp_select_teacher);
        frameLayout= ((AfterLoginActivityStudent)getContext()).getFrameLayout();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        List<String> teacherList= new ArrayList<>();
        for (Map.Entry<Integer, String> m:hashMap.entrySet()
        ) {
            teacherList.add("id:"+m.getKey()+"  "+m.getValue());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, teacherList);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selected= spinner.getItemAtPosition(position).toString();
                String[] firstSplit= selected.split("  ", 2);
                String[] secondSplit= firstSplit[0].split(":", 2);
                selectedTeacherName= firstSplit[1];
                selectedTeacherId= Integer.parseInt(secondSplit[1]);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                String ans1= question1.isChecked()?"Yes":"No";
                String ans2= question2.isChecked()?"Yes":"No";
                String feedbackToTeacher= getStringFromTIL(tilQuestion3);

                apiInterface.giveFeedback("Token "+preferences.getString("token", ""), selectedTeacherId,
                        preferences.getInt("id", 0), feedbackToTeacher)
                        .enqueue(new Callback<FeedbackModel>() {
                            @Override
                            public void onResponse(Call<FeedbackModel> call, Response<FeedbackModel> response) {
                                if (response.isSuccessful()){
                                    Toast.makeText(getContext(), "Feedback sent successfully!", Toast.LENGTH_SHORT).show();
                                    ((AfterLoginActivityStudent)getContext()).clearAllFragmentTransactions();
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
                                        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<FeedbackModel> call, Throwable t) {
                                Log.e("TAG", "onFailure: "+t.getMessage());
                                showSnackbar(v, "Couldn't send feedback!");
                            }
                        });
            }
        });
    }
}
