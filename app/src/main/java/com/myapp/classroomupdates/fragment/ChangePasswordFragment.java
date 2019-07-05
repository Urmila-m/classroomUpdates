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
import android.widget.Toast;

import com.myapp.classroomupdates.interfaces.MultipleEditTextWatcher;
import com.myapp.classroomupdates.interfaces.OnFragmentClickListener;
import com.myapp.classroomupdates.R;

import static com.myapp.classroomupdates.Globals.isEmpty;

public class ChangePasswordFragment extends Fragment {
    private TextInputLayout tilCurrentPassword, tilNewPassword, tilConfirmPass;
    private OnFragmentClickListener mListener;
    private Button btnSave;
    private Bundle bundle;

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
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentClickListener){
            mListener= (OnFragmentClickListener) context;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tilNewPassword= view.findViewById(R.id.til_new_password);
        tilCurrentPassword= view.findViewById(R.id.til_current_password);
        tilConfirmPass= view.findViewById(R.id.til_confirm_pass_change);
        btnSave= view.findViewById(R.id.btn_change_password);

        bundle= new Bundle();
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
                if (tilConfirmPass.getError()==null && tilCurrentPassword.getError()== null && tilNewPassword== null) {
                    if (isAllFilled()) {
                        if (tilNewPassword.getEditText().getText().toString().equals(tilConfirmPass.getEditText().getText().toString())) {
                            String currentPass = tilCurrentPassword.getEditText().getText().toString();
                            String newPassword = tilNewPassword.getEditText().getText().toString();
                            String confirmNewPass = tilNewPassword.getEditText().getText().toString();
                            bundle.putString("newPassword", newPassword);
                            mListener.onFragmentClicked(bundle, btnSave.getId());
                        }
                        else {
                            Toast.makeText(getContext(), "Passwords don't match!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(getContext(), "Fill all the fields", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private boolean isAllFilled(){
        if (!isEmpty(tilConfirmPass.getEditText().getText().toString())&&
                !isEmpty(tilNewPassword.getEditText().getText().toString())&&
                !isEmpty(tilCurrentPassword.getEditText().getText().toString())){
            return true;
        }
        else
            return false;
    }

}
