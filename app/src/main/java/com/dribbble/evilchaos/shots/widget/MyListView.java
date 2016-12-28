package com.dribbble.evilchaos.shots.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import java.util.concurrent.ExecutionException;

/**
 * Created by liujiachao on 2016/12/28.
 */

public class MyListView extends ListView {

    public MyListView(Context context) {
        super(context);
    }

    public MyListView(Context context,AttributeSet attrs) {
        super(context,attrs);
    }

    public MyListView(Context context,AttributeSet attrs,int defStyle) {
        super(context,attrs,defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expendSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2
        ,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expendSpec);
    }
}
