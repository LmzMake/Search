package com.bwie.search.home.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.bwie.search.home.modul.utils.ToastUtils;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;
import java.util.Set;

public class QQLoginActivity extends AppCompatActivity {

    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //授权开始的回调
        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Set<String> set = data.keySet();
            SharedPreferences qq = getSharedPreferences("QQ", MODE_PRIVATE);
            SharedPreferences.Editor edit = qq.edit();
            for (String string : set) {
                String str = data.get(string);
                // 设置头像
                String touxiang = data.get("profile_image_url");
                edit.putString("头像", touxiang);
                // 设置昵称
                String nicheng = data.get("screen_name");
                edit.putString("昵称", nicheng);
                edit.putBoolean("状态", true);
                edit.commit();

                if (string.equals("province")) {
                }
                if (string.equals("city")) {
                }
                if (string.equals("gender")) {
                }
                if (string.equals("uid")) {
                }
                if (string.equals("yellow_vip_level")) {
                }
            }
            Intent intentqq = new Intent(QQLoginActivity.this, SearchActivity.class);
            startActivity(intentqq);
            finish();
            Toast toat = Toast.makeText(QQLoginActivity.this, "QQ已授权登录", Toast.LENGTH_SHORT);
            ToastUtils.showMyToast(toat, 80);
            finish();

        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {

            Toast toat = Toast.makeText(QQLoginActivity.this, "授权错误", Toast.LENGTH_SHORT);
            ToastUtils.showMyToast(toat, 80);
            //失败跳转到主界面
            startActivity(new Intent(QQLoginActivity.this, SearchActivity.class));
            finish();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast toat = Toast.makeText(QQLoginActivity.this, "授权失败", Toast.LENGTH_SHORT);
            ToastUtils.showMyToast(toat, 80);
            //失败跳转到主界面
            startActivity(new Intent(QQLoginActivity.this, SearchActivity.class));
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.QQ, umAuthListener);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
