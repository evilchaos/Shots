package com.dribbble.evilchaos.shots.entity;

import java.io.Serializable;

/**
 * Created by liujiachao on 2016/12/7.
 */

public class Tab implements Serializable{
    private int icon;
    private String tag;
    private Class fragment;

    public Tab(int icon, String tag,Class fragment) {
        this.icon = icon;
        this.tag = tag;
        this.fragment = fragment;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public Class getFragment() {
        return fragment;
    }

    public void setFragment(Class fragment) {
        this.fragment = fragment;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
