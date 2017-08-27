package com.bwie.search.appliction;

import android.app.Application;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;


/**
 * auther  ：王兵洋
 * date ：   2017/7/7
 * 类的作用 ：1.初始化第三方的讯飞语音 2.初始化QQ登陆
 * 实现思路 ：
 */

public class APPliction extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化讯飞语音
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=595ce6d0");
        //初始化QQ登陆
        PlatformConfig.setQQZone("1106201751", "wxegKgMywkkFEeoV");
        //初始化SDK防止发生意外
        UMShareAPI.get(this);
    }
}