package com.zhangqie.shoppingcart;

import com.zhangqie.shoppingcart.dao.BizDao;
import com.zhangqie.shoppingcart.model.AreaModel;
import com.zhangqie.shoppingcart.model.SheetHeader;
import com.zhangqie.shoppingcart.model.TreeModel;
import com.zhangqie.shoppingcart.util.DataBaseOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class Application extends android.app.Application {

    public static DataBaseOpenHelper db = null;

    public static BizDao bizDao;
    @Override
    public void onCreate() {
        super.onCreate();

        init();
    }

    private void init() {

        List<String> all = new ArrayList<>();
        all.add(SheetHeader.createTable());
        all.add(TreeModel.createTable());
        all.add(AreaModel.createTable());
        all.add("create table biz_id_table (id integer primary key autoincrement,biz_name text,currDate text,sno integer)");

        db = DataBaseOpenHelper.getInstance(this, "tree.db"
                , 5, all);

        bizDao = new BizDao();
    }
}
