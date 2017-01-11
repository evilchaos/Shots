package com.dribbble.evilchaos.shots.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.dribbble.evilchaos.shots.R;
import com.dribbble.evilchaos.shots.adapter.BaseAdapter;
import com.dribbble.evilchaos.shots.adapter.BriefAdapter;
import com.dribbble.evilchaos.shots.entity.ShotItem;
import com.dribbble.evilchaos.shots.http.SpotsCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * Created by liujiachao on 2017/1/11.
 */

public class UserLikesActivity extends BaseInfoActivity  {
    private BriefAdapter adapter;

    private List<ShotItem> shotItems = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void getData() {
        String url = buildUrl();
        okHttpUtils.get(url,new SpotsCallback<List<ShotItem>>(UserLikesActivity.this){
            @Override
            public void onSuccess(Response response, List<ShotItem> shotDatas) {
                shotItems = shotDatas;
                showData();
            }
        });

    }

    @Override
    protected void showData() {

    }


    @Override
    protected String getBaseUrl() {
        return intent.getStringExtra("likes_url");
    }

    @Override
    protected void initLayoutId() {
        layoutId = R.layout.user_info_item;
    }
}
