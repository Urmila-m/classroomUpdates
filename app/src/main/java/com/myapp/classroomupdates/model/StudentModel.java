package com.myapp.classroomupdates.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StudentModel implements Serializable {
    private String password;

    private String programme_name;

    private boolean is_class_representative;

    private String phone;

    private String name;

    private String batch;

    private String roll_number;

    private String email;

    private String group;

    public String getPassword ()
    {
        return password;
    }

    public void setPassword (String password)
    {
        this.password = password;
    }

    public String getProgramme_name ()
    {
        return programme_name;
    }

    public void setProgramme_name (String programme_name)
    {
        this.programme_name = programme_name;
    }

    public boolean getIs_class_representative ()
    {
        return is_class_representative;
    }

    public void setIs_class_representative (boolean is_class_representative)
    {
        this.is_class_representative = is_class_representative;
    }

    public String getPhone ()
    {
        return phone;
    }

    public void setPhone (String phone)
    {
        this.phone = phone;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getBatch ()
    {
        return batch;
    }

    public void setBatch (String batch)
    {
        this.batch = batch;
    }

    public String getRoll_number ()
    {
        return roll_number;
    }

    public void setRoll_number (String roll_number)
    {
        this.roll_number = roll_number;
    }

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    public String getGroup ()
    {
        return group;
    }

    public void setGroup (String group)
    {
        this.group = group;
    }

    @Override
    public String toString()
    {
        return "StudentModel [password = "+password+", programme_name = "+programme_name+", is_class_representative = "+is_class_representative+", phone = "+phone+", name = "+name+", batch = "+batch+", roll_number = "+roll_number+", email = "+email+", group = "+group+"]";
    }
}
