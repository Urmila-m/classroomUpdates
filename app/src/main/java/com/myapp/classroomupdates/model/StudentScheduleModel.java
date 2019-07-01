package com.myapp.classroomupdates.model;

import java.io.Serializable;

public class StudentScheduleModel extends ScheduleModel implements Serializable {

    private String teacher;

    public StudentScheduleModel(String startTime, String endTime, String subject, String location, String teacher) {
        super(startTime, endTime, subject, location);
        this.teacher= teacher;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return "StudentScheduleModel{" +
                "teacher='" + teacher + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", subject='" + subject + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
