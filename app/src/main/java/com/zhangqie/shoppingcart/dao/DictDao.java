package com.zhangqie.shoppingcart.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.zhangqie.shoppingcart.Application;
import com.zhangqie.shoppingcart.model.DictModel;

import java.util.ArrayList;
import java.util.List;

public class DictDao {


    public int save(DictModel dictModel) {

        ContentValues values = new ContentValues();
        values.put("id", dictModel.getId());
        values.put("type", dictModel.getType());
        values.put("val", dictModel.getValue());

        if (-1 == dictModel.getId()) {
            values.remove("id");
            //新增
            Application.db.insert(DictModel.TABLE_NAME, values);
            Cursor row = Application.db.query(DictModel.TABLE_NAME, "where  rowid = last_insert_rowid()");
            if (row.moveToNext()) {
                dictModel.setId(row.getInt(row.getColumnIndex("id")));
                return dictModel.getId();
            }
        }

        Application.db.update(DictModel.TABLE_NAME, values, "id=?", new String[]{String.valueOf(dictModel.getId())});

        return dictModel.getId();
    }

    public void insertInitData() {
        DictModel dictModel;
        String[] types = new String[]{"分场造林部", "起源", "造林年度", "调查人员", "树种"};
        String[] address = new String[]{
                "东山",
                "高岭",
                "河嵩",
                "宁康",
                "忠东",
                "田林",
                "百色",
                "南宁",
                "平南",
                "藤县",
                "贺州",
                "北流",
                "桂平",
                "兴业",
                "陆川",
                "罗城"

        };

        for (String s : address) {
            dictModel = new DictModel();
            dictModel.setType(types[0]);
            dictModel.setValue(s);
            this.save(dictModel);
        }
        String[] qy = new String[]{
                "植苗",
                "萌芽"
        };
        for (String s : qy) {
            dictModel = new DictModel();
            dictModel.setType(types[1]);
            dictModel.setValue(s);
            this.save(dictModel);
        }
        String[] years = new String[]{
                "2014",
                "2013",
                "2012",
                "2011",
                "2010",
                "2009",
                "2008",
                "2015",
                "2016",
                "2017",
                "2018",
                "2019",
                "2020"
        };
        for (String s : years) {
            dictModel = new DictModel();
            dictModel.setType(types[2]);
            dictModel.setValue(s);
            this.save(dictModel);
        }
        String[] persons = new String[]{
                "张三",
                "李四",
                "王五"
        };
        for (String s : persons) {
            dictModel = new DictModel();
            dictModel.setType(types[3]);
            dictModel.setValue(s);
            this.save(dictModel);
        }


        String[] treeTypes = new String[]{
                "八角", "马尾松", "杉木"
        };
        for (String s : treeTypes) {
            dictModel = new DictModel();
            dictModel.setType(types[4]);
            dictModel.setValue(s);
            this.save(dictModel);
        }

    }

    public List<DictModel> list(String type) {
        List<DictModel> list = new
                ArrayList<>();
        Cursor cursor = Application.db.query(DictModel.TABLE_NAME, "where type='" + type + "'");
        while (cursor.moveToNext()) {
            DictModel dictModel = new DictModel();
            dictModel.setId(cursor.getInt(cursor.getColumnIndex("id")));
            dictModel.setType(cursor.getString(cursor.getColumnIndex("type")));
            dictModel.setValue(cursor.getString(cursor.getColumnIndex("val")));
            list.add(dictModel);
        }
        return list;

    }

    public void delete(String val) {

        Application.db.delete(DictModel.TABLE_NAME, "val=?", new String[]{val});


    }


}
