package com.myapp.classroomupdates.model;

import java.io.Serializable;

public class FeedbackModel implements Serializable {

    private String datetime_updated;

    private String teacher_name;

//    private null questionnaire;

    private int teacher;

    private String review;

    private String feedback_by_name;

    private String datetime_created;

    private int id;

    private int feedback_by;

    public String getDatetime_updated ()
    {
        return datetime_updated;
    }

    public void setDatetime_updated (String datetime_updated)
    {
        this.datetime_updated = datetime_updated;
    }

    public String getTeacher_name ()
    {
        return teacher_name;
    }

    public void setTeacher_name (String teacher_name)
    {
        this.teacher_name = teacher_name;
    }

//    public null getQuestionnaire ()
//    {
//        return questionnaire;
//    }
//
//    public void setQuestionnaire (null questionnaire)
//    {
//        this.questionnaire = questionnaire;
//    }

    public int getTeacher ()
    {
        return teacher;
    }

    public void setTeacher (int teacher)
    {
        this.teacher = teacher;
    }

    public String getReview ()
    {
        return review;
    }

    public void setReview (String review)
    {
        this.review = review;
    }

    public String getFeedback_by_name ()
    {
        return feedback_by_name;
    }

    public void setFeedback_by_name (String feedback_by_name)
    {
        this.feedback_by_name = feedback_by_name;
    }

    public String getDatetime_created ()
    {
        return datetime_created;
    }

    public void setDatetime_created (String datetime_created)
    {
        this.datetime_created = datetime_created;
    }

    public int getId ()
    {
        return id;
    }

    public void setId (int id)
    {
        this.id = id;
    }

    public int getFeedback_by ()
    {
        return feedback_by;
    }

    public void setFeedback_by (int feedback_by)
    {
        this.feedback_by = feedback_by;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [datetime_updated = "+datetime_updated+", teacher_name = "+teacher_name+", teacher = "+teacher+", review = "+review+", feedback_by_name = "+feedback_by_name+", datetime_created = "+datetime_created+", id = "+id+", feedback_by = "+feedback_by+"]";
    }
}
