package com.myapp.classroomupdates.model;

import java.io.Serializable;

public class FeedbackModel implements Serializable {
    private Boolean regular, interactive;
    private String suggestion;

    public FeedbackModel(Boolean regular, Boolean interactive, String suggestion) {
        this.regular = regular;
        this.interactive = interactive;
        this.suggestion = suggestion;
    }

    public Boolean getRegular() {
        return regular;
    }

    public void setRegular(Boolean regular) {
        this.regular = regular;
    }

    public Boolean getInteractive() {
        return interactive;
    }

    public void setInteractive(Boolean interactive) {
        this.interactive = interactive;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    @Override
    public String toString() {
        return "FeedbackModel{" +
                "regular=" + regular +
                ", interactive=" + interactive +
                ", suggestion='" + suggestion + '\'' +
                '}';
    }
}
