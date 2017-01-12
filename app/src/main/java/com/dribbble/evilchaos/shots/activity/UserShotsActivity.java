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

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * Created by liujiachao on 2017/1/11.
 */

public class UserShotsActivity extends BaseInfoActivity {
    private UserShotsAdapter adapter;
    private List<ShotItem> shotItems = new ArrayList<>();

    @Override
    protected void getData() {
        String url = buildUrl();
        okHttpUtils.get(url,new SpotsCallback<List<ShotItem>>(UserShotsActivity.this){
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
                adapter = new UserShotsAdapter(this, shotItems);
                mRecycleView.setAdapter(adapter);
                mRecycleView.setLayoutManager(new LinearLayoutManager(this));
                //mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL_LIST));
                adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(UserShotsActivity.this,ShotsDetailActivity.class);
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
        return intent.getStringExtra("shots_url");
    }

    @Override
    protected void initLayoutId() {
        layoutId = R.layout.user_info_item;
    }
}
