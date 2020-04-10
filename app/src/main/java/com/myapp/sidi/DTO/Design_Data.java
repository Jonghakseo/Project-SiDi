package com.myapp.sidi.DTO;

public class Design_Data {
    private String design;
    private String tag_1;
    private String tag_2;
    private String tag_3;
    private String tag_4;


    public Design_Data(String design, String tag_1, String tag_2, String tag_3, String tag_4) {
        this.design = design;
        this.tag_1 = tag_1;
        this.tag_2 = tag_2;
        this.tag_3 = tag_3;
        this.tag_4 = tag_4;
    }


    public String getDesign() {
        return design;
    }

    public void setDesign(String design) {
        this.design = design;
    }

    public String getTag_1() {
        return tag_1;
    }

    public void setTag_1(String tag_1) {
        this.tag_1 = tag_1;
    }

    public String getTag_2() {
        return tag_2;
    }

    public void setTag_2(String tag_2) {
        this.tag_2 = tag_2;
    }

    public String getTag_3() {
        return tag_3;
    }

    public void setTag_3(String tag_3) {
        this.tag_3 = tag_3;
    }

    public String getTag_4() {
        return tag_4;
    }

    public void setTag_4(String tag_4) {
        this.tag_4 = tag_4;
    }
}
