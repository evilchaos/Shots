package com.dribbble.evilchaos.shots.adapter;

import android.content.Context;

import java.util.List;

/**
 * Created by liujiachao on 2016/12/12.
 */

public abstract class SimpleAdapter<T> extends BaseAdapter<T,BaseViewHolder> {
    public SimpleAdapter(Context context, int mLayoutResId) {
        super(context, mLayoutResId);
    }

    public SimpleAdapter(Context context, int mLayoutResId, List<T> datas) {
        super(context, mLayoutResId, datas);
    }
}
