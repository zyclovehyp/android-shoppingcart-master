package com.zhangqie.shoppingcart;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.zhangqie.shoppingcart.dao.DictDao;

public class StartActivity extends AppCompatActivity {


    private static int SPLASH_DISPLAY_LENGHT = 2000;    //延迟2秒

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_NO_TITLE);//去掉标题
        fullScreen();
        setContentView(R.layout.activity_start);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(StartActivity.this, AddPageActivity.class);    //第二个参数即为执行完跳转后的Activity
                startActivity(intent);
                StartActivity.this.finish();   //关闭splashActivity，将其回收，否则按返回键会返回此界面
            }
        }, SPLASH_DISPLAY_LENGHT);
        initData();
    }

    private void fullScreen() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        final View decorView = getWindow().getDecorView();
        final int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int i) {
                if ((i & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                    decorView.setSystemUiVisibility(uiOptions);
                } else {

                }
            }
        });
    }

    private void initData() {
        SharedPreferences sharedPreferences =
                getSharedPreferences("init", MODE_PRIVATE);
        boolean inited = sharedPreferences.getBoolean("inited", false);

        if (!inited) {
            DictDao dictDao = new DictDao();
            dictDao.insertInitData();
            SharedPreferences.Editor editor =
                    sharedPreferences.edit();
            editor.putBoolean("inited", true);
            editor.commit();
        }


    }
}
