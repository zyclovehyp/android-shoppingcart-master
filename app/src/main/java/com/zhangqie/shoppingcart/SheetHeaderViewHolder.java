package com.zhangqie.shoppingcart;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.zhangqie.shoppingcart.dao.SheetHeaderDao;
import com.zhangqie.shoppingcart.model.SheetHeader;
import com.zhangqie.shoppingcart.util.TimeUtils;
import com.zhangqie.shoppingcart.widget.ItemGroup;

public class SheetHeaderViewHolder implements View.OnClickListener {
    private static final String TAG = SheetHeaderViewHolder.class.getName();
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

    StartActivtiy startActivtiy;

    public SheetHeaderViewHolder(final Context context, final SheetHeader sheetHeader
            , View view) {

        this.context = context;
        this.content = view;
        ydno = findByID(R.id.ig_ydno);
        ydno.setText(sheetHeader.getSheetNo());

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
        type = findByID(R.id.sheet_type);
        other_edit = findByID(R.id.other_edit);

        fczlb.setText("");
        sz.setText("");
        qy.setText("");
        cfdd.setText("");
        lban.setText("");
        xban.setText("");
        zlnd.setText("");
        ybd.setText("");
        bzdmj.setText("");
        gps.setText("");
        person.setText("");
        ydmnum.setText("1");
        other_edit.setText("");
        type.setText("");

        ydno.getContentEdt().setInputType(InputType.TYPE_CLASS_NUMBER);
        lban.getContentEdt().setInputType(InputType.TYPE_CLASS_NUMBER);
        xban.getContentEdt().setInputType(InputType.TYPE_CLASS_NUMBER);
        ybd.getContentEdt().setInputType(InputType.TYPE_CLASS_NUMBER);

        bzdmj.getContentEdt().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sheetHeader.setSheetNo(ydno.getText());
                if (null != startActivtiy) {
                    startActivtiy.startActivity(sheetHeader);
                }
            }
        });

        this.sheetHeader = sheetHeader;
        dao = new SheetHeaderDao();
    }

    public void setView(SheetHeader sheetHeader) {
        this.sheetHeader = sheetHeader;
        ydno.setText(sheetHeader.getSheetNo());
        fczlb.setText(sheetHeader.getFcZLB());
        sz.setText(sheetHeader.getTreeType());
        qy.setText(sheetHeader.getSourceAddress());
        cfdd.setText(sheetHeader.getAddress());
        lban.setText(sheetHeader.getClassType());
        xban.setText(sheetHeader.getSmallType());
        zlnd.setText(sheetHeader.getBuildYear());
        ybd.setText(sheetHeader.getYbd());
        bzdmj.setText(sheetHeader.getMianJi());
        gps.setText(sheetHeader.getGps());
        ydmnum.setText(sheetHeader.getYdmnum());
        other_edit.setText(sheetHeader.getRemark());
        person.setText(sheetHeader.getPerson());

    }

    public void changeMianji(String mianji) {
        this.bzdmj.getContentEdt().setText(mianji);
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

    public void setStartActivtiy(StartActivtiy startActivtiy) {
        this.startActivtiy = startActivtiy;
    }

    public void save() {

        sheetHeader.setSheetNo(ydno.getText());


        sheetHeader.setFcZLB(fczlb.getText());
        sheetHeader.setTreeType(sz.getText());
        sheetHeader.setAddress(cfdd.getText());
        sheetHeader.setSourceAddress(qy.getText());
        sheetHeader.setClassType(lban.getText());
        sheetHeader.setSmallType(xban.getText());
        sheetHeader.setBuildYear(zlnd.getText());
        sheetHeader.setYbd(ybd.getText());
        sheetHeader.setMianJi(bzdmj.getText());
        sheetHeader.setGps(gps.getText());
        sheetHeader.setDate(date.getText());
        sheetHeader.setType(type.isChecked() ? "0" : "1");
        sheetHeader.setRemark(other_edit.getText().toString());
        sheetHeader.setPerson(person.getText());
        sheetHeader.setYdmnum(ydmnum.getText().toString());


        int id = 0;
        try {
            id = dao.save(sheetHeader);
            Toast.makeText(context, "Success", Toast.LENGTH_LONG).show();
        } catch (Exception e) {

            Toast.makeText(context, "Error:" + e.getMessage(), Toast.LENGTH_LONG).show();
            Log.i(TAG, "save: ", e);
            e.printStackTrace();
        }

        if (null != saveListener)
            saveListener.onSave(id);
    }

    public interface OnSaveListener {
        void onSave(int id);
    }

    public interface StartActivtiy {
        void startActivity(SheetHeader sheetHeader);
    }
}
