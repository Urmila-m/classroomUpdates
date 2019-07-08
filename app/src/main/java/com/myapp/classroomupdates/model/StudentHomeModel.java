package com.myapp.classroomupdates.model;

import java.io.Serializable;

public class StudentHomeModel implements Serializable {
    private String teacher, subject, location, date, msg, time;
    private Boolean attend;

    public StudentHomeModel(String teacher, String subject, String location, String date, String msg, String time, Boolean attend) {
        this.teacher = teacher;
        this.subject = subject;
        this.location = location;
        this.date = date;
        this.msg = msg;
        this.time = time;
        this.attend = attend;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Boolean getAttend() {
        return attend;
    }

    public void setAttend(Boolean attend) {
        this.attend = attend;
    }

    @Override
    public String toString() {
        return "StudentHomeModel{" +
                "teacher='" + teacher + '\'' +
                ", subject='" + subject + '\'' +
                ", location='" + location + '\'' +
                ", date='" + date + '\'' +
                ", msg='" + msg + '\'' +
                ", time='" + time + '\'' +
                ", attend=" + attend +
                '}';
    }
}
