package com.myapp.classroomupdates.model;

import java.io.Serializable;
import java.util.List;

public class TeacherModel implements Serializable {
    String email, name, password, ppLoc, token;
    List<String> subjects;

    public TeacherModel(String email, String name, String password, String ppLoc, List<String> subjects) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.ppLoc = ppLoc;
        this.subjects = subjects;
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

    public String getPpLoc() {
        return ppLoc;
    }

    public void setPpLoc(String ppLoc) {
        this.ppLoc = ppLoc;
    }

    public List<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }

    @Override
    public String toString() {
        return "TeacherModel{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", ppLoc='" + ppLoc + '\'' +
                ", token='" + token + '\'' +
                ", subjects=" + subjects +
                '}';
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
