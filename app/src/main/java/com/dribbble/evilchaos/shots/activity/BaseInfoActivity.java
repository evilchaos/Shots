package com.dribbble.evilchaos.shots.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.dribbble.evilchaos.shots.R;
import com.dribbble.evilchaos.shots.entity.ShotItem;
import com.dribbble.evilchaos.shots.http.BaseCallback;
import com.dribbble.evilchaos.shots.http.OkHttpUtils;

import java.util.List;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by liujiachao on 2017/1/10.
 */

public abstract class BaseInfoActivity extends BaseActivity {

    protected int layoutId;
    protected ImageView mImageView;
    protected TextView userTitleName;
    protected MaterialRefreshLayout mRefreshLayout;
    protected RecyclerView mRecycleView;

    private int page = 1;
    private int per_page = 30;

    protected OkHttpUtils okHttpUtils = OkHttpUtils.getInstance();
    protected static final int STATE_NORMAL=0;
    protected static final int STATE_REFRESH=1;
    protected static final int STATE_MORE=2;
    protected int state = STATE_NORMAL;

    protected String userName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initLayoutId();
        super.onCreate(savedInstanceState);
        initViews();
    }

    protected void initViews() {
        mImageView = (ImageView)findViewById(R.id.likes_back);

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        userTitleName = (TextView)findViewById(R.id.user_title_name);
        userTitleName.setText(userName);
        mRefreshLayout = (MaterialRefreshLayout)findViewById(R.id.user_info_refresh);
        mRecycleView = (RecyclerView)findViewById(R.id.user_info_rec);

        initRefreshLayout();

    }

    protected void initRefreshLayout() {
        mRefreshLayout.setLoadMore(true);
        mRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                loadMoreData();
            }

            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                refreshData();
            }
        });
    }

    protected void refreshData() {
        page = 1;
        state = STATE_REFRESH;
        getData();
    }

    protected void loadMoreData() {
        page++;
        state = STATE_MORE;
        getData();
    }

    protected void getData() {
        String url = buildUrl();
        okHttpUtils.get(url, new BaseCallback<List<ShotItem>>() {
            @Override
            public void onBeforeRequest(Request request) {

            }

            @Override
            public void onResponse(Response response) {

            }

            @Override
            public void onFailure(Request request, Exception e) {

            }

            @Override
            public void onSuccess(Response response, List<ShotItem> shotsData) {
                shotItems = shotsData;
                showData();
            }

            @Override
            public void OnError(Response response, int code, Exception e) {

            }
        });

    }

    protected abstract String buildUrl();

    protected abstract void showData();


    protected abstract void initLayoutId();
}
