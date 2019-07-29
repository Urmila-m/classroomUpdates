package com.myapp.classroomupdates.model;

public class CanGiveFeedbackModel {
    private boolean can_give_feedback;

    public CanGiveFeedbackModel(boolean can_give_feedback) {
        this.can_give_feedback = can_give_feedback;
    }

    public boolean isCan_give_feedback() {
        return can_give_feedback;
    }

    public void setCan_give_feedback(boolean can_give_feedback) {
        this.can_give_feedback = can_give_feedback;
    }
}
