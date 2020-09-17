package com.zhangqie.shoppingcart;

import android.app.Activity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.zhangqie.shoppingcart.dao.DictDao;
import com.zhangqie.shoppingcart.dao.SheetHeaderDao;
import com.zhangqie.shoppingcart.model.DictModel;
import com.zhangqie.shoppingcart.model.SheetHeader;
import com.zhangqie.shoppingcart.util.TimeUtils;
import com.zhangqie.shoppingcart.widget.ItemGroup;
import com.zyc.popwindow.bean.FiltrateBean;
import com.zyc.popwindow.view.ScreenPopWindow;
import com.zyc.popwindow.view.adapter.ScreenListViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class SheetHeaderViewHolder implements View.OnClickListener {
    private static final String TAG = SheetHeaderViewHolder.class.getName();
    Activity context;
    SheetHeader sheetHeader;
    OnSaveListener saveListener;
    SheetHeaderDao dao;
    DictDao dictDao;
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

    public SheetHeaderViewHolder(final Activity context, final SheetHeader sheetHeader
            , View view) {

        dictDao = new DictDao();
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
        sz.setText("按树");
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
        ybd.getContentEdt().setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        initPopWindow(context);
        /*person.getContentEdt().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/
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

    private void initPopWindow(final Activity context) {
        person.getJtRightIv().setImageResource(R.mipmap.country_selecter);
        person.setItemOnClickListener(new ItemGroup.ItemOnClickListener() {
            @Override
            public void onItemClick(final View v) {
                initParam();
                screenPopWindow = new ScreenPopWindow(context, personDictList);
                screenPopWindow.getAdapter().setRightBtnClick(new ScreenListViewAdapter.OnRightBtnClick() {
                    @Override
                    public void onClick(EditText view, FiltrateBean filtrateBean) {

                        if ("".equals(String.valueOf(view.getText()))) {
                            return;
                        }
                        List<FiltrateBean.Children> children =
                                filtrateBean.getChildren();
                        for (FiltrateBean.Children child : children) {
                            if (child.getValue().equals(view.getText().toString())) {
                                Toast.makeText(context, "已存在", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        FiltrateBean.Children cd = new FiltrateBean.Children();
                        cd.setValue(view.getText().toString());
                        filtrateBean.getChildren().add(cd);
                        DictModel model = new DictModel();
                        model.setType(filtrateBean.getTypeName());
                        model.setValue(cd.getValue());
                        dictDao.save(model);
                        initParam();
                        screenPopWindow.getAdapter().notifyDataSetChanged();

                    }
                });
                //设置多选，因为共用的一个bean，这里调用reset重置下数据
                screenPopWindow.setSingle(false).build();
                screenPopWindow.showAsDropDown(person.getContentEdt());
                screenPopWindow.setOnConfirmClickListener(new ScreenPopWindow.OnConfirmClickListener() {
                    @Override
                    public void onConfirmClick(List<String> list) {
                        StringBuilder str = new StringBuilder();
                        for (int i = 0; i < list.size(); i++) {
                            str.append(list.get(i)).append("&");
                        }
                        if (list.size() > 0) {
                            person.getContentEdt().setText(str.deleteCharAt(str.length() - 1));
                        } else {
                            person.getContentEdt().setText("");
                        }
                        Toast.makeText(context, str.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        qy.getJtRightIv().setImageResource(R.mipmap.country_selecter);
        qy.setItemOnClickListener(new ItemGroup.ItemOnClickListener() {
            @Override
            public void onItemClick(final View v) {
                initParam();
                screenPopWindow = new ScreenPopWindow(context, qyDictList);
                screenPopWindow.getAdapter().setRightBtnClick(new ScreenListViewAdapter.OnRightBtnClick() {
                    @Override
                    public void onClick(EditText view, FiltrateBean filtrateBean) {
                        if ("".equals(String.valueOf(view.getText()))) {
                            return;
                        }
                        List<FiltrateBean.Children> children =
                                filtrateBean.getChildren();
                        for (FiltrateBean.Children child : children) {
                            if (child.getValue().equals(view.getText().toString())) {
                                Toast.makeText(context, "已存在", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        FiltrateBean.Children cd = new FiltrateBean.Children();
                        cd.setValue(view.getText().toString());
                        filtrateBean.getChildren().add(cd);
                        DictModel model = new DictModel();
                        model.setType(filtrateBean.getTypeName());
                        model.setValue(cd.getValue());
                        dictDao.save(model);
                        initParam();
                        screenPopWindow.getAdapter().notifyDataSetChanged();

                    }
                });
                //设置多选，因为共用的一个bean，这里调用reset重置下数据
                screenPopWindow.setSingle(true).build();
                screenPopWindow.showAsDropDown(qy.getContentEdt());
                screenPopWindow.setOnConfirmClickListener(new ScreenPopWindow.OnConfirmClickListener() {
                    @Override
                    public void onConfirmClick(List<String> list) {
                        if (list.size() > 0) {
                            qy.getContentEdt().setText(list.get(0));
                        } else {
                            qy.getContentEdt().setText("");
                        }
                    }
                });
            }
        });


        zlnd.getJtRightIv().setImageResource(R.mipmap.country_selecter);
        zlnd.setItemOnClickListener(new ItemGroup.ItemOnClickListener() {
            @Override
            public void onItemClick(final View v) {
                initParam();
                screenPopWindow = new ScreenPopWindow(context, yearDictList);
                screenPopWindow.getAdapter().setRightBtnClick(new ScreenListViewAdapter.OnRightBtnClick() {
                    @Override
                    public void onClick(EditText view, FiltrateBean filtrateBean) {
                        if ("".equals(String.valueOf(view.getText()))) {
                            return;
                        }
                        List<FiltrateBean.Children> children =
                                filtrateBean.getChildren();
                        for (FiltrateBean.Children child : children) {
                            if (child.getValue().equals(view.getText().toString())) {
                                Toast.makeText(context, "已存在", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        FiltrateBean.Children cd = new FiltrateBean.Children();
                        cd.setValue(view.getText().toString());
                        filtrateBean.getChildren().add(cd);
                        DictModel model = new DictModel();
                        model.setType(filtrateBean.getTypeName());
                        model.setValue(cd.getValue());
                        dictDao.save(model);
                        initParam();
                        screenPopWindow.getAdapter().notifyDataSetChanged();

                    }
                });
                //设置多选，因为共用的一个bean，这里调用reset重置下数据
                screenPopWindow.setSingle(true).build();
                screenPopWindow.showAsDropDown(zlnd.getContentEdt());
                screenPopWindow.setOnConfirmClickListener(new ScreenPopWindow.OnConfirmClickListener() {
                    @Override
                    public void onConfirmClick(List<String> list) {
                        if (list.size() > 0) {
                            zlnd.getContentEdt().setText(list.get(0));
                        } else {
                            zlnd.getContentEdt().setText("");
                        }

                    }
                });
            }
        });


        fczlb.getJtRightIv().setImageResource(R.mipmap.country_selecter);
        fczlb.setItemOnClickListener(new ItemGroup.ItemOnClickListener() {
            @Override
            public void onItemClick(final View v) {
                initParam();
                screenPopWindow = new ScreenPopWindow(context, fczlbDictList);
                screenPopWindow.getAdapter().setRightBtnClick(new ScreenListViewAdapter.OnRightBtnClick() {
                    @Override
                    public void onClick(EditText view, FiltrateBean filtrateBean) {
                        if ("".equals(String.valueOf(view.getText()))) {
                            return;
                        }
                        List<FiltrateBean.Children> children =
                                filtrateBean.getChildren();
                        for (FiltrateBean.Children child : children) {
                            if (child.getValue().equals(view.getText().toString())) {
                                Toast.makeText(context, "已存在", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        FiltrateBean.Children cd = new FiltrateBean.Children();
                        cd.setValue(view.getText().toString());
                        filtrateBean.getChildren().add(cd);
                        DictModel model = new DictModel();
                        model.setType(filtrateBean.getTypeName());
                        model.setValue(cd.getValue());
                        dictDao.save(model);
                        initParam();
                        screenPopWindow.getAdapter().notifyDataSetChanged();

                    }
                });
                //设置多选，因为共用的一个bean，这里调用reset重置下数据
                screenPopWindow.setSingle(true).build();
                screenPopWindow.showAsDropDown(fczlb.getContentEdt());
                screenPopWindow.setOnConfirmClickListener(new ScreenPopWindow.OnConfirmClickListener() {
                    @Override
                    public void onConfirmClick(List<String> list) {
                        if (list.size() > 0) {
                            fczlb.getContentEdt().setText(list.get(0));
                        } else {
                            fczlb.getContentEdt().setText("");
                        }

                    }
                });
            }
        });
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

    private List<FiltrateBean> personDictList, yearDictList, qyDictList, fczlbDictList;
    private ScreenPopWindow screenPopWindow;

    private void initParam() {
        List<DictModel> persons = dictDao.list("调查人员");
        FiltrateBean fb1 = new FiltrateBean();
        fb1.setTypeName("调查人员");
        String txt = person.getText();
        String[] txts = null;
        if (!"".equals(txt)) {
            txts = txt.split("&");
        }
        List<FiltrateBean.Children> childrenList = new ArrayList<>();
        for (DictModel dictModel : persons) {
            boolean selected = false;
            if (null != txts && txts.length > 0) {
                for (String s : txts) {
                    if (dictModel.getValue().equals(s)) {
                        selected = true;
                        break;
                    }
                }
            }
            FiltrateBean.Children cd = new FiltrateBean.Children();
            cd.setValue(dictModel.getValue());
            cd.setSelected(selected);
            childrenList.add(cd);
        }

        fb1.setChildren(childrenList);
        personDictList = new ArrayList<>();
        personDictList.add(fb1);

        List<DictModel> years = dictDao.list("造林年度");
        fb1 = new FiltrateBean();
        fb1.setTypeName("造林年度");
        childrenList = new ArrayList<>();
        for (DictModel dictModel : years) {
            FiltrateBean.Children cd = new FiltrateBean.Children();
            cd.setValue(dictModel.getValue());
            cd.setSelected(dictModel.getValue().equals(zlnd.getText()));
            childrenList.add(cd);
        }

        fb1.setChildren(childrenList);
        yearDictList = new ArrayList<>();
        yearDictList.add(fb1);


        List<DictModel> qyList = dictDao.list("起源");
        fb1 = new FiltrateBean();
        fb1.setTypeName("起源");
        childrenList = new ArrayList<>();
        for (DictModel dictModel : qyList) {
            FiltrateBean.Children cd = new FiltrateBean.Children();
            cd.setValue(dictModel.getValue());
            cd.setSelected(dictModel.getValue().equals(qy.getText()));
            childrenList.add(cd);
        }

        fb1.setChildren(childrenList);
        qyDictList = new ArrayList<>();
        qyDictList.add(fb1);

        List<DictModel> fcList = dictDao.list("分场造林部");
        fb1 = new FiltrateBean();
        fb1.setTypeName("分场造林部");
        childrenList = new ArrayList<>();
        for (DictModel dictModel : fcList) {
            FiltrateBean.Children cd = new FiltrateBean.Children();
            cd.setValue(dictModel.getValue());
            cd.setSelected(dictModel.getValue().equals(fczlb.getText()));
            childrenList.add(cd);
        }

        fb1.setChildren(childrenList);
        fczlbDictList = new ArrayList<>();
        fczlbDictList.add(fb1);

    }
}
