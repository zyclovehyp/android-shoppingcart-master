package com.zhangqie.shoppingcart.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.zhangqie.shoppingcart.Application;
import com.zhangqie.shoppingcart.model.AreaModel;
import com.zhangqie.shoppingcart.model.SheetHeader;
import com.zhangqie.shoppingcart.model.TreeModel;

import java.util.ArrayList;
import java.util.List;

public class SheetHeaderDao {


    public int save(SheetHeader sheetHeader) throws Exception {
        ContentValues values = new ContentValues();
        values.put("sheet_id", sheetHeader.getSheetId());
        values.put("sheet_no", sheetHeader.getSheetNo());
        values.put("fc_zlb", sheetHeader.getFcZLB());
        values.put("treeType", sheetHeader.getTreeType());
        values.put("address", sheetHeader.getAddress());
        values.put("sourceAddress", sheetHeader.getSourceAddress());
        values.put("classType", sheetHeader.getClassType());
        values.put("smallType", sheetHeader.getSmallType());
        values.put("buildYear", sheetHeader.getBuildYear());
        values.put("ybd", sheetHeader.getYbd());
        values.put("mianJi", sheetHeader.getMianJi());
        values.put("gps", sheetHeader.getGps());
        values.put("date", sheetHeader.getDate());
        values.put("type", sheetHeader.getType());

        values.put("ydmnum", sheetHeader.getYdmnum());
        values.put("remark", sheetHeader.getRemark());
        values.put("person", sheetHeader.getPerson());
        if (-1 == sheetHeader.getSheetId()) {
            values.remove("sheet_id");
            Application.db.insert(SheetHeader.TABLE_NAME, values);

            Cursor row = Application.db.query(SheetHeader.TABLE_NAME, "where  rowid = last_insert_rowid()");
            if (row.moveToNext()) {
                sheetHeader.setSheetId(row.getInt(row.getColumnIndex("sheet_id")));
                return sheetHeader.getSheetId();
            }
        } else {
            Application.db.update(SheetHeader.TABLE_NAME, values, "sheet_id=?", new String[]{String.valueOf(values.get("sheet_id"))});
            return values.getAsInteger("sheet_id");
        }


        return 0;
    }

    public List<SheetHeader> list() {
        List<SheetHeader> list = new ArrayList<>();
        Cursor cursor = Application.db.query(SheetHeader.TABLE_NAME, "");

        while (cursor.moveToNext()) {
            SheetHeader sheetHeader = new SheetHeader();
            sheetHeader.setSheetId(cursor.getInt(cursor.getColumnIndex("sheet_id")));
            sheetHeader.setGps(cursor.getString(cursor.getColumnIndex("gps")));
            sheetHeader.setSheetNo(cursor.getString(cursor.getColumnIndex("sheet_no")));
            sheetHeader.setFcZLB(cursor.getString(cursor.getColumnIndex("fc_zlb")));
            sheetHeader.setTreeType(cursor.getString(cursor.getColumnIndex("treeType")));
            sheetHeader.setAddress(cursor.getString(cursor.getColumnIndex("address")));
            sheetHeader.setSourceAddress(cursor.getString(cursor.getColumnIndex("sourceAddress")));
            sheetHeader.setClassType(cursor.getString(cursor.getColumnIndex("classType")));
            sheetHeader.setSmallType(cursor.getString(cursor.getColumnIndex("smallType")));
            sheetHeader.setBuildYear(cursor.getString(cursor.getColumnIndex("buildYear")));
            sheetHeader.setYbd(cursor.getString(cursor.getColumnIndex("ybd")));
            sheetHeader.setMianJi(cursor.getString(cursor.getColumnIndex("mianJi")));
            sheetHeader.setDate(cursor.getString(cursor.getColumnIndex("date")));
            sheetHeader.setType(cursor.getString(cursor.getColumnIndex("type")));
            sheetHeader.setYdmnum(cursor.getString(cursor.getColumnIndex("ydmnum")));
            sheetHeader.setRemark(cursor.getString(cursor.getColumnIndex("remark")));
            sheetHeader.setPerson(cursor.getString(cursor.getColumnIndex("person")));
            list.add(sheetHeader);
        }


        return list;

    }

    public void delete(SheetHeader sheetHeader) {
        Application.db.delete(SheetHeader.TABLE_NAME, "sheet_id=?", new String[]{"" + sheetHeader.getSheetId()});
        Application.db.delete(AreaModel.TABLE_NAME, "sheet_id=?", new String[]{"" + sheetHeader.getSheetId()});
        Application.db.delete(TreeModel.TABLE_NAME, "sheet_id=?", new String[]{"" + sheetHeader.getSheetId()});
    }


}
