package com.zhangqie.shoppingcart.model;

import java.util.List;

public class SheetHeader {

    private int sheetId;


    private String sheetNo;//样带编号

    /*分场(造林部)*/
    private String fcZLB;

    /*树    种*/
    private String treeType;

    /*采伐地点*/
    private String address;

    /*起源*/
    private String sourceAddress;

    /*林班*/
    private String classType;

    /*小班*/
    private String smallType;

    /*造林年度*/
    private String buildYear;

    /*郁闭度*/
    private String ybd;

    /*标准地面积*/
    private String mianJi;

    /*GPS*/
    private String gps;

    private List<TreeModel> allData;

    public int getSheetId() {
        return sheetId;
    }

    public void setSheetId(int sheetId) {
        this.sheetId = sheetId;
    }

    public String getSheetNo() {
        return sheetNo;
    }

    public void setSheetNo(String sheetNo) {
        this.sheetNo = sheetNo;
    }

    public String getFcZLB() {
        return fcZLB;
    }

    public void setFcZLB(String fcZLB) {
        this.fcZLB = fcZLB;
    }

    public String getTreeType() {
        return treeType;
    }

    public void setTreeType(String treeType) {
        this.treeType = treeType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSourceAddress() {
        return sourceAddress;
    }

    public void setSourceAddress(String sourceAddress) {
        this.sourceAddress = sourceAddress;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getSmallType() {
        return smallType;
    }

    public void setSmallType(String smallType) {
        this.smallType = smallType;
    }

    public String getBuildYear() {
        return buildYear;
    }

    public void setBuildYear(String buildYear) {
        this.buildYear = buildYear;
    }

    public String getYbd() {
        return ybd;
    }

    public void setYbd(String ybd) {
        this.ybd = ybd;
    }

    public String getMianJi() {
        return mianJi;
    }

    public void setMianJi(String mianJi) {
        this.mianJi = mianJi;
    }

    public String getGps() {
        return gps;
    }

    public void setGps(String gps) {
        this.gps = gps;
    }

    public List<TreeModel> getAllData() {
        return allData;
    }

    public void setAllData(List<TreeModel> allData) {
        this.allData = allData;
    }
}
