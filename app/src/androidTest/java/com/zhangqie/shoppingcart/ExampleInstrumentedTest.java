package com.zhangqie.shoppingcart;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.zhangqie.shoppingcart.util.DataBaseOpenHelper;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private static final String TAG = "ExampleInstrumentedTest";
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.zhangqie.shoppingcart", appContext.getPackageName());
    }

    //    private static final String SQL_CREATE = "create table student_info(_id integer primary key autoincrement,student_id integer,name text,age integer,teacher text,address text,phone long)";
    @Test
    public void dbTest() {
        Context appContext = InstrumentationRegistry.getTargetContext();

        List<String> initSql = new ArrayList<>();
        String sheetHeader = "create table sheet_header (id integer primary key autoincrement,sheet_code text,create_date datetime,remark text)";
        initSql.add(sheetHeader);
        DataBaseOpenHelper instance = DataBaseOpenHelper.getInstance(appContext, "tree.db", 1, initSql);
        ContentValues values = new ContentValues();
        values.put("sheet_code", "abcd123");
        values.put("create_date", "2020-09-16");
        values.put("remark", "remark....");
        instance.insert("sheet_header", values);

        Cursor cursor = instance.query("sheet_header", "");

        System.out.println("begin..........");
        Log.i(TAG, "dbTest:begin.............");
        String sheetCode= "";
        while (cursor.moveToNext()) {

            sheetCode = cursor.getString(cursor.getColumnIndex("sheet_code"));
            System.out.println(cursor.getInt(cursor.getColumnIndex("id")));
            System.out.println(cursor.getString(cursor.getColumnIndex("sheet_code")));
            System.out.println(cursor.getString(cursor.getColumnIndex("create_date")));
            System.out.println(cursor.getString(cursor.getColumnIndex("remark")));

        }
        Log.i(TAG, "dbTest: end.............");
        System.out.println("end..........");
        assertEquals("abcd123",sheetCode);
    }
}
