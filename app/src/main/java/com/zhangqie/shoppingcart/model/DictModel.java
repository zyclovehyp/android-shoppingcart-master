package com.zhangqie.shoppingcart.model;

public class DictModel {

    private int id = -1;
    private String type;
    private String value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static String createTable() {


        return "create table dict_model (" +
                "id integer primary key autoincrement" +
                " ,type text" +
                " ,val text" +
                ")";
    }

    public static final String TABLE_NAME = "dict_model";
}
