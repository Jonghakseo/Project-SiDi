package com.myapp.sidi.DTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SimilarImageResult {
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
        @SerializedName("design")
        @Expose
        private String design;
        @SerializedName("similarity")
        @Expose
        private String similarity;

        public String getDesign() {
            return design;
        }

        public void setDesign(String design) {
            this.design = design;
        }

        public String getSimilarity() {
            return similarity;
        }

        public void setSimilarity(String similarity) {
            this.similarity = similarity;
        }
    }

}
