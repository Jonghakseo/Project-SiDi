package com.myapp.sidi.DTO;

public class SketchListData {
    private String uploadUser;
    private String url;

    public SketchListData(String uploadUser, String url) {
        this.uploadUser = uploadUser;
        this.url = url;
    }

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
