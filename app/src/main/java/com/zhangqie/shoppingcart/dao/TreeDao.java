package com.zhangqie.shoppingcart.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.zhangqie.shoppingcart.Application;
import com.zhangqie.shoppingcart.model.TreeModel;

import java.util.ArrayList;
import java.util.List;

public class TreeDao {

    private static final String TAG = "TreeDao";

    public int save(TreeModel treeModel) {
        try {
            ContentValues contentValues = new ContentValues();

            contentValues.put("id", treeModel.getId());
            contentValues.put("sheet_id", treeModel.getSheetId());
            contentValues.put("jin_jie", treeModel.getJinJie());
            contentValues.put("num", treeModel.getNum());
            contentValues.put("test_hight1", treeModel.getTestHight1());
            contentValues.put("test_hight2", treeModel.getTestHight2());
            contentValues.put("test_hight3", treeModel.getTestHight3());
            contentValues.put("test_width1", treeModel.getTestWidth1());
            contentValues.put("test_width2", treeModel.getTestWidth2());
            contentValues.put("test_width3", treeModel.getTestWidth3());

            if (treeModel.getId() == -1) {
                contentValues.remove("id");
            }
            Cursor cursor =
                    Application.db.query(TreeModel.TABLE_NAME, "where id = " + treeModel.getId());
            if (cursor.moveToNext()) {
                //update
                Application.db.update(TreeModel.TABLE_NAME, contentValues, "id=?", new String[]{
                        treeModel.getId() + ""
                });
                return treeModel.getId();
            }
            Application.db.insert(TreeModel.TABLE_NAME, contentValues);
            Cursor row = Application.db.query(TreeModel.TABLE_NAME, "where  rowid = last_insert_rowid()");
            if (row.moveToNext()) {
                treeModel.setId(row.getInt(row.getColumnIndex("id")));
                return treeModel.getId();
            }
        } catch (Exception e) {
            Log.i(TAG, "save: ", e);
        }


        return 0;

    }

    public List<TreeModel> list(String sheet_id) {


        List<TreeModel> list = new ArrayList<>();

        try {

            Cursor cursor = Application.db.query(TreeModel.TABLE_NAME, " where sheet_id = " + sheet_id);
            while (cursor.moveToNext()) {
                TreeModel treeModel = new TreeModel();

                treeModel.setId(cursor.getInt(cursor.getColumnIndex("id")));
                treeModel.setSheetId(cursor.getInt(cursor.getColumnIndex("sheet_id")));
                treeModel.setJinJie(cursor.getString(cursor.getColumnIndex("jin_jie")));
                treeModel.setNum(cursor.getInt(cursor.getColumnIndex("num")));
                treeModel.setTestHight1(cursor.getString(cursor.getColumnIndex("test_hight1")));
                treeModel.setTestHight2(cursor.getString(cursor.getColumnIndex("test_hight2")));
                treeModel.setTestHight3(cursor.getString(cursor.getColumnIndex("test_hight3")));

                treeModel.setTestWidth1(cursor.getString(cursor.getColumnIndex("test_width1")));
                treeModel.setTestWidth2(cursor.getString(cursor.getColumnIndex("test_width2")));
                treeModel.setTestWidth3(cursor.getString(cursor.getColumnIndex("test_width3")));


                list.add(treeModel);
            }
        } catch (Exception e) {
            Log.i(TAG, "list: ", e);
        }
        return list;
    }
}
