package com.demo.karl.aweather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import interfaces.heweather.com.interfacesmodule.view.HeConfig;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 此为我个人申请的和风开发者账号，次数有限，仅用作我的demo，如果其他App建议另行申请
        HeConfig.init("HE1806212023441210", "c83ad40d99204f708f8eab931585aa32");
        HeConfig.switchToFreeServerNode();
    }
}
