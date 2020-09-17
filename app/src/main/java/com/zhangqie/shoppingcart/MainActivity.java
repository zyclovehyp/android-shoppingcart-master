package com.zhangqie.shoppingcart;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.zhangqie.shoppingcart.adapter.TreeChildExpandAdapter;
import com.zhangqie.shoppingcart.callback.OnClickAddCloseListenter;
import com.zhangqie.shoppingcart.dao.TreeDao;
import com.zhangqie.shoppingcart.model.SheetHeader;
import com.zhangqie.shoppingcart.model.TreeModel;
import com.zhangqie.shoppingcart.util.DataList;
import com.zhangqie.shoppingcart.widget.ItemGroup;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends BaseActivity {

    private static String TAG = MainActivity.class.getName();
    @Bind(R.id.cart_expandablelistview)
    ExpandableListView cartExpandablelistview;
    @Bind(R.id.cart_money)
    TextView cartMoney;
    @Bind(R.id.cart_shopp_moular)
    Button cartShoppMoular;

    @Bind({R.id.formTitleBar})
    TitleBar formTitleBar;
    @Bind(R.id.titleBar)
    TitleBar titleBar;
//    TreeExpandAdapter treeExpandAdapter;

    TreeChildExpandAdapter treeExpandAdapter;

    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    TreeDao treeDao;
    double price;
    int num;
    SheetHeader sheet;

    SheetHeaderViewHolder viewHolder;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //定位参数设置
    private AMapLocationClientOption aMapLocationClientOption;
    ItemGroup gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.draw_edit_page);
        ButterKnife.bind(this);
        gps = findViewById(R.id.ig_gps);
        sheet = (SheetHeader) getIntent().getSerializableExtra("header");
        initLeftView();
        treeDao = new TreeDao();

        initView();
        initMapLocation();

        formTitleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {

            }

            @Override
            public void onTitleClick(View v) {

            }

            @Override
            public void onRightClick(View v) {
                if (!reqLocationPerssion()) return;
                String title = formTitleBar.getRightView().getText().toString();
                if ("开始定位".equals(title)) {
                    formTitleBar.setRightTitle("停止定位");
                    mLocationClient.startLocation();
                } else {
                    formTitleBar.setRightTitle("开始定位");
                    mLocationClient.stopLocation();
                }

            }
        });
    }


    private boolean reqLocationPerssion() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {//未开启定位权限
            //开启定位权限,200是标识码
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);

            return false;
        } else {
            return true;
        }

    }


    private void initMapLocation() {
        //声明定位回调监听器
        AMapLocationListener mLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation.getErrorCode() == 0) {
                    //定位成功回调信息，设置相关消息
                    aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                    aMapLocation.getLatitude();//获取经度
                    aMapLocation.getLongitude();//获取纬度;
                    aMapLocation.getAccuracy();//获取精度信息
                    aMapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果
                    aMapLocation.getCountry();//国家信息
                    aMapLocation.getProvince();//省信息
                    aMapLocation.getCity();//城市信息
                    aMapLocation.getDistrict();//城区信息
                    aMapLocation.getRoad();//街道信息
                    aMapLocation.getCityCode();//城市编码
                    aMapLocation.getAdCode();//地区编码
                    gps.setText(aMapLocation.getLatitude() + "&" + aMapLocation.getAccuracy());
                    //header_gps.setText(aMapLocation.getLatitude()+","+aMapLocation.getAccuracy());
                } else {
//                    Toast.makeText(AddPageActivity.this, "GPS定位失败：" + aMapLocation.getErrorInfo(), Toast.LENGTH_LONG).show();
                    //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                    Log.e("Tomato", "location Error, ErrCode:"
                            + aMapLocation.getErrorCode() + ", errInfo:"
                            + aMapLocation.getErrorInfo());
                }
            }
        };
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);


        //初始化定位参数
        aMapLocationClientOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        aMapLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        aMapLocationClientOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        aMapLocationClientOption.setOnceLocation(false);
        //设置是否强制刷新WIFI，默认为强制刷新
        aMapLocationClientOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        aMapLocationClientOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        aMapLocationClientOption.setInterval(2000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(aMapLocationClientOption);
        //启动定位
        // mLocationClient.startLocation();
    }

    private void initLeftView() {
        formTitleBar.setTitle("修改标准地调查记录表");
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
        treeExpandAdapter = new TreeChildExpandAdapter(this, cartExpandablelistview, sheet);

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
