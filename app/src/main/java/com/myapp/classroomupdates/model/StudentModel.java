package com.myapp.classroomupdates.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StudentModel implements Serializable {
    private String name;
    private String email;
    private String password;
    private String batch;

    @SerializedName("programme_name")
    private String program;

    @SerializedName("roll_number")
    private String roll;

    private String group;
//    private String ppLoc;

    @Override
    public String toString() {
        return "StudentModel{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", batch='" + batch + '\'' +
                ", program='" + program + '\'' +
                ", roll='" + roll + '\'' +
                ", group='" + group + '\'' +
//                ", ppLoc='" + ppLoc + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getBatch() {
        return batch;
    }

    public String getProgram() {
        return program;
    }

    public String getRoll() {
        return roll;
    }

    public String getGroup() {
        return group;
    }

//    public String getPpLoc() {
//        return ppLoc;
//    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public void setGroup(String group) {
        this.group = group;
    }

//    public void setPpLoc(String ppLoc) {
//        this.ppLoc = ppLoc;
//    }

    public StudentModel(String name, String email, String password, String batch, String program, String roll, String group) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.batch = batch;
        this.program = program;
        this.roll = roll;
        this.group = group;
//        this.ppLoc = ppLoc;
    }

}
