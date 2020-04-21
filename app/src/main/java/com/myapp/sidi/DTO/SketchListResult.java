package com.myapp.sidi.DTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SketchListResult {

    @SerializedName("result")
    @Expose
    private List<Result> result = new ArrayList<>();

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public class Result {
        @SerializedName("uploadUser")
        @Expose
        private String uploadUser;
        @SerializedName("url")
        @Expose
        private String url;

        public String getUploadUser() {
            return uploadUser;
        }

        public void setUploadUser(String uploadUser) {
            this.uploadUser = uploadUser;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }



}
