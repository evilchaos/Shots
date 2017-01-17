package com.dribbble.evilchaos.shots.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.dribbble.evilchaos.shots.R;
import com.dribbble.evilchaos.shots.adapter.BaseAdapter;
import com.dribbble.evilchaos.shots.adapter.FollowerAdapter;
import com.dribbble.evilchaos.shots.entity.FollowerData;
import com.dribbble.evilchaos.shots.http.SpotsCallback;
import com.dribbble.evilchaos.shots.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * Created by liujiachao on 2017/1/13.
 */

public class FollowerActivity extends BaseInfoActivity {

    private List<FollowerData> followerDatas = new ArrayList<>();
    private FollowerAdapter adapter;

    @Override
    protected String getBaseUrl() {
        return intent.getStringExtra("followers_url");
    }

    @Override
    protected void initLayoutId() {
        layoutId = R.layout.user_info_item;
    }

    @Override
    protected void setActivityTitle() {
        String name = intent.getStringExtra("name");
        String title = name + "'s" + " follower";
        mTitleName.setText(title);
    }

    @Override
    protected void getData() {
        String url = buildUrl();
        okHttpUtils.get(url,new SpotsCallback<List<FollowerData>>(FollowerActivity.this){
            @Override
            public void onSuccess(Response response, List<FollowerData> datas) {
                followerDatas = datas;
                showData();
            }
        });

    }

    @Override
    protected void showData() {
        switch (state) {
            case STATE_NORMAL:
                adapter = new FollowerAdapter(this, followerDatas);
                mRecycleView.setAdapter(adapter);
                mRecycleView.setLayoutManager(new LinearLayoutManager(this));
                mRecycleView.addItemDecoration(new DividerItemDecoration(FollowerActivity.this,DividerItemDecoration.VERTICAL_LIST));
                adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener(){
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(FollowerActivity.this,ProfileActivity.class);

                        Bundle bundle = new Bundle();
                        bundle.putString("user_name",followerDatas.get(position).getFollower().getUsername());
                        intent.putExtras(bundle);

                        startActivity(intent);
                    }
                });
                break;

            case STATE_REFRESH:
                adapter.clear();
                adapter.addData(followerDatas);
                mRecycleView.scrollToPosition(0);
                mRefreshLayout.finishRefresh();
                break;

            case STATE_MORE:
                adapter.addData(adapter.getDatas().size(), followerDatas);
                mRecycleView.scrollToPosition(adapter.getDatas().size());
                mRefreshLayout.finishRefreshLoadMore();
                break;
        }
    }
}
