package com.myapp.classroomupdates.model;

import java.io.Serializable;

public class ScheduleModel {

    protected String startTime, endTime, subject, location;

    public ScheduleModel(String startTime, String endTime, String subject, String location) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.subject = subject;
        this.location = location;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
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
}
