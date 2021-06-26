package com.aoslec.honey_all.Bean;

public class IngredientBean {
    String iCode;
    String mCode;
    String iName;
    String iCapacity;
    String iUnit;
    String iPrice;
    boolean isSelected;

    public IngredientBean(boolean isSelected, String iCode, String iName, String iCapacity, String iUnit, String iPrice) {
        this.iCode = iCode;
        this.iName = iName;
        this.iCapacity = iCapacity;
        this.iUnit = iUnit;
        this.iPrice = iPrice;
    }

    public String getiCode() {
        return iCode;
    }

    public void setiCode(String iCode) {
        this.iCode = iCode;
    }

    public String getmCode() {
        return mCode;
    }

    public void setmCode(String mCode) {
        this.mCode = mCode;
    }

    public String getiName() {
        return iName;
    }

    public void setiName(String iName) {
        this.iName = iName;
    }

    public String getiCapacity() {
        return iCapacity;
    }

    public void setiCapacity(String iCapacity) {
        this.iCapacity = iCapacity;
    }

    public String getiUnit() {
        return iUnit;
    }

    public void setiUnit(String iUnit) {
        this.iUnit = iUnit;
    }

    public String getiPrice() {

        return iPrice;
    }

    public void setiPrice(String iPrice) {

        this.iPrice = iPrice;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
