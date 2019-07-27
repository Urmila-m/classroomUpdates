package com.myapp.classroomupdates.model;

import java.util.ArrayList;
import java.util.List;

public class ChangePasswordErrorModel {
    private ArrayList<String> new_password2;

    public ArrayList<String> getNew_password2 ()
    {
        return new_password2;
    }

    public void setNew_password2 (ArrayList<String> new_password2)
    {
        this.new_password2 = new_password2;
    }

    @Override
    public String toString()
    {
        String msg="";
        for (String s:new_password2
             ) {
            msg+=s+"\n";
        }
        return "ClassPojo [new_password2 = "+msg+"]";
    }
}
