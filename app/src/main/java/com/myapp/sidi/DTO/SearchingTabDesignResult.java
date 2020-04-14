package com.myapp.sidi.DTO;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SearchingTabDesignResult {

   private String test;

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }
    public List<SearchingTabDesignDetailResult> list = new ArrayList<>();

    public List<SearchingTabDesignDetailResult> getList() {
        return list;
    }

    public void setList(List<SearchingTabDesignDetailResult> list) {
        this.list = list;
    }

    public class SearchingTabDesignDetailResult{
        String dep1;
        String dep2;
        String dep3;

        public String getDep1() {
            return dep1;
        }

        public void setDep1(String dep1) {
            this.dep1 = dep1;
        }

        public String getDep2() {
            return dep2;
        }

        public void setDep2(String dep2) {
            this.dep2 = dep2;
        }

        public String getDep3() {
            return dep3;
        }

        public void setDep3(String dep3) {
            this.dep3 = dep3;
        }
    }
}
