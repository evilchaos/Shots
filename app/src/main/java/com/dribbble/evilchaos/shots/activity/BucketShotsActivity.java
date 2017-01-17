package com.dribbble.evilchaos.shots.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.dribbble.evilchaos.shots.R;
import com.dribbble.evilchaos.shots.adapter.BaseAdapter;
import com.dribbble.evilchaos.shots.adapter.BriefAdapter;
import com.dribbble.evilchaos.shots.adapter.UserShotsAdapter;
import com.dribbble.evilchaos.shots.entity.ShotItem;
import com.dribbble.evilchaos.shots.http.SpotsCallback;
import com.dribbble.evilchaos.shots.util.API;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * Created by liujiachao on 2017/1/17.
 */

public class BucketShotsActivity extends BaseInfoActivity {

    private BriefAdapter adapter;
    private List<ShotItem> shotItems = new ArrayList<>();
    @Override
    protected void getData() {
        String url = buildUrl();
        okHttpUtils.get(url, new SpotsCallback <List<ShotItem>>(BucketShotsActivity.this){
            @Override
            public void onSuccess(Response response, List<ShotItem> shotDatas) {
                shotItems = shotDatas;
                showData();
            }
        });
    }

    @Override
    protected void showData() {
        switch (state) {
            case STATE_NORMAL:
                adapter = new BriefAdapter(this, shotItems);
                mRecycleView.setAdapter(adapter);
                mRecycleView.setLayoutManager(new LinearLayoutManager(this));
                adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(BucketShotsActivity.this,ShotsDetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("shots_data",shotItems.get(position));
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
                break;

            case STATE_REFRESH:
                adapter.clear();
                adapter.addData(shotItems);
                mRecycleView.scrollToPosition(0);
                mRefreshLayout.finishRefresh();
                break;

            case STATE_MORE:
                adapter.addData(adapter.getDatas().size(), shotItems);
                mRecycleView.scrollToPosition(adapter.getDatas().size());
                mRefreshLayout.finishRefreshLoadMore();
                break;
        }
    }

    @Override
    protected String getBaseUrl() {
        String bucket_id = intent.getStringExtra("bucket_id");
        return API.url + "buckets/"  + bucket_id + "/" + "shots";
    }

    @Override
    protected void initLayoutId() {
        layoutId = R.layout.user_info_item;
    }

    @Override
    protected void setActivityTitle() {
        String name = intent.getStringExtra("bucket_name");
        mTitleName.setText(name);
    }
}
