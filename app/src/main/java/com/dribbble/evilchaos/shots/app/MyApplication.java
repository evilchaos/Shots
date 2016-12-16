package com.dribbble.evilchaos.shots.app;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by liujiachao on 2016/12/9.
 */

public class MyApplication extends Application {
    public static Context mApplicationContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationContext = getApplicationContext();
        Fresco.initialize(this);
    }
}
