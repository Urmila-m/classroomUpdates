package com.myapp.classroomupdates.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class TeacherModel implements Serializable {

    private String email;
    private String name;
    private String phone;
    private String password;
    public String username;
//    @SerializedName("user_type")
//    private String ppLoc;
    private String subjects;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public TeacherModel(String email, String name, String password, String phone, String subjects) {
        this.email = email;
        this.name = name;
        this.password = password;
//        this.ppLoc = ppLoc;
        this.phone= phone;
        this.subjects = subjects;
    }

    public TeacherModel()
    {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public String getPpLoc() {
//        return ppLoc;
//    }
//
//    public void setPpLoc(String ppLoc) {
//        this.ppLoc = ppLoc;
//    }

    public String getSubjects() {
        return subjects;
    }

    public void setSubjects(String subjects) {
        this.subjects = subjects;
    }

    @Override
    public String toString() {
        return "TeacherModel{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
//                ", ppLoc='" + ppLoc + '\'' +
                ", subjects=" + subjects +
                '}';
    }
}
