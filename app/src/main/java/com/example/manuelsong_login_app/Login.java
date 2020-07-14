package com.example.manuelsong_login_app;
//Manuel Song 110116811
//I used all the template, frame , and methods from textbook
public class Login {
    private int loginID;
    private String websiteName;
    private String identification;
    private String password;
    private String importance;
    private String frequency;



    public Login() {
        loginID = -1;
        frequency="0";
    }

    public int getLoginID() {
        return loginID;
    }
    public void setLoginID(int i) {
        loginID = i;
    }
    public String getWebsiteName() {
        return websiteName;
    }
    public void setWebsiteName(String s) {
        websiteName = s;
    }
    public String getIdentification() {
        return identification;
    }
    public void setIdentification(String s) {
        identification = s;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String s) {
        password = s;
    }
    public String getImportance() {
        return importance;
    }
    public void setImportance(String s) {
        importance = s;
    }
    public String getFrequency() {
        return frequency;
    }
    public void setFrequency(String s) {
        frequency = s;
    }

}
