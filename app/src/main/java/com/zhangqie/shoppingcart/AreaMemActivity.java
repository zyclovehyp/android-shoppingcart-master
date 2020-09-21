package com.zhangqie.shoppingcart;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.data.table.TableData;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.zhangqie.shoppingcart.dao.AreaDao;
import com.zhangqie.shoppingcart.model.AreaModel;
import com.zhangqie.shoppingcart.model.SheetHeader;
import com.zhangqie.shoppingcart.widget.ItemGroup;
import com.zhangqie.shoppingcart.widget.MDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AreaMemActivity extends BaseActivity {

    private static final String TAG = AreaMemActivity.class.getName();

    @Bind(R.id.areaTable)
    SmartTable<AreaModel> table;
    @Bind(R.id.nv_titleBar)
    TitleBar titleBar;
    List<AreaModel> rows;
    SheetHeader sheet;
    List<Column> columns;
    TableData<AreaModel> tableData;

    AreaDao areaDao;
    int sheetId;
    String sheetNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_mem);
        ButterKnife.bind(this);

        areaDao = new AreaDao();
        sheet = (SheetHeader) getIntent().getSerializableExtra("header");
        sheetId = sheet.getSheetId();
        sheetNo = sheet.getSheetNo();

        initTitlBar();


        initColumn();

        buildTableData();


        configTable();

    }

    private void configTable() {
        table.getConfig().setShowTableTitle(false);
        table.getConfig().setShowXSequence(false);
        WindowManager wm = this.getWindowManager();
        int screenWith = wm.getDefaultDisplay().getWidth();
        table.getConfig().setMinTableWidth(screenWith); //设置最小宽度 屏幕宽度

        table.getConfig().setColumnTitleHorizontalPadding(0);
        table.getConfig().setHorizontalPadding(0);
        table.getConfig().setFixedYSequence(true);


        table.setTableData(tableData);
    }

    private void buildTableData() {
        rows = areaDao.list("" + sheetId, sheetNo);


        tableData = new TableData<AreaModel>("", rows, columns);
        tableData.setShowCount(true);
        tableData.setOnRowClickListener(new TableData.OnRowClickListener<AreaModel>() {
            @Override
            public void onClick(Column column, final AreaModel areaModel, int col, int row) {
                MDialog mDialog = null;
                final MDialog.Builder builder = new MDialog.Builder(AreaMemActivity.this);
                builder.setData(areaModel);
                builder.setSaveClick(new MDialog.Builder.OnSaveClick() {
                    @Override
                    public void onSave(MDialog dialog, ItemGroup igWidth, ItemGroup igHeight, ItemGroup igP) {

                        try {
                            areaModel.setWidth(Double.parseDouble(igWidth.getText()));
                            areaModel.setHeight(Double.parseDouble(igHeight.getText()));
                            areaModel.setP(Double.parseDouble(igP.getText()));
                            areaDao.save(areaModel);
                            table.getTableData().setT(rows);
                            table.notifyDataChanged();
                            Toast.makeText(AreaMemActivity.this, "修改成功", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        } catch (Exception e) {
                            Toast.makeText(AreaMemActivity.this, "Error:" + e.getMessage(), Toast.LENGTH_LONG).show();
                            Log.i(TAG, "修改成功: ", e);
                            e.printStackTrace();
                        }

                    }
                });
                mDialog = builder.show();
                mDialog.setDialogWindowAttr(0.8, 0.5, AreaMemActivity.this);
            }
        });
    }

    private void initColumn() {
        final Column<String> height = new Column<>("样带长m", "height");
        final Column<String> width = new Column<>("带宽m", "width");
        final Column<String> p = new Column<>("坡度°", "p");
        /*final Column<String> percent = new Column<>("Percent/100", "percent");
        final Column<String> atan = new Column<>("ATAN(a)", "atan");
        final Column<String> cos = new Column<>("COS(a)", "cos");
        final Column<String> away = new Column<>("水平距离m", "away");*/
        final Column<String> area = new Column<>("样带面积m²", "area");
        area.setAutoCount(true);

        columns = new ArrayList<>();
        columns.add(height);
        columns.add(width);
        columns.add(p);
        columns.add(area);
    }

    private void initTitlBar() {
        titleBar.setRightTitle("添加");

        titleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {
                getVal();
            }

            @Override
            public void onTitleClick(View v) {

            }

            @Override
            public void onRightClick(View v) {
                MDialog mDialog = null;
                final MDialog.Builder builder = new MDialog.Builder(AreaMemActivity.this);
                builder.setSaveClick(new MDialog.Builder.OnSaveClick() {
                    @Override
                    public void onSave(MDialog dialog, ItemGroup igWidth, ItemGroup igHeight, ItemGroup igP) {

                        try {
                            AreaModel areaModel = new AreaModel();
                            areaModel.setSheetId(sheetId);
                            areaModel.setSheetNo(sheetNo);
                            areaModel.setWidth(Double.parseDouble(igWidth.getText()));
                            areaModel.setHeight(Double.parseDouble(igHeight.getText()));
                            areaModel.setP(Double.parseDouble(igP.getText()));

                            areaDao.save(areaModel);
                            rows.add(areaModel);
                            table.getTableData().setT(rows);
                            table.notifyDataChanged();
                            Toast.makeText(AreaMemActivity.this, "添加成功", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        } catch (Exception e) {
                            Toast.makeText(AreaMemActivity.this, "Error:" + e.getMessage(), Toast.LENGTH_LONG).show();
                            Log.i(TAG, "onSave: ", e);
                            //e.printStackTrace();
                        }

                    }
                });
                mDialog = builder.show();
                mDialog.setDialogWindowAttr(0.8, 0.5, AreaMemActivity.this);


            }
        });
    }


    /**
     * 不起作用
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getVal();
    }

    /**
     * 需要拦截press的事件
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            getVal();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    private void getVal() {

        List<AreaModel> areaModels = tableData.getT();

        double mianji = 0.0;
        for (AreaModel areaModel : areaModels) {
            mianji += areaModel.getArea();
        }
        mianji = mianji / 10000;
        sheet.setMianJi(String.valueOf(mianji));
        Intent i = new Intent();
        setResult(3, i.putExtra("result", sheet));
        finish();

    }

}
