package com.bwie.search.home.modul.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * auther  ：王兵洋
 * date ：   2017/7/11
 * 类的作用 ：  通过listView显示搜索的历史记录
 * 实现思路 ：
 */

public class MyListView extends ListView {

    public MyListView(Context context) {
        super(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
