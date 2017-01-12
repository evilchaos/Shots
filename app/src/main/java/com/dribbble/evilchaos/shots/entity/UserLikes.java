package com.dribbble.evilchaos.shots.entity;

import java.io.Serializable;

/**
 * Created by liujiachao on 2017/1/12.
 */

public class UserLikes implements Serializable {

    int id;
    String created_at;
    ShotItem shot;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public ShotItem getShot() {
        return shot;
    }

    public void setShot(ShotItem shot) {
        this.shot = shot;
    }
}
