package com.bwie.search.home.modul.utils;

import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * auther  ：王兵洋
 * date ：   2017/7/11
 * 类的作用 ：   对Toast时间的长短进行控制
 * 实现思路 ：
 */

public class ToastUtils {

    /**
     * 设置Tosat时间
     *
     * @param toast
     * @param cnt
     */
    public static void showMyToast(final Toast toast, final int cnt) {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                toast.show();
            }
        }, 0, 3000);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                toast.cancel();
                timer.cancel();
            }
        }, cnt);
    }

}
