package com.zhangqie.shoppingcart.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.zhangqie.shoppingcart.Application;
import com.zhangqie.shoppingcart.model.SheetHeader;

import java.util.ArrayList;
import java.util.List;

public class SheetHeaderDao {


    public int save(ContentValues contentValues) {

        Application.db.insert(SheetHeader.TABLE_NAME, contentValues);

        //select * from TABLE_NAME where rowid = last_insert_rowid() ;

        Cursor row = Application.db.query(SheetHeader.TABLE_NAME, "where  rowid = last_insert_rowid()");
        while (row.moveToNext()) {

            return row.getInt(row.getColumnIndex("sheet_id"));
        }


        return 0;
    }

    public List<SheetHeader> list(){
        List<SheetHeader> list = new ArrayList<>();
        Cursor cursor = Application.db.query(SheetHeader.TABLE_NAME,"");

        while (cursor.moveToNext()){
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
            list.add(sheetHeader);
        }


        return list;

    }


}
