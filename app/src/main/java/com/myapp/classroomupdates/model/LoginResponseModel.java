package com.myapp.classroomupdates.model;

public class LoginResponseModel {
    String token;
    StudentModel student_detail;
    TeacherModel teacher_detail;

    public LoginResponseModel(String token, StudentModel student_detail, TeacherModel teacher_detail) {
        this.token = token;
        this.student_detail = student_detail;
        this.teacher_detail = teacher_detail;
    }

    @Override
    public String toString() {
        return "LoginResponseModel{" +
                "token='" + token + '\'' +
                ", student_detail=" + student_detail +
                ", teacher_detail=" + teacher_detail +
                '}';
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public StudentModel getStudent_detail() {
        return student_detail;
    }

    public void setStudent_detail(StudentModel student_detail) {
        this.student_detail = student_detail;
    }

    public TeacherModel getTeacher_detail() {
        return teacher_detail;
    }

    public void setTeacher_detail(TeacherModel teacher_detail) {
        this.teacher_detail = teacher_detail;
    }
}
