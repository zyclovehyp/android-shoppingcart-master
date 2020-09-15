package com.zhangqie.shoppingcart.model;

import java.io.Serializable;
import java.util.List;

public class SheetHeader implements Serializable {


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

    private String date;

    private String type;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private List<TreeModel> allData;

    private boolean isCheck;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public void setAllData(List<TreeModel> allData) {
        this.allData = allData;
    }

    public static String createTable() {
        return "create table sheet_header (" +
                "sheet_id integer primary key autoincrement" +
                ", sheet_no text" +
                ", fc_zlb text" +
                ", treeType text" +
                ", address text" +
                ", sourceAddress" +
                ", classType text" +
                ", smallType text" +
                ", buildYear text" +
                ", ybd text" +
                ", mianJi text" +
                ", date text" +
                ", gps text,type text)";
    }

    public static final String TABLE_NAME = "sheet_header";

}
