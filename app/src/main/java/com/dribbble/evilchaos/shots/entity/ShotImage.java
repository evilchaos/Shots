package com.dribbble.evilchaos.shots.entity;

import java.io.Serializable;

/**
 * Created by liujiachao on 2016/12/12.
 */

public class ShotImage implements Serializable {
    String hidpi;
    String normal;
    String teaser;

    public String getHidpi() {
        return hidpi;
    }

    public void setHidpi(String hidpi) {
        this.hidpi = hidpi;
    }

    public String getNormal() {
        return normal;
    }

    public void setNormal(String normal) {
        this.normal = normal;
    }

    public String getTeaser() {
        return teaser;
    }

    public void setTeaser(String teaser) {
        this.teaser = teaser;
    }

    public String getHeightImageUri() {
        return hidpi != null ? hidpi : normal != null ? normal : teaser;
    }
}
