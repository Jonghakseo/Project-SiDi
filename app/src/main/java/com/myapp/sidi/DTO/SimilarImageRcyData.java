package com.myapp.sidi.DTO;

public class SimilarImageRcyData {
    private String design;
    private int designId;

    public SimilarImageRcyData(String design, int designId) {
        this.design = design;
        this.designId = designId;
    }

    public String getDesign() {
        return design;
    }

    public void setDesign(String design) {
        this.design = design;
    }

    public int getDesignId() {
        return designId;
    }

    public void setDesignId(int designId) {
        this.designId = designId;
    }
}