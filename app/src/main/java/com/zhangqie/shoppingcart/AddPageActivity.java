package com.zhangqie.shoppingcart;

import android.content.Intent;
import android.os.Environment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.zhangqie.shoppingcart.adapter.SheetHeaderAdapter;
import com.zhangqie.shoppingcart.callback.OnViewItemClickListener;
import com.zhangqie.shoppingcart.dao.TreeDao;
import com.zhangqie.shoppingcart.model.SheetHeader;
import com.zhangqie.shoppingcart.model.TreeModel;
import com.zhangqie.shoppingcart.util.DataList;
import com.zhangqie.shoppingcart.util.ExcelUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jxl.Sheet;

public class AddPageActivity extends AppCompatActivity {

    ListView mSheet;
    DrawerLayout drawer;
    TitleBar titleBar;
    SheetHeaderViewHolder viewHolder;
    ArrayList<ArrayList<String>> recordList;
    List<SheetHeader> all =
            new ArrayList<>();

    Button export_excel;
    TreeDao treeDao;
    private File file;
    private String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sheet_header_main);
        mSheet = findViewById(R.id.mSheetList);
        drawer = findViewById(R.id.drawer_layout);
        titleBar = findViewById(R.id.nv_titleBar);
        treeDao = new TreeDao();
        export_excel = findViewById(R.id.export_excel);
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
                if (!drawer.isDrawerOpen(getDrawerGravity())) {
                    drawer.openDrawer(getDrawerGravity());
                }
            }
        });


        final SheetHeaderAdapter adapter = new SheetHeaderAdapter(this,
                all);

        mSheet.setAdapter(adapter);
        adapter.setOnViewItemClickListener(new OnViewItemClickListener() {
            @Override
            public void onItemClick(boolean isFlang, View view, int position) {
                switch (view.getId()) {
                    case R.id.check_box:
                        all.get(position).setCheck(!all.get(position).isCheck());
                        adapter.notifyDataSetChanged();
                        break;
                    case R.id.mContent:
                        Intent intent = new Intent(AddPageActivity.this,
                                MainActivity.class);
                        intent.putExtra("header", all.get(position));
                        startActivity(intent);
                        break;
                }
            }
        });
        SheetHeader sheet = new SheetHeader();
        viewHolder = new SheetHeaderViewHolder(this, sheet, findViewById(R.id.sheet_page));
        all = viewHolder.dao.list();
        viewHolder.setSaveListener(new SheetHeaderViewHolder.OnSaveListener() {
            @Override
            public void onSave(int id) {
                all = viewHolder.dao.list();
                adapter.setList(all);
                adapter.notifyDataSetChanged();
                if (drawer.isDrawerOpen(getDrawerGravity())) {
                    drawer.closeDrawer(getDrawerGravity());
                }
            }
        });
        findViewById(R.id.sheet_page).setTag(viewHolder);
        adapter.setList(all);
        adapter.notifyDataSetChanged();

        export_excel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exportExcel(v);
            }
        });
    }

    /**
     * 导出excel
     *
     * @param view
     */
    public void exportExcel(View view) {
        file = new File(getSDPath() + "/Record");
        makeDir(file);

        fileName = getSDPath() + "/Record/成绩表123.csv";
        ExcelUtil.exportCSV(fileName, getRecordData());
    }

    private String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();
        }
        String dir = sdDir.toString();
        return dir;
    }

    public void makeDir(File dir) {
        if (!dir.getParentFile().exists()) {
            makeDir(dir.getParentFile());
        }
        dir.mkdir();
    }

    /**
     * 将数据集合 转化成ArrayList<ArrayList<String>>
     *
     * @return
     */
    private ArrayList<ArrayList<String>> getRecordData() {
        recordList = new ArrayList<>();
        for (int i = 0; i < all.size(); i++) {

            if (!all.get(i).isCheck()) {
                SheetHeader sheetHeader = all.get(i);
                buildHeader(sheetHeader);
                buildChild();
                ArrayList<String> beanList = null;
                List<TreeModel> all = treeDao.list(sheetHeader.getSheetId() + "");

                if (all.size() <= 0) {
                    all = DataList.build(sheetHeader.getSheetId(),
                            "0".equals(sheetHeader.getType()) ? DataList.danJin() : DataList.doubleJin());

                }
                for (int l = 0; l < all.size(); l++) {

                    TreeModel treeModel = all.get(l);
                    beanList = new ArrayList<String>();
                    beanList.add(treeModel.getJinJie());
                    beanList.add(treeModel.getNum() + "");
                    beanList.add(treeModel.getTestWidth1());
                    beanList.add(treeModel.getTestHight1());
                    beanList.add(treeModel.getTestWidth2());
                    beanList.add(treeModel.getTestHight2());
                    beanList.add(treeModel.getTestWidth3());
                    beanList.add(treeModel.getTestHight3());
                    recordList.add(beanList);
                }

            }


        }
        return recordList;
    }

    private void buildHeader(SheetHeader sheetHeader) {

        ArrayList<String> beanList = new ArrayList<String>();

        beanList.add("样带编号");
        beanList.add(sheetHeader.getSheetNo());
        beanList.add("树种");
        beanList.add(sheetHeader.getTreeType());
        beanList.add("林班");
        beanList.add(sheetHeader.getClassType());
        beanList.add("起源");
        beanList.add(sheetHeader.getSourceAddress());
        recordList.add(beanList);

        beanList = new ArrayList<String>();

        beanList.add("分场(造林部)");
        beanList.add(sheetHeader.getFcZLB());
        beanList.add("采伐地点");
        beanList.add(sheetHeader.getAddress());
        beanList.add("小班");
        beanList.add(sheetHeader.getSmallType());
        beanList.add("造林年度");
        beanList.add(sheetHeader.getBuildYear());
        recordList.add(beanList);

        beanList = new ArrayList<String>();

        beanList.add("标准地面积");
        beanList.add(sheetHeader.getMianJi());
        beanList.add("GPS坐标");
        beanList.add(sheetHeader.getGps());
        beanList.add("样地木根数");
        beanList.add(sheetHeader.getSheetNo());
        beanList.add("郁闭度");
        beanList.add(sheetHeader.getYbd());
        recordList.add(beanList);


    }

    private void buildChild() {
        ArrayList<String> beanList = new ArrayList<String>();

        //径阶	总株数	胸径	树高	胸径	树高	胸径	树高
        beanList.add("径阶");
        beanList.add("总株数");

        beanList.add("胸径");
        beanList.add("树高");

        beanList.add("胸径");
        beanList.add("树高");

        beanList.add("胸径");
        beanList.add("树高");
        recordList.add(beanList);
    }

    private int getDrawerGravity() {

        return Gravity.START;
    }

}
