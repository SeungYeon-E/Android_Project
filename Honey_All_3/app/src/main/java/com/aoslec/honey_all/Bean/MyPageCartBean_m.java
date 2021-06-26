package com.aoslec.honey_all.Bean;

//0625 해당 페이지 추가함
public class MyPageCartBean_m {
    private String client_cId, mName, mCode, tipContent;

    public MyPageCartBean_m(String client_cId, String mCode, String mName, String tipContent) {
        this.client_cId = client_cId;
        this.mName = mName;
        this.mCode = mCode;
        this.tipContent = tipContent;
    }

    public String getClient_cId() {
        return client_cId;
    }

    public void setClient_cId(String client_cId) {
        this.client_cId = client_cId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmCode() {
        return mCode;
    }

    public void setmCode(String mCode) {
        this.mCode = mCode;
    }

    public String getTipContent() {
        return tipContent;
    }

    public void setTipContent(String tipContent) {
        this.tipContent = tipContent;
    }
}
