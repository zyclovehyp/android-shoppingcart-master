package com.zhangqie.shoppingcart;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.zhangqie.shoppingcart.adapter.SheetHeaderAdapter;
import com.zhangqie.shoppingcart.callback.OnViewItemClickListener;
import com.zhangqie.shoppingcart.dao.AreaDao;
import com.zhangqie.shoppingcart.dao.SheetHeaderDao;
import com.zhangqie.shoppingcart.dao.TreeDao;
import com.zhangqie.shoppingcart.model.AreaModel;
import com.zhangqie.shoppingcart.model.SheetHeader;
import com.zhangqie.shoppingcart.model.TreeModel;
import com.zhangqie.shoppingcart.util.DataList;
import com.zhangqie.shoppingcart.util.ExcelUtil;
import com.zhangqie.shoppingcart.util.TimeUtils;
import com.zhangqie.shoppingcart.widget.ItemGroup;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class AddPageActivity extends BaseActivity {

    private static final String TAG = AddPageActivity.class.getName();
    ListView mSheet;
    DrawerLayout drawer;
    TitleBar titleBar, formTitleBar;
    SheetHeaderViewHolder viewHolder;
    ArrayList<ArrayList<String>> recordList;
    List<SheetHeader> all =
            new ArrayList<>();

    Button export_excel, delete;
    TreeDao treeDao;
    AreaDao areaDao;
    private File file;
    private String fileName;
    SheetHeaderDao dao;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //定位参数设置

    ItemGroup gps;
    private AMapLocationClientOption aMapLocationClientOption;
    SheetHeaderAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sheet_header_main);
        initNvPage();
        dao = new SheetHeaderDao();
        areaDao = new AreaDao();
        gps = findViewById(R.id.ig_gps);
        mSheet = findViewById(R.id.mSheetList);
        drawer = findViewById(R.id.drawer_layout);
        titleBar = findViewById(R.id.nv_titleBar);
        treeDao = new TreeDao();
        export_excel = findViewById(R.id.export_excel);
        titleBar.setRightTitle("添加");
        formTitleBar = findViewById(R.id.formTitleBar);
        initMapLocation();
        initTitleBar();

        all = dao.list();
        adapter = new SheetHeaderAdapter(this,
                all);

        adapter.setOnViewItemClickListener(new OnViewItemClickListener() {
            @Override
            public void onItemClick(boolean isFlang, View view, int position) {
                switch (view.getId()) {
                    case R.id.check_box:
                        all.get(position).setCheck(!all.get(position).isCheck());
                        adapter.notifyDataSetChanged();
                        break;
                    case R.id.shop_name:
                    case R.id.clickVIew:
                        Intent intent = new Intent(AddPageActivity.this,
                                MainActivity.class);
                        intent.putExtra("header", all.get(position));
                        startActivity(intent);
                        break;
                }
            }
        });
        LayoutInflater inflater = LayoutInflater.from(this);
        View emptyView = inflater.inflate(R.layout.empty_view, null);

        ViewGroup group = (ViewGroup) mSheet.getParent();
        group.addView(emptyView);
        mSheet.setEmptyView(emptyView);
        mSheet.setAdapter(adapter);


        export_excel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exportExcel(v);
            }
        });
        delete = findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = 0;
                for (int i = 0; i < all.size(); i++) {
                    if (all.get(i).isCheck()) {
                        count++;
                        SheetHeader sheetHeader = all.get(i);
                        dao.delete(sheetHeader);
                    }
                }
                if (count == 0) {
                    Toast.makeText(AddPageActivity.this, "请选择要删除的数据！", Toast.LENGTH_LONG).show();
                } else {
                    all = dao.list();
                    adapter.setList(all);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(AddPageActivity.this, "删除成功！", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void initTitleBar() {
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

        titleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {

            }

            @Override
            public void onTitleClick(View v) {

            }

            @Override
            public void onRightClick(View v) {
                final SheetHeader sheet = new SheetHeader();
                sheet.setSheetNo(Application.bizDao.getBizNo());
                viewHolder = new SheetHeaderViewHolder(AddPageActivity.this, sheet, findViewById(R.id.sheet_page));
                if (!drawer.isDrawerOpen(getDrawerGravity())) {
                    drawer.openDrawer(getDrawerGravity());
                }

                viewHolder.setSaveListener(new SheetHeaderViewHolder.OnSaveListener() {
                    @Override
                    public void onSave(int id) {
                        all = viewHolder.dao.list();
                        adapter.setList(all);
                        adapter.notifyDataSetChanged();
                        Log.i(TAG, "onSave: ...list size..." + all.size());
                        if (drawer.isDrawerOpen(getDrawerGravity())) {
                            drawer.closeDrawer(getDrawerGravity());
                        }
                    }
                });
                viewHolder.setStartActivtiy(new SheetHeaderViewHolder.StartActivtiy() {
                    @Override
                    public void startActivity(SheetHeader sheetHeader) {
                        Toast.makeText(AddPageActivity.this, "还未保存信息", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(AddPageActivity.this, AreaMemActivity.class);
                        intent.putExtra("header", sheetHeader);
                        startActivityForResult(intent, 1);
                    }
                });
                findViewById(R.id.sheet_page).setTag(viewHolder);
            }
        });
    }

    /**
     * 导出excel
     *
     * @param view
     */
    public void exportExcel(View view) {

        if (!requestFileSavePermission()) return;

        file = new File(getSDPath() + "/Record");
        makeDir(file);

        String title = TimeUtils.getYear() + TimeUtils.getMonth() + TimeUtils.getDay() + TimeUtils.getCurrentTime();
        fileName = getSDPath() + "/Record/" + title + ".xls";
        ExcelUtil.initExcel(fileName,title);
//        ExcelUtil.exportCSV(fileName, getRecordData());

        ExcelUtil.writeObjListToExcel(fileName, getRecordData(), this);
        //Toast.makeText(this, "导出成功！\n文件导出路径：" + fileName, Toast.LENGTH_LONG).show();
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
                    double x = new BigDecimal(aMapLocation.getLatitude()).setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
                    double y = new BigDecimal(aMapLocation.getAccuracy()).setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();

                    gps.setText(x + "&" + y);
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

    /**
     * 将数据集合 转化成ArrayList<ArrayList<String>>
     *
     * @return
     */
    private ArrayList<ArrayList<String>> getRecordData() {
        recordList = new ArrayList<>();
        for (int i = 0; i < all.size(); i++) {

            if (all.get(i).isCheck()) {
                SheetHeader sheetHeader = all.get(i);
                buildHeader(sheetHeader);
                buildChild();
                ArrayList<String> beanList = null;
                List<TreeModel> all = treeDao.list(sheetHeader.getSheetId() + "");

                if (all.size() <= 0) {
                    all = DataList.build(sheetHeader.getSheetId(),
                            "0".equals(sheetHeader.getType()) ? DataList.danJin() : DataList.doubleJin());

                }
                int num = 0;
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
                    num += treeModel.getNum();
                    recordList.add(beanList);
                }
                beanList = new ArrayList<String>();
                beanList.add("合计");
                beanList.add(num + "");
                beanList.add("");
                beanList.add("");
                beanList.add("");
                recordList.add(beanList);

                beanList = new ArrayList<String>();
                beanList.add("其他记载情况");
                beanList.add(sheetHeader.getRemark());
                recordList.add(beanList);


                beanList = new ArrayList<String>();
                beanList.add("调查人员");
                beanList.add(sheetHeader.getPerson());
                beanList.add("分场");
                beanList.add("");
                beanList.add("调查日期");
                beanList.add(sheetHeader.getDate());
                recordList.add(beanList);


                buildLine();
                buildArea(sheetHeader);

                buildLine();
            }


        }
        return recordList;
    }

    private void buildLine() {
        ArrayList<String> beanList;
        beanList = new ArrayList<String>();
        beanList.add("");
        beanList.add("");
        beanList.add("");
        beanList.add("");
        beanList.add("");
        beanList.add("");
        beanList.add("");
        recordList.add(beanList);
    }

    private void buildHeader(SheetHeader sheetHeader) {

        ArrayList<String> beanList;

        beanList = new ArrayList<String>();

        beanList.add("");
        beanList.add("");
        beanList.add("");
        beanList.add("标准地调查记录表");
        beanList.add("");
        beanList.add("");
        beanList.add("");
        beanList.add("");
        recordList.add(beanList);


        beanList = new ArrayList<String>();

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
        beanList.add(sheetHeader.getYdmnum());
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


    private void buildArea(SheetHeader sheetHeader) {
        List<AreaModel> list = areaDao.list(sheetHeader.getSheetId() + "", sheetHeader.getSheetNo());


        ArrayList<String> beanList = null;


        beanList = new ArrayList<String>();

        beanList.add("");
        beanList.add("");

        beanList.add("样带面积计算表");


        recordList.add(beanList);


        beanList = new ArrayList<String>();

        //径阶	总株数	胸径	树高	胸径	树高	胸径	树高
        beanList.add("NO");
        beanList.add("样带长m");

        beanList.add("样带宽m");
        beanList.add("坡度°");

        beanList.add("Percent/100");
        beanList.add("ATAN(a)");

        beanList.add("COS(a)");
        beanList.add("水平距离m");
        beanList.add("样带面积m2");

        recordList.add(beanList);

        double sum = 0.0;
        for (int i = 0; i < list.size(); i++) {
            AreaModel model = list.get(i);
            beanList = new ArrayList<>();
            beanList.add((i + 1) + "");
            beanList.add("" + model.getHeight());
            beanList.add("" + model.getWidth());
            beanList.add("" + model.getP());
            beanList.add("" + model.getPercent());
            beanList.add("" + model.getAtan());
            beanList.add("" + model.getCos());
            beanList.add("" + model.getAway());
            beanList.add("" + model.getArea());
            sum += model.getArea();
            recordList.add(beanList);
        }
        beanList = new ArrayList<String>();
        beanList.add("合计");
        beanList.add("");
        beanList.add("");
        beanList.add("");
        beanList.add("");
        beanList.add("");
        beanList.add("");
        beanList.add("");
        beanList.add("" + sum);
        recordList.add(beanList);

    }

    private int getDrawerGravity() {

        return Gravity.START;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 200://刚才的识别码
                if (null == grantResults || grantResults.length <= 0) {
                    Toast.makeText(AddPageActivity.this, "未开启定位权限,请手动到设置去开启权限", Toast.LENGTH_LONG).show();
                    return;
                }
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {//用户同意权限,执行我们的操作
                    mLocationClient.startLocation();//开始定位
                } else {//用户拒绝之后,当然我们也可以弹出一个窗口,直接跳转到系统设置页面
                    Toast.makeText(AddPageActivity.this, "未开启定位权限,请手动到设置去开启权限", Toast.LENGTH_LONG).show();
                }
                break;
            case 100:
                if (null == grantResults || grantResults.length <= 0) {
                    Toast.makeText(AddPageActivity.this, "未开启存储权限,请手动到设置去开启权限", Toast.LENGTH_LONG).show();
                    return;
                }
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {//用户同意权限,执行我们的操作
                    Toast.makeText(AddPageActivity.this, "未开启存储权限,请手动到设置去开启权限", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
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


    private void requestPermission() {

        reqLocationPerssion();

        requestFileSavePermission();
    }

    @Override
    protected void onResume() {
        super.onResume();
        all = dao.list();
        adapter.setList(all);
        adapter.notifyDataSetChanged();

    }

    boolean firstRequest = true;

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (firstRequest) {
            firstRequest = false;
            requestPermission();
        }
    }

    private boolean reqLocationPerssion() {
        if (ContextCompat.checkSelfPermission(AddPageActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {//未开启定位权限
            //开启定位权限,200是标识码
            ActivityCompat.requestPermissions(AddPageActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);

            return false;
        } else {
            return true;
        }

    }

    private boolean requestFileSavePermission() {
        if (ContextCompat.checkSelfPermission(AddPageActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {//未开启定位权限
            //开启定位权限,200是标识码
            ActivityCompat.requestPermissions(AddPageActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);

            return false;
        } else {
            return true;
        }
    }
}
