package com.zhangqie.shoppingcart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.data.format.bg.BaseBackgroundFormat;
import com.bin.david.form.data.table.TableData;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.zhangqie.shoppingcart.model.AreaModel;
import com.zhangqie.shoppingcart.widget.ItemGroup;
import com.zhangqie.shoppingcart.widget.MDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AreaMemActivity extends AppCompatActivity {


    @Bind(R.id.areaTable)
    SmartTable<AreaModel> table;
    @Bind(R.id.nv_titleBar)
    TitleBar titleBar;
    List<AreaModel> rows;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_mem);
        ButterKnife.bind(this);

        titleBar.setRightTitle("添加");

        titleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {

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
                    public void onSave(MDialog dialog,ItemGroup igWidth, ItemGroup igHeight, ItemGroup igP) {
                        AreaModel areaModel = new AreaModel();
                        areaModel.setWidth(Double.parseDouble(igWidth.getText()));
                        areaModel.setHeight(Double.parseDouble(igHeight.getText()));
                        areaModel.setP(Double.parseDouble(igP.getText()));
                        rows.add(areaModel);
                        table.getTableData().setT(rows);
                        table.notifyDataChanged();
                        Toast.makeText(AreaMemActivity.this, "click....", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                });
                mDialog = builder.show();
                mDialog.setDialogWindowAttr(0.8, 0.5, AreaMemActivity.this);


            }
        });
        WindowManager wm = this.getWindowManager();
        int screenWith = wm.getDefaultDisplay().getWidth();
        table.getConfig().setMinTableWidth(screenWith); //设置最小宽度 屏幕宽度
        /**
         * 样带长m	带宽m	坡度。	Percent/100
         * ATAN(a)	COS(a)	水平距离m	样带面积m2
         */

        final Column<String> height = new Column<>("样带长m", "height");
        final Column<String> width = new Column<>("带宽m", "width");
        final Column<String> p = new Column<>("坡度°", "p");
        /*final Column<String> percent = new Column<>("Percent/100", "percent");
        final Column<String> atan = new Column<>("ATAN(a)", "atan");
        final Column<String> cos = new Column<>("COS(a)", "cos");
        final Column<String> away = new Column<>("水平距离m", "away");*/
        final Column<String> area = new Column<>("样带面积m²", "area");
        area.setAutoCount(true);
        table.getConfig().setShowTableTitle(false);
        table.getConfig().setShowXSequence(false);

        rows = new ArrayList<>();
        for (int i = 0; i < 5; i++) {

            AreaModel model = new AreaModel();

            rows.add(model);

        }
        rows.get(0).setHeight(50);
        rows.get(0).setP(25);

//        TableData<AreaModel> tableData = new TableData<AreaModel>("", rows, height, width, p, percent, atan, cos, away, area);
        TableData<AreaModel> tableData = new TableData<AreaModel>("", rows, height, width, p, area);

        tableData.setShowCount(true);
        tableData.setOnRowClickListener(new TableData.OnRowClickListener<AreaModel>() {
            @Override
            public void onClick(Column column, final AreaModel areaModel, int col, int row) {
                MDialog mDialog = null;
                final MDialog.Builder builder = new MDialog.Builder(AreaMemActivity.this);
                builder.setData(areaModel);
                builder.setSaveClick(new MDialog.Builder.OnSaveClick() {
                    @Override
                    public void onSave(MDialog dialog,ItemGroup igWidth, ItemGroup igHeight, ItemGroup igP) {
                        areaModel.setWidth(Double.parseDouble(igWidth.getText()));
                        areaModel.setHeight(Double.parseDouble(igHeight.getText()));
                        areaModel.setP(Double.parseDouble(igP.getText()));
                        //rows.add(areaModel);
                        table.getTableData().setT(rows);
                        table.notifyDataChanged();
                        Toast.makeText(AreaMemActivity.this, "click....", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                });
                mDialog = builder.show();
                mDialog.setDialogWindowAttr(0.8, 0.5, AreaMemActivity.this);
            }
        });
//        table.getConfig().setColumnTitleBackground(new BaseBackgroundFormat(getResources().getColor(R.color.windows_bg)));
//        table.getConfig().setCountBackground(new BaseBackgroundFormat(getResources().getColor(R.color.windows_bg)));

        table.setTableData(tableData);
    }
}
