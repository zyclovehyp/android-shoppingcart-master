package com.zhangqie.shoppingcart.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.zhangqie.shoppingcart.Application;
import com.zhangqie.shoppingcart.util.TimeUtils;

public class BizDao {


    public  String getBizNo() {
        String date = String.format("%d%d%d", TimeUtils.getYear(), TimeUtils.getMonth(),
                TimeUtils.getDay());
        Cursor cursor =
                Application.db.query("biz_id_table", "where biz_name='TreeHeader'");

        int sno = 0;
        if (cursor.moveToNext()) {
            String currDate = cursor.getString(cursor.getColumnIndex("currDate"));
            sno = cursor.getInt(cursor.getColumnIndex("sno"));
            if (date.equals(currDate)) {
                sno = sno + 1;
            } else {
                sno = 1;
            }
            ContentValues contentValues = new ContentValues();
            contentValues.put("sno", sno);
            Application.db.update("biz_id_table", contentValues,
                    "id=?", new String[]{cursor.getInt(cursor.getColumnIndex("id")) + ""});

        } else {
            ContentValues contentValues = new ContentValues();
            sno = 1;
            contentValues.put("currDate", date);
            contentValues.put("biz_name", "TreeHeader");
            contentValues.put("sno", 1);
            Application.db.insert("biz_id_table", contentValues);
        }
        //String.format("%5d", 1).replace(" ", "0");
        date = date + String.format("%4d", sno).replace(" ", "0");
        return date;
    }
}
