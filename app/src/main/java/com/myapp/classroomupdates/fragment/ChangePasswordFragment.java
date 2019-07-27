package com.myapp.classroomupdates.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.myapp.classroomupdates.Globals;
import com.myapp.classroomupdates.activity.AfterLoginActivityStudent;
import com.myapp.classroomupdates.activity.AfterLoginTeacherActivity;
import com.myapp.classroomupdates.interfaces.MultipleEditTextWatcher;
import com.myapp.classroomupdates.interfaces.OnFragmentClickListener;
import com.myapp.classroomupdates.R;
import com.myapp.classroomupdates.model.ChangePasswordErrorModel;
import com.myapp.classroomupdates.model.PostResponse;
import com.myapp.classroomupdates.utility.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.myapp.classroomupdates.Globals.apiInterface;
import static com.myapp.classroomupdates.Globals.fromJsonToStudent;
import static com.myapp.classroomupdates.Globals.fromJsonToTeacher;
import static com.myapp.classroomupdates.Globals.getStringFromTIL;
import static com.myapp.classroomupdates.Globals.isEmpty;
import static com.myapp.classroomupdates.Globals.preferences;
import static com.myapp.classroomupdates.Globals.showSnackbar;

public class ChangePasswordFragment extends Fragment {
    private TextInputLayout tilCurrentPassword, tilNewPassword, tilConfirmPass;
    private Button btnSave;
    private FrameLayout frameLayout;

    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.change_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tilNewPassword= view.findViewById(R.id.til_new_password);
        tilCurrentPassword= view.findViewById(R.id.til_current_password);
        tilConfirmPass= view.findViewById(R.id.til_confirm_pass_change);
        btnSave= view.findViewById(R.id.btn_change_password);
        if (getContext() instanceof AfterLoginActivityStudent) {
            frameLayout = ((AfterLoginActivityStudent) getContext()).getFrameLayout();
        }
        else if (getContext() instanceof AfterLoginTeacherActivity){
            frameLayout= ((AfterLoginTeacherActivity)getContext()).getFrameLayout();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tilConfirmPass.getEditText().addTextChangedListener(new MultipleEditTextWatcher(tilConfirmPass));
        tilCurrentPassword.getEditText().addTextChangedListener(new MultipleEditTextWatcher(tilCurrentPassword));
        tilNewPassword.getEditText().addTextChangedListener(new MultipleEditTextWatcher(tilNewPassword));
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TAG", "onClick: clicked" );
                if (isValidData()) {
                    if (getStringFromTIL(tilNewPassword).equals(getStringFromTIL(tilConfirmPass))) {
                        String newPassword = getStringFromTIL(tilNewPassword);
                        String currentPassword = getStringFromTIL(tilCurrentPassword);
                        String password = null;
                        if (preferences.getString("user_type", "").equals("Student")){
                            password= fromJsonToStudent(preferences.getString("Student", "")).getPassword();
                        }
                        else if (preferences.getString("user_type", "").equals("Teacher")){
                            password= fromJsonToTeacher(preferences.getString("Teacher", "")).getPassword();
                        }
                        if(currentPassword.equals(password)){
                            if (NetworkUtils.isNetworkConnected(getContext())) {
                                apiInterface.changeUserPassword("Token "+preferences.getString("token", ""), newPassword, newPassword)
                                        .enqueue(new Callback<PostResponse>() {
                                            @Override
                                            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                                                if (response.isSuccessful()){
                                                    Toast.makeText(getContext(), response.body().getDetail(), Toast.LENGTH_LONG).show();
                                                    if (getContext() instanceof AfterLoginTeacherActivity) {
                                                        ((AfterLoginTeacherActivity) getContext()).clearAllFragmentTransactions();
                                                    }
                                                    else if (getContext() instanceof AfterLoginActivityStudent){
                                                        ((AfterLoginActivityStudent) getContext()).clearAllFragmentTransactions();
                                                    }
                                                    getActivity().recreate();
                                                }
                                                else {
                                                    try {
                                                        JSONObject jsonObject= new JSONObject(response.errorBody().string());
                                                        JSONArray array= jsonObject.getJSONArray("new_password2");
                                                        String[] arr= new String[array.length()];
                                                        for( int i=0; i<array.length(); i++){
                                                            arr[i]= array.getString(i);
                                                        }
                                                        String displayText="";
                                                        for (String s:arr
                                                             ) {
                                                            displayText+=(s+"\n");
                                                        }
                                                        Toast.makeText(getContext(), displayText, Toast.LENGTH_SHORT).show();
                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }
                                            @Override
                                            public void onFailure(Call<PostResponse> call, Throwable t) {
                                                Log.e("TAG", "onFailure: "+t.getMessage());
                                                Toast.makeText(getContext(), "Something went wrong :(", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            } else
                                showSnackbar(v, "Password change failed!");
                        }
                        else{
                            Toast.makeText(getContext(), "Incorrect current password", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(getContext(), "Passwords don't match!!", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getContext(), "Make sure there are no errors and fill all the fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isValidData(){
        if (tilConfirmPass.getError()==null && tilCurrentPassword.getError()== null && tilNewPassword.getError()== null &&
                (!isEmpty(tilConfirmPass.getEditText().getText().toString()))&&
                (!isEmpty(tilNewPassword.getEditText().getText().toString()))&&
                (!isEmpty(tilCurrentPassword.getEditText().getText().toString()))){
            return true;
        }
        else
            return false;
    }

}
