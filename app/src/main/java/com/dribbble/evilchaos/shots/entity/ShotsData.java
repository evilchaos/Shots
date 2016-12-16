package com.dribbble.evilchaos.shots.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liujiachao on 2016/12/14.
 */

public class ShotsData implements Serializable{
    List<ShotItem> results;

    public List<ShotItem> getResults() {
        return results;
    }

    public void setResults(List<ShotItem> results) {
        this.results = results;
    }
}
