package com.dribbble.evilchaos.shots.app;

import android.app.Application;
import android.content.Context;

import com.dribbble.evilchaos.shots.R;
import com.facebook.drawee.backends.pipeline.Fresco;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by liujiachao on 2016/12/9.
 */

public class MyApplication extends Application {
    public static Context mApplicationContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationContext = getApplicationContext();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        //.setDefaultFontPath("fonts/YatraOne-Regular.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
        Fresco.initialize(this);
    }
}
