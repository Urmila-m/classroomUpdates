package com.myapp.classroomupdates.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PostResponse implements Serializable {
    @SerializedName("detail")
    private String detail;

    public PostResponse(String detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "PostResponse{" +
                "detail=" + detail +
                '}';
    }


    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
