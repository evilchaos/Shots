package com.dribbble.evilchaos.shots.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.dribbble.evilchaos.shots.R;
import com.dribbble.evilchaos.shots.adapter.BaseAdapter;
import com.dribbble.evilchaos.shots.adapter.BriefAdapter;
import com.dribbble.evilchaos.shots.adapter.UserLikeAdapter;
import com.dribbble.evilchaos.shots.entity.ShotItem;
import com.dribbble.evilchaos.shots.entity.UserLikes;
import com.dribbble.evilchaos.shots.http.SpotsCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * Created by liujiachao on 2017/1/11.
 */

public class UserLikesActivity extends BaseInfoActivity  {
    private UserLikeAdapter adapter;

    private List<UserLikes> userLikes = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void getData() {
        String url = buildUrl();
        okHttpUtils.get(url,new SpotsCallback<List<UserLikes>>(UserLikesActivity.this){
            @Override
            public void onSuccess(Response response, List<UserLikes> shotDatas) {
                userLikes = shotDatas;
                showData();
            }
        });

    }

    @Override
    protected void showData() {
        switch (state) {
            case STATE_NORMAL:
                adapter = new UserLikeAdapter(this, userLikes);
                mRecycleView.setAdapter(adapter);
                mRecycleView.setLayoutManager(new LinearLayoutManager(this));
                adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(UserLikesActivity.this,ShotsDetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("shots_data",userLikes.get(position).getShot());
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
                break;

            case STATE_REFRESH:
                adapter.clear();
                adapter.addData(userLikes);
                mRecycleView.scrollToPosition(0);
                mRefreshLayout.finishRefresh();
                break;

            case STATE_MORE:
                adapter.addData(adapter.getDatas().size(), userLikes);
                mRecycleView.scrollToPosition(adapter.getDatas().size());
                mRefreshLayout.finishRefreshLoadMore();
                break;
        }
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
