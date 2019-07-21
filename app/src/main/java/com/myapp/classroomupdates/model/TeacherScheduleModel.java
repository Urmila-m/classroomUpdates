package com.myapp.classroomupdates.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TeacherScheduleModel extends ScheduleModel implements Serializable {

    @SerializedName("short_form")
    private String batch;

    public TeacherScheduleModel(String startTime, String endTime, String subject, String location) {
        super(startTime, endTime, subject, location);
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    @Override
    public String toString() {
        return "TeacherScheduleModel{" +
                "batch='" + batch + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", subject='" + subject + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
