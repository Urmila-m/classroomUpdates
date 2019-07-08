package com.myapp.classroomupdates.model;

import java.io.Serializable;

public class PostResponse implements Serializable {
    private boolean success;
    private String message;

    public PostResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    @Override
    public String toString() {
        return "PostResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                '}';
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
