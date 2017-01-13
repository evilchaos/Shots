package com.dribbble.evilchaos.shots.activity;

import android.support.v7.widget.LinearLayoutManager;

import com.dribbble.evilchaos.shots.R;
import com.dribbble.evilchaos.shots.adapter.FollowingAdapter;
import com.dribbble.evilchaos.shots.entity.FollowingData;
import com.dribbble.evilchaos.shots.http.SpotsCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * Created by liujiachao on 2017/1/13.
 */

public class FollowingActivity extends BaseInfoActivity {

    private List<FollowingData> followingDatas = new ArrayList<>();
    private FollowingAdapter adapter;
    @Override
    protected String getBaseUrl() {
        return intent.getStringExtra("followings_url");
    }

    @Override
    protected void initLayoutId() {
        layoutId = R.layout.user_follow;
    }

    @Override
    protected void getData() {
        String url = buildUrl();
        okHttpUtils.get(url,new SpotsCallback<List<FollowingData>>(FollowingActivity.this){
            @Override
            public void onSuccess(Response response, List<FollowingData> datas) {
                followingDatas = datas;
                showData();
            }
        });
    }

    @Override
    protected void showData() {
        switch (state) {
            case STATE_NORMAL:
                adapter = new FollowingAdapter(this, followingDatas);
                mRecycleView.setAdapter(adapter);
                mRecycleView.setLayoutManager(new LinearLayoutManager(this));
                //mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL_LIST));
                break;

            case STATE_REFRESH:
                adapter.clear();
                adapter.addData(followingDatas);
                mRecycleView.scrollToPosition(0);
                mRefreshLayout.finishRefresh();
                break;

            case STATE_MORE:
                adapter.addData(adapter.getDatas().size(), followingDatas);
                mRecycleView.scrollToPosition(adapter.getDatas().size());
                mRefreshLayout.finishRefreshLoadMore();
                break;
        }
    }
}
