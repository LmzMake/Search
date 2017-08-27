package com.bwie.search.home.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.bwie.search.R;
import com.bwie.search.home.modul.utils.ToastUtils;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;
import java.util.List;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView settingListV;

    private Button seetingButn;
    private List<String> mList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
        initData();
    }

    private void initData() {
        settingListV.setAdapter(new ArrayAdapter<String>(SettingActivity.this, android.R.layout.simple_list_item_1, mList));
        //设置界面需要的功能添加到集合  通过listView加载出来。
        mList.add("编辑资料");
        mList.add("账号和绑定设置");
        mList.add("黑名单");
        mList.add("清除缓存");
        mList.add("字体大小");
        mList.add("列表显示摘要");
        mList.add("非WiFi网络流量");
        mList.add("非WiFi网络播放提醒");
        mList.add("推送通知");
        mList.add("离线下载");
        mList.add("检查版本");
    }


    private void initView() {
        settingListV = (ListView) findViewById(R.id.settingListV);
        seetingButn = (Button) findViewById(R.id.seetingButn);
        seetingButn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.seetingButn:
                 //退出QQ登录
                UMShareAPI.get(SettingActivity.this).deleteOauth(SettingActivity.this, SHARE_MEDIA.QQ, null);
                SharedPreferences zt = getSharedPreferences("ZT", MODE_PRIVATE);
                zt.edit().putBoolean("zt", false).putString("sj", null).commit();
                Toast toast = Toast.makeText(SettingActivity.this, "已退出登录", Toast.LENGTH_SHORT);
                ToastUtils.showMyToast(toast, 80);
                SharedPreferences qq = getSharedPreferences("QQ", MODE_PRIVATE);
                qq.edit().putBoolean("状态", false).putString("头像", null).putString("昵称", null).commit();
                Intent intent = new Intent(SettingActivity.this, SearchActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}