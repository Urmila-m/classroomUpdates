package com.myapp.classroomupdates.model;

public class AttendanceModel {
    private int totalClasses, attendedClasses;

    public int getTotalClasses() {
        return totalClasses;
    }

    public void setTotalClasses(int totalClasses) {
        this.totalClasses = totalClasses;
    }

    public int getAttendedClasses() {
        return attendedClasses;
    }

    public void setAttendedClasses(int attendedClasses) {
        this.attendedClasses = attendedClasses;
    }

    public AttendanceModel(int totalClasses, int attendedClasses) {
        this.totalClasses = totalClasses;
        this.attendedClasses = attendedClasses;
    }

    @Override
    public String toString() {
        return "AttendanceModel{" +
                "totalClasses=" + totalClasses +
                ", attendedClasses=" + attendedClasses +
                '}';
    }
}
