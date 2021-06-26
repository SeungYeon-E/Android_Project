package com.aoslec.honey_all.Bean;

public class MyPageBean {

    private String userPw;
    private String userName;
    private String userTel;
    private String UserEmail;

    public MyPageBean(String userPw, String userName, String userTel, String userEmail) {
        this.userPw = userPw;
        this.userName = userName;
        this.userTel = userTel;
        UserEmail = userEmail;
    }

    public String getUserPw() {
        return userPw;
    }

    public void setUserPw(String userPw) {
        this.userPw = userPw;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }
}
