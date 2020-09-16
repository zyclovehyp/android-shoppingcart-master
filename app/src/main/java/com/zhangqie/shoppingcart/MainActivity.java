package com.zhangqie.shoppingcart;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.zhangqie.shoppingcart.adapter.TreeExpandAdapter;
import com.zhangqie.shoppingcart.callback.OnClickAddCloseListenter;
import com.zhangqie.shoppingcart.dao.TreeDao;
import com.zhangqie.shoppingcart.model.SheetHeader;
import com.zhangqie.shoppingcart.model.TreeModel;
import com.zhangqie.shoppingcart.util.DataList;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity {

    private static String TAG = MainActivity.class.getName();
    @Bind(R.id.cart_expandablelistview)
    ExpandableListView cartExpandablelistview;
    @Bind(R.id.cart_money)
    TextView cartMoney;
    @Bind(R.id.cart_shopp_moular)
    Button cartShoppMoular;

    @Bind(R.id.titleBar)
    TitleBar titleBar;
    TreeExpandAdapter treeExpandAdapter;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    TreeDao treeDao;
    double price;
    int num;
    SheetHeader sheet;

    SheetHeaderViewHolder viewHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.draw_edit_page);
        sheet = (SheetHeader) getIntent().getSerializableExtra("header");
        initLeftView();
        treeDao = new TreeDao();
        ButterKnife.bind(this);
        initView();
    }

    private void initLeftView() {
        viewHolder = new SheetHeaderViewHolder(this, sheet, findViewById(R.id.sheet_page));
        viewHolder.setView(sheet);
        viewHolder.setSaveListener(new SheetHeaderViewHolder.OnSaveListener() {
            @Override
            public void onSave(int id) {
                if (drawerLayout.isDrawerOpen(getDrawerGravity())) {
                    drawerLayout.closeDrawer(getDrawerGravity());
                }
                Toast.makeText(MainActivity.this, "保存成功！", Toast.LENGTH_LONG).show();
            }
        });
        viewHolder.setStartActivtiy(new SheetHeaderViewHolder.StartActivtiy() {
            @Override
            public void startActivity(SheetHeader sheetHeader) {
                Intent intent = new Intent(MainActivity.this, AreaMemActivity.class);
                intent.putExtra("header", sheetHeader);
                startActivityForResult(intent, 2);
            }
        });
    }

    private void initView() {
        OnTitleBarListener titleBarListener = new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {
                finish();
                if (drawerLayout.isDrawerOpen(getDrawerGravity())) {
                    drawerLayout.closeDrawer(getDrawerGravity());
                }
            }

            @Override
            public void onTitleClick(View v) {

            }

            @Override
            public void onRightClick(View v) {
                if (!drawerLayout.isDrawerOpen(getDrawerGravity())) {
                    drawerLayout.openDrawer(getDrawerGravity());
                }
            }
        };
        cartExpandablelistview.setGroupIndicator(null);
        titleBar.setOnTitleBarListener(titleBarListener);
        initNvPage();
        showData();
    }

    private int getDrawerGravity() {

        return Gravity.START;
    }

    private void setAllData() {
        List<TreeModel> all = treeDao.list(sheet.getSheetId() + "");

        if (all.size() <= 0) {
            all = DataList.build(sheet.getSheetId(),
                    "0".equals(sheet.getType()) ? DataList.danJin() : DataList.doubleJin());

        }
        sheet.setAllData(all);
        saveAll();
    }

    private void saveAll() {

        for (int i = 0; i < sheet.getAllData().size(); i++) {
            treeDao.save(sheet.getAllData().get(i));
        }
    }

    private void showData() {

        setAllData();

        if (sheet != null && sheet.getAllData().size() > 0) {
            treeExpandAdapter = null;
            showExpandData();
        }
    }

    private void showExpandData() {
        treeExpandAdapter = new TreeExpandAdapter(this, cartExpandablelistview, sheet);

        cartExpandablelistview.setAdapter(treeExpandAdapter);

        cartExpandablelistview.expandGroup(0);
        treeExpandAdapter.setOnClickAddCloseListenter(new OnClickAddCloseListenter() {
            @Override
            public void onItemClick(View view, int index, int onePosition, int position, int num) {
                if (index == 1) {
                    if (num >= 1) {
                        TreeModel treeModel = sheet.getAllData().get(position);
                        treeModel.setNum((num - 1));
                        treeDao.save(treeModel);
                        treeExpandAdapter.notifyDataSetChanged();
                    }
                } else {
                    TreeModel treeModel = sheet.getAllData().get(position);
                    treeModel.setNum((num + 1));
                    treeDao.save(treeModel);
                    treeExpandAdapter.notifyDataSetChanged();
                }
                showCommodityCalculation();
            }
        });
        showCommodityCalculation();
    }

    private void showCommodityCalculation() {

        try {
            String money = String.valueOf(price);
            num = 0;
            for (int i = 0; i < sheet.getAllData().size(); i++) {
                TreeModel treeModel = sheet.getAllData().get(i);

                num += treeModel.getNum();
            }
            cartMoney.setText("" + num + "株");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @OnClick(R.id.cart_shopp_moular)
    public void onClick() {
        Toast.makeText(MainActivity.this, "提交数据:  " + cartMoney.getText().toString(), Toast.LENGTH_LONG).show();
    }


    private void initNvPage() {

        Button jianNum = findViewById(R.id.jian_num);
        Button addNum = findViewById(R.id.add_num);

        final Button ydmnum = findViewById(R.id.ydmnum);


        jianNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num = ydmnum.getText().toString();
                int n = Integer.parseInt(num);
                n = n - 1;
                if (n <= 0) {
                    return;
                }
                changeNum(n);
            }
        });
        addNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num = ydmnum.getText().toString();
                int n = Integer.parseInt(num);
                n = n + 1;
                changeNum(n);
            }
        });
    }

    private void changeNum(int num) {
        final Button ydmnum = findViewById(R.id.ydmnum);
        ydmnum.setText("" + num);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //此处可以根据两个Code进行判断，本页面和结果页面跳过来的值
        if (requestCode == 2 && resultCode == 3) {
            SheetHeader sheet = (SheetHeader) data.getSerializableExtra("result");
            viewHolder.changeMianji(sheet.getMianJi());
            //需要更新数据
            try {
                viewHolder.dao.save(sheet);
            } catch (Exception e) {
                Log.i(TAG, "result..: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
