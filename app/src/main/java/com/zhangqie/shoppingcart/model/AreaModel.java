package com.zhangqie.shoppingcart.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class AreaModel implements Serializable {


    private int id = -1;

    private int sheetId;

    private String sheetNo;

    /**
     * 样带长m	带宽m	坡度。	Percent/100
     * ATAN(a)	COS(a)	水平距离m	样带面积m2
     */
    private double height;//长

    private double width = 10;//宽

    private double p;//坡度

    private double percent;//percent

    private double atan;//atan

    private double cos;//cos

    private double away;//水平距离

    private double area;//面积


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

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getP() {
        return p;
    }

    public void setP(double p) {
        this.p = p;
        getPercent();
        getAtan();
        getCos();
        getAway();
        getArea();

    }


    public double getPercent() {
        double val = Math.tan(Math.toRadians(getP()));

        this.percent = new BigDecimal(val).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();

        return percent;
    }

    public double getAtan() {
        double val = Math.atan(Math.tan(Math.toRadians(getP())));

        this.atan = new BigDecimal(val).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
        return atan;
    }

    public double getCos() {
        double val = Math.cos(Math.atan(Math.tan(Math.toRadians(getP()))));
        this.cos = new BigDecimal(val).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
        return cos;
    }

    public double getAway() {
        double val = Math.cos(Math.atan(Math.tan(Math.toRadians(getP())))) * getHeight();
        this.away = new BigDecimal(val).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
        return away;
    }

    public double getArea() {
        double val = Math.cos(Math.atan(Math.tan(Math.toRadians(getP())))) * getHeight() * getWidth() * 1;

        this.area = new BigDecimal(val).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();

        return area;
    }

    public String getSheetNo() {
        return sheetNo;
    }

    public void setSheetNo(String sheetNo) {
        this.sheetNo = sheetNo;
    }

    public static String createTable() {


        return "create table area_model (" +
                "id integer primary key autoincrement,sheet_id integer,sheet_no text" +
                ", height text" +
                ", width text" +
                ", p text" +
                ", percent text" +
                ", atan text" +
                ", cos text" +
                ", away text" +
                ", area text)";
    }

    public static final String TABLE_NAME = "area_model";
}
