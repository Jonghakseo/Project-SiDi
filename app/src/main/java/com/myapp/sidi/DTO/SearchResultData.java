package com.myapp.sidi.DTO;

public class SearchResultData {
    private String url;
    private String serverIndex;
    private String designNum;
    private String registrationNum;
    private String country;
    private String designCode;
    private String designName;
    private String registerPerson;
    private String date_application;
    private String date_registration;
    private String date_publication;
    private String dep_1;
    private String dep_2;
    private String dep_3;
    private String dep_4;
    private String dep_5;

    public SearchResultData(String url,String serverIndex, String designNum,String registrationNum,String designCode,String country, String designName, String registerPerson, String date_application, String date_registration, String date_publication, String dep_1, String dep_2, String dep_3, String dep_4, String dep_5) {
        this.url = url;
        this.serverIndex = serverIndex;
        this.designNum = designNum;
        this.registrationNum = registrationNum;
        this.country = country;
        this.designCode = designCode;
        this.designName = designName;
        this.registerPerson = registerPerson;
        this.date_application = date_application;
        this.date_registration = date_registration;
        this.date_publication = date_publication;
        this.dep_1 = dep_1;
        this.dep_2 = dep_2;
        this.dep_3 = dep_3;
        this.dep_4 = dep_4;
        this.dep_5 = dep_5;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

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

    public String getDate_application() {
        return date_application;
    }

    public void setDate_application(String date_application) {
        this.date_application = date_application;
    }

    public String getDate_registration() {
        return date_registration;
    }

    public void setDate_registration(String date_registration) {
        this.date_registration = date_registration;
    }

    public String getDate_publication() {
        return date_publication;
    }

    public void setDate_publication(String date_publication) {
        this.date_publication = date_publication;
    }

    public String getDep_1() {
        return dep_1;
    }

    public void setDep_1(String dep_1) {
        this.dep_1 = dep_1;
    }

    public String getDep_2() {
        return dep_2;
    }

    public void setDep_2(String dep_2) {
        this.dep_2 = dep_2;
    }

    public String getDep_3() {
        return dep_3;
    }

    public void setDep_3(String dep_3) {
        this.dep_3 = dep_3;
    }

    public String getDep_4() {
        return dep_4;
    }

    public void setDep_4(String dep_4) {
        this.dep_4 = dep_4;
    }

    public String getDep_5() {
        return dep_5;
    }

    public void setDep_5(String dep_5) {
        this.dep_5 = dep_5;
    }


}
