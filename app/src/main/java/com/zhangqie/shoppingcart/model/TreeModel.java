package com.zhangqie.shoppingcart.model;

public class TreeModel {

    private int sheetId;

    private int id;
    private String jinJie;//径阶

    private int num;//株数

    private String testHight1;//实测数据
    private String testHight2;
    private String testHight3;

    private String testWidth1;
    private String testWidth2;
    private String testWidth3;

    private boolean hasMoved;
    private int downX;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSheetId() {
        return sheetId;
    }

    public void setSheetId(int sheetId) {
        this.sheetId = sheetId;
    }

    public String getJinJie() {
        return jinJie;
    }

    public void setJinJie(String jinJie) {
        this.jinJie = jinJie;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getTestHight1() {
        return testHight1;
    }

    public void setTestHight1(String testHight1) {
        this.testHight1 = testHight1;
    }

    public String getTestHight2() {
        return testHight2;
    }

    public void setTestHight2(String testHight2) {
        this.testHight2 = testHight2;
    }

    public String getTestHight3() {
        return testHight3;
    }

    public void setTestHight3(String testHight3) {
        this.testHight3 = testHight3;
    }

    public String getTestWidth1() {
        return testWidth1;
    }

    public void setTestWidth1(String testWidth1) {
        this.testWidth1 = testWidth1;
    }

    public String getTestWidth2() {
        return testWidth2;
    }

    public void setTestWidth2(String testWidth2) {
        this.testWidth2 = testWidth2;
    }

    public String getTestWidth3() {
        return testWidth3;
    }

    public void setTestWidth3(String testWidth3) {
        this.testWidth3 = testWidth3;
    }

    public boolean isHasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public int getDownX() {
        return downX;
    }

    public void setDownX(int downX) {
        this.downX = downX;
    }

    public static String createTable() {
        return "create table tree_model (" +
                "id integer primary key autoincrement,sheet_id integer" +
                ", jin_jie text" +
                ", num integer" +
                ", test_hight1 text" +
                ", test_hight2 text" +
                ", test_hight3 text" +
                ", test_width1 text" +
                ", test_width2 text" +
                ", test_width3 text)";
    }

    public static final String TABLE_NAME = "tree_model";
}
