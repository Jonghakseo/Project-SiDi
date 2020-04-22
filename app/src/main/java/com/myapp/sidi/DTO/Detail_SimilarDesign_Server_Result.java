package com.myapp.sidi.DTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Detail_SimilarDesign_Server_Result {
    @SerializedName("result")
    @Expose
    private List<Result> result = new ArrayList<>();

    public Detail_SimilarDesign_Server_Result(String url, String serverIndex, String designNum, String registrationNum, String designCode, String country, String designName, String registerPerson, String dateApplication, String dateRegistration, String datePublication, String dep1, String dep2, String dep3, String dep4, String dep5) {
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }


    public class Result {

        public Result(String url, String serverIndex, String designNum, String registrationNum, String country, String designCode, String designName, String registerPerson, String dateApplication, String dateRegistration, String datePublication, String dep1, String dep2, String dep3, String dep4, String dep5) {
            this.url = url;
            this.serverIndex = serverIndex;
            this.designNum = designNum;
            this.registrationNum = registrationNum;
            this.country = country;
            this.designCode = designCode;
            this.designName = designName;
            this.registerPerson = registerPerson;
            this.dateApplication = dateApplication;
            this.dateRegistration = dateRegistration;
            this.datePublication = datePublication;
            this.dep1 = dep1;
            this.dep2 = dep2;
            this.dep3 = dep3;
            this.dep4 = dep4;
            this.dep5 = dep5;
        }

        @SerializedName("url")
        @Expose
        private String url;
        @SerializedName("serverIndex")
        @Expose
        private String serverIndex;
        @SerializedName("designNum")
        @Expose
        private String designNum;
        @SerializedName("registrationNum")
        @Expose
        private String registrationNum;
        @SerializedName("country")
        @Expose
        private String country;
        @SerializedName("designCode")
        @Expose
        private String designCode;
        @SerializedName("designName")
        @Expose
        private String designName;
        @SerializedName("registerPerson")
        @Expose
        private String registerPerson;
        @SerializedName("date_application")
        @Expose
        private String dateApplication;
        @SerializedName("date_registration")
        @Expose
        private String dateRegistration;
        @SerializedName("date_publication")
        @Expose
        private String datePublication;
        @SerializedName("dep1")
        @Expose
        private String dep1;
        @SerializedName("dep2")
        @Expose
        private String dep2;
        @SerializedName("dep3")
        @Expose
        private String dep3;
        @SerializedName("dep4")
        @Expose
        private String dep4;
        @SerializedName("dep5")
        @Expose
        private String dep5;


        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getServerIndex() {
            return serverIndex;
        }

        public void setServerIndex(String serverIndex) {
            this.serverIndex = serverIndex;
        }

        public String getDesignNum() {
            return designNum;
        }

        public void setDesignNum(String designNum) {
            this.designNum = designNum;
        }

        public String getRegistrationNum() {
            return registrationNum;
        }

        public void setRegistrationNum(String registrationNum) {
            this.registrationNum = registrationNum;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getDesignCode() {
            return designCode;
        }

        public void setDesignCode(String designCode) {
            this.designCode = designCode;
        }





        public String getDesignName() {
            return designName;
        }

        public void setDesignName(String designName) {
            this.designName = designName;
        }

        public String getRegisterPerson() {
            return registerPerson;
        }

        public void setRegisterPerson(String registerPerson) {
            this.registerPerson = registerPerson;
        }

        public String getDateApplication() {
            return dateApplication;
        }

        public void setDateApplication(String dateApplication) {
            this.dateApplication = dateApplication;
        }

        public String getDateRegistration() {
            return dateRegistration;
        }

        public void setDateRegistration(String dateRegisteration) {
            this.dateRegistration = dateRegisteration;
        }

        public String getDatePublication() {
            return datePublication;
        }

        public void setDatePublication(String datePublication) {
            this.datePublication = datePublication;
        }

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

        public String getDep4() {
            return dep4;
        }

        public void setDep4(String dep4) {
            this.dep4 = dep4;
        }

        public String getDep5() {
            return dep5;
        }

        public void setDep5(String dep5) {
            this.dep5 = dep5;
        }
    }


}
