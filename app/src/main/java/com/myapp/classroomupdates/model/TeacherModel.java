package com.myapp.classroomupdates.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class TeacherModel implements Serializable {
        private String password;

        private String phone;

        private String subjects;

        private String name;

        private boolean is_full_timer;

        private String short_name;

//        private String id;

        private String department;

        private String user;

        private String email;

        public String getPassword ()
        {
            return password;
        }

        public void setPassword (String password)
        {
            this.password = password;
        }

        public String getPhone ()
    {
        return phone;
    }

        public void setPhone (String phone)
        {
            this.phone = phone;
        }

        public String getSubjects ()
        {
            return subjects;
        }

        public void setSubjects (String subjects)
        {
            this.subjects = subjects;
        }

        public String getName ()
        {
            return name;
        }

        public void setName (String name)
        {
            this.name = name;
        }

        public boolean getIs_full_timer ()
        {
            return is_full_timer;
        }

        public void setIs_full_timer (boolean is_full_timer)
        {
            this.is_full_timer = is_full_timer;
        }

        public String getShort_name ()
    {
        if (short_name==null){
            return "";
        }
        else {
            return short_name;
        }
    }

        public void setShort_name (String short_name)
        {
            this.short_name = short_name;
        }

//        public String getId ()
//        {
//            return id;
//        }

//        public void setId (String id)
//        {
//            this.id = id;
//        }

        public String getDepartment ()
    {
        if (department==null){
            return "";
        }
        else {
            return department;
        }
    }

        public void setDepartment (String department)
        {
            this.department = department;
        }

        public String getUser ()
        {
            return user;
        }

        public void setUser (String user)
        {
            this.user = user;
        }

        public String getEmail ()
        {
            return email;
        }

        public void setEmail (String email)
        {
            this.email = email;
        }

        @Override
        public String toString()
        {
            return "TeacherModel [password = "+password+", phone = "+phone+", subjects = "+subjects+", name = "+name+", is_full_timer = "+is_full_timer+", short_name = "+short_name+", department = "+department+", user = "+user+", email = "+email+"]";
        }
}