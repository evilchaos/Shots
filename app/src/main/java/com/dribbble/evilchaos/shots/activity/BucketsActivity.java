package com.dribbble.evilchaos.shots.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.dribbble.evilchaos.shots.R;
import com.dribbble.evilchaos.shots.adapter.BaseAdapter;
import com.dribbble.evilchaos.shots.adapter.BucketsAdapter;
import com.dribbble.evilchaos.shots.entity.BucketData;
import com.dribbble.evilchaos.shots.http.SpotsCallback;
import com.dribbble.evilchaos.shots.widget.DividerDecorationForBuckets;
import com.dribbble.evilchaos.shots.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * Created by liujiachao on 2017/1/13.
 */

public class BucketsActivity extends BaseInfoActivity {

    List<BucketData> mBucketDatas = new ArrayList<>();
    BucketsAdapter adapter;

    @Override
    protected String getBaseUrl() {
        return intent.getStringExtra("buckets_url");
    }

    @Override
    protected void initLayoutId() {
        layoutId = R.layout.user_info_item;
    }

    @Override
    protected void getData() {
        String url = buildUrl();
        okHttpUtils.get(url,new SpotsCallback<List<BucketData>>(BucketsActivity.this) {
            @Override
            public void onSuccess(Response response, List<BucketData> datas) {
                mBucketDatas =  datas;
                showData();
            }
        });
    }

    @Override
    protected void setActivityTitle() {
        String name = intent.getStringExtra("name");
        String title = name + "'s" + " buckets";
        mTitleName.setText(title);
    }

    @Override
    protected void showData() {
        switch (state) {
            case STATE_NORMAL:
                adapter = new BucketsAdapter(this, mBucketDatas);
                mRecycleView.setAdapter(adapter);
                mRecycleView.setLayoutManager(new LinearLayoutManager(this));
                mRecycleView.addItemDecoration(new DividerDecorationForBuckets(BucketsActivity.this, DividerDecorationForBuckets.VERTICAL_LIST));
                adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(BucketsActivity.this,BucketShotsActivity.class);
                        intent.putExtra("bucket_name",mBucketDatas.get(position).getName());
                        intent.putExtra("bucket_id",String.valueOf(mBucketDatas.get(position).getId()));
                        startActivity(intent);
                    }
                });
                break;

            case STATE_REFRESH:
                adapter.clear();
                adapter.addData(mBucketDatas);
                mRecycleView.scrollToPosition(0);
                mRefreshLayout.finishRefresh();
                break;

            case STATE_MORE:
                adapter.addData(adapter.getDatas().size(), mBucketDatas);
                mRecycleView.scrollToPosition(adapter.getDatas().size());
                mRefreshLayout.finishRefreshLoadMore();
                break;
        }
    }
}
