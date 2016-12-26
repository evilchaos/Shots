package com.dribbble.evilchaos.shots.widget;


import android.annotation.SuppressLint;
import android.text.TextPaint;
import android.text.style.UnderlineSpan;

import com.dribbble.evilchaos.shots.R;
import com.dribbble.evilchaos.shots.app.MyApplication;

/**
 * Created by liujiachao on 2016/12/16.
 */

@SuppressLint("ParcelCreator")
public class NoUnderlineSpan extends UnderlineSpan {
    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setUnderlineText(false);
    }
}
