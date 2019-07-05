package com.myapp.classroomupdates.interfaces;

import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.myapp.classroomupdates.Globals;
import com.myapp.classroomupdates.R;

public class MultipleEditTextWatcher implements TextWatcher {

    private TextInputLayout inputLayout;

    public MultipleEditTextWatcher(TextInputLayout textInputLayout) {
        this.inputLayout = textInputLayout;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (inputLayout.getId()== R.id.til_name_login||
                inputLayout.getId()==R.id.til_email_sign_up||
                inputLayout.getId()== R.id.til_email_forgot_pass){
            if (!Globals.isEmailValid(s)){
                inputLayout.setError("Email not valid");
            }
            else {
                inputLayout.setError(null);
            }
        }
        else if (inputLayout.getId()== R.id.til_password_login
                    || inputLayout.getId()== R.id.til_confirm_pass_change
                    ||inputLayout.getId()== R.id.til_new_password
                    || inputLayout.getId()== R.id.til_current_password
                    || inputLayout.getId()== R.id.til_password_sign_up
                    || inputLayout.getId()== R.id.til_confirm_pass_sign_up){
            if (s.length()<8){
                inputLayout.setError("Password should be at least 8 chars long");
            }
            else {
                inputLayout.setError(null);
            }
        }

        else if (inputLayout.getId()== R.id.til_name_sign_up){
            if (Globals.isEmpty(inputLayout.getEditText().getText().toString())){
                inputLayout.setError("Name cant be empty!!");
            }
            else {
                inputLayout.setError(null);
            }
        }

        else if (inputLayout.getId()== R.id.til_batch_student_signup|| inputLayout.getId()== R.id.til_roll_student_signup){
            if (!Globals.isthreeNumberFormat(s)){
                inputLayout.setError("Please match the format.");
            }
            else {
                inputLayout.setError(null);
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
