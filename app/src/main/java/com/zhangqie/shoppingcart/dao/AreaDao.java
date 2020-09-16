package com.zhangqie.shoppingcart.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.zhangqie.shoppingcart.Application;
import com.zhangqie.shoppingcart.model.AreaModel;

import java.util.ArrayList;
import java.util.List;

public class AreaDao {

    private static final String TAG = "AreaDao";


    public int save(AreaModel areaModel) throws Exception {

        ContentValues contentValues = new ContentValues();

        contentValues.put("id", areaModel.getId());
        contentValues.put("sheet_id", areaModel.getSheetId());
        contentValues.put("height", areaModel.getHeight());
        contentValues.put("width", areaModel.getWidth());
        contentValues.put("p", areaModel.getP());
        contentValues.put("percent", areaModel.getPercent());
        contentValues.put("atan", areaModel.getAtan());
        contentValues.put("cos", areaModel.getCos());
        contentValues.put("away", areaModel.getAway());
        contentValues.put("area", areaModel.getArea());
        contentValues.put("sheet_no",areaModel.getSheetNo());

        if (-1 == areaModel.getId()) {
            contentValues.remove("id");
            Application.db.insert(AreaModel.TABLE_NAME, contentValues);
            Cursor row = Application.db.query(AreaModel.TABLE_NAME, "where  rowid = last_insert_rowid()");
            if (row.moveToNext()) {
                areaModel.setId(row.getInt(row.getColumnIndex("id")));
                return areaModel.getId();
            }
        }
        Cursor cursor =
                Application.db.query(AreaModel.TABLE_NAME, "where id = " + areaModel.getId());
        if (cursor.moveToNext()) {
            //update
            Application.db.update(AreaModel.TABLE_NAME, contentValues, "id=?", new String[]{
                    areaModel.getId() + ""
            });
            return areaModel.getId();
        }
        return 0;
    }

    public List<AreaModel> list(String sheet_id,String sheet_no) {


        List<AreaModel> list = new ArrayList<>();

        try {

            Cursor cursor = Application.db.query(AreaModel.TABLE_NAME, " where sheet_id = " + sheet_id + " or sheet_no = '"+sheet_no+"'");
            while (cursor.moveToNext()) {
                AreaModel treeModel = new AreaModel();

                treeModel.setId(cursor.getInt(cursor.getColumnIndex("id")));
                treeModel.setSheetId(cursor.getInt(cursor.getColumnIndex("sheet_id")));

                treeModel.setHeight(cursor.getDouble(cursor.getColumnIndex("height")));
                treeModel.setWidth(cursor.getDouble(cursor.getColumnIndex("width")));
                treeModel.setP(cursor.getDouble(cursor.getColumnIndex("p")));
                treeModel.getP();


                list.add(treeModel);
            }
        } catch (Exception e) {
            Log.i(TAG, "list: ", e);
        }
        return list;
    }


}
