package com.bwie.search.home.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.bwie.search.home.modul.utils.ConnectionUtil;

/**
 * 作者 ：   王兵洋
 * 时间 ：   2017/7/18
 * 类的作用 ：   进行网络判断  如果没有网络就跳转到网络设置界面。。。。
 * 实现思路 ：
 */

public class NetPanDuan extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         *      判断网络是否开启 如果没有开启跳转到设置界面，开启的话直接跳转到搜索界面
         */
        if (!ConnectionUtil.isConn(getApplicationContext())) {
            ConnectionUtil.setNetworkMethod(NetPanDuan.this);
        } else {
            Intent intent = new Intent(NetPanDuan.this, SearchActivity.class);
            startActivity(intent);
            finish();
        }
    }
    /**
     * 设置完网络后利用Activity生命周期的onRestart重新进行判断网络状态。网络开启跳转到设置界面。。。。。
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        if (!ConnectionUtil.isConn(getApplicationContext())) {
            ConnectionUtil.setNetworkMethod(NetPanDuan.this);
        } else {
            Intent intent = new Intent(NetPanDuan.this, SearchActivity.class);
            startActivity(intent);
            finish();
        }
    }
}