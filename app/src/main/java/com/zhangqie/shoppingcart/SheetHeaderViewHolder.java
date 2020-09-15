package com.zhangqie.shoppingcart;

import android.content.ContentValues;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import com.zhangqie.shoppingcart.dao.SheetHeaderDao;
import com.zhangqie.shoppingcart.model.SheetHeader;
import com.zhangqie.shoppingcart.util.TimeUtils;
import com.zhangqie.shoppingcart.widget.ItemGroup;

public class SheetHeaderViewHolder implements View.OnClickListener {
    Context context;
    SheetHeader sheetHeader;
    OnSaveListener saveListener;
    SheetHeaderDao dao;
    View content;
    ItemGroup ydno,
            fczlb,
            sz,
            qy,
            cfdd,
            lban,
            xban,
            zlnd,
            ybd,
            bzdmj,
            gps,
            person,
            date;
    Button ydmnum, save_btn;
    EditText other_edit;
    Switch type;

    public SheetHeaderViewHolder(Context context, SheetHeader sheetHeader
            , View view) {

        this.context = context;
        this.content = view;

        ydno = findByID(R.id.ig_ydno);
        ydno.setText(Application.bizDao.getBizNo());
        fczlb = findByID(R.id.ig_fczlb);
        sz = findByID(R.id.ig_sz);
        qy = findByID(R.id.ig_qy);
        cfdd = findByID(R.id.ig_cfdd);
        lban = findByID(R.id.ig_lban);
        xban = findByID(R.id.ig_xban);
        zlnd = findByID(R.id.ig_zlnd);
        ybd = findByID(R.id.ig_ybd);
        bzdmj = findByID(R.id.ig_bzdmj);
        gps = findByID(R.id.ig_gps);
        person = findByID(R.id.ig_person);
        date = findByID(R.id.ig_date);
        date.setText(TimeUtils.getTime());
        ydmnum = findByID(R.id.ydmnum);
        save_btn = findByID(R.id.save_btn);
        save_btn.setOnClickListener(this);
        other_edit = findByID(R.id.other_edit);
        type = findByID(R.id.sheet_type);
        this.sheetHeader = sheetHeader;
        dao = new SheetHeaderDao();
    }

    public void setSaveListener(OnSaveListener saveListener) {
        this.saveListener = saveListener;
    }

    private <T extends View> T findByID(int id) {

        return this.content.findViewById(id);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save_btn:
                save();
                break;
        }
    }

    public void save() {

        ContentValues values = new ContentValues();


        values.put("sheet_no", ydno.getText());
        values.put("fc_zlb", fczlb.getText());
        values.put("treeType", sz.getText());
        values.put("address", cfdd.getText());
        values.put("sourceAddress", qy.getText());
        values.put("classType", lban.getText());
        values.put("smallType", xban.getText());
        values.put("buildYear", zlnd.getText());
        values.put("ybd", ybd.getText());
        values.put("mianJi", bzdmj.getText());
        values.put("gps", gps.getText());
        values.put("date",date.getText());
        values.put("type", type.isChecked());
        int id = dao.save(values);

        sheetHeader.setSheetId(id);
        if (null != saveListener)
            saveListener.onSave(id);
    }

    public interface OnSaveListener {
        void onSave(int id);
    }
}
