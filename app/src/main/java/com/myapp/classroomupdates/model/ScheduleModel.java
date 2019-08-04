package com.myapp.classroomupdates.model;

import java.io.Serializable;

public class ScheduleModel implements Serializable
{
    private String corrected_from_time;

    private RoutineOfModel routine_of;

    private String teachers;

    private String subject;

    private String corrected_to_time;

    private boolean is_attending;

    private int id;

    private String to_time;

    private String from_time;

    private boolean is_cancelled;

    private String room;

    private String day_of_week;

    public String getCorrected_from_time ()
    {
        return corrected_from_time;
    }

    public void setCorrected_from_time (String corrected_from_time)
    {
        this.corrected_from_time = corrected_from_time;
    }

    public RoutineOfModel getRoutine_of ()
    {
        return routine_of;
    }

    public void setRoutine_of (RoutineOfModel routine_of)
    {
        this.routine_of = routine_of;
    }

    public String getTeachers ()
    {
        return teachers;
    }

    public void setTeachers (String teachers)
    {
        this.teachers = teachers;
    }

    public String getSubject ()
    {
        return subject;
    }

    public void setSubject (String subject)
    {
        this.subject = subject;
    }

    public String getCorrected_to_time ()
    {
        return corrected_to_time;
    }

    public void setCorrected_to_time (String corrected_to_time)
    {
        this.corrected_to_time = corrected_to_time;
    }

    public boolean getIs_attending ()
    {
        return is_attending;
    }

    public void setIs_attending (boolean is_attending)
    {
        this.is_attending = is_attending;
    }

    public int getId ()
    {
        return id;
    }

    public void setId (int id)
    {
        this.id = id;
    }

    public String getTo_time ()
    {
        return to_time;
    }

    public void setTo_time (String to_time)
    {
        this.to_time = to_time;
    }

    public String getFrom_time ()
    {
        return from_time;
    }

    public void setFrom_time (String from_time)
    {
        this.from_time = from_time;
    }

    public boolean getIs_cancelled ()
    {
        return is_cancelled;
    }

    public void setIs_cancelled (boolean is_cancelled)
    {
        this.is_cancelled = is_cancelled;
    }

    public String getRoom ()
    {
        return room;
    }

    public void setRoom (String room)
    {
        this.room = room;
    }

    public String getDay_of_week ()
    {
        return day_of_week;
    }

    public void setDay_of_week (String day_of_week)
    {
        this.day_of_week = day_of_week;
    }

    @Override
    public String toString()
    {
        return "ScheduleModel [corrected_from_time = "+corrected_from_time+", routine_of = "+routine_of+", teachers = "+teachers+", subject = "+subject+", corrected_to_time = "+corrected_to_time+", is_attending = "+is_attending+", to_time = "+to_time+", from_time = "+from_time+", is_cancelled = "+is_cancelled+", room = "+room+", day_of_week = "+day_of_week+"]";
    }
}