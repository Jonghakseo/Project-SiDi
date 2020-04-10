package com.myapp.sidi.DTO;

public class SearchResultData {
    private String design;
    private String designId;

    public SearchResultData(String design, String designId) {
        this.design = design;
        this.designId = designId;
    }

    public String getDesign() {
        return design;
    }

    public void setDesign(String design) {
        this.design = design;
    }




    public String getDesignId() {
        return designId;
    }

    public void setDesignId(String designId) {
        this.designId = designId;
    }

}
