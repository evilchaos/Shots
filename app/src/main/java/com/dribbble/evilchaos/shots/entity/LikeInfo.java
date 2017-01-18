package com.dribbble.evilchaos.shots.entity;

import java.io.Serializable;

/**
 * Created by liujiachao on 2017/1/18.
 */

public class LikeInfo implements Serializable {
    String id;
    String created_at;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
