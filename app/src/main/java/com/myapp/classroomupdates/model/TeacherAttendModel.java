package com.myapp.classroomupdates.model;

import java.io.Serializable;

public class TeacherAttendModel implements Serializable {
    private Boolean attend;
    private String message, time, subject, program, semester, date, email;

    public TeacherAttendModel(Boolean attend, String message, String time, String subject, String program, String semester, String date, String email) {
        this.attend = attend;
        this.message = message;
        this.time = time;
        this.subject = subject;
        this.program = program;
        this.semester = semester;
        this.date = date;
        this.email = email;
    }

    public Boolean getAttend() {
        return attend;
    }

    public void setAttend(Boolean attend) {
        this.attend = attend;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "TeacherAttendModel{" +
                "attend=" + attend +
                ", message='" + message + '\'' +
                ", time='" + time + '\'' +
                ", subject='" + subject + '\'' +
                ", program='" + program + '\'' +
                ", semester='" + semester + '\'' +
                ", date='" + date + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
