package com.dribbble.evilchaos.shots.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.dribbble.evilchaos.shots.R;
import com.dribbble.evilchaos.shots.http.BaseCallback;
import com.dribbble.evilchaos.shots.http.OkHttpUtils;
import com.dribbble.evilchaos.shots.util.UserLocalData;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by liujiachao on 2017/1/10.
 */

public abstract class BaseInfoActivity  extends BaseActivity {


    protected Intent intent;
    protected int layoutId;
    protected ImageView mImageView;
    protected TextView mTitleName;
    protected MaterialRefreshLayout mRefreshLayout;
    protected RecyclerView mRecycleView;

    protected int page = 1;
    protected int per_page = 30;
    public static final int REQUEST_CODE = 1;

    protected OkHttpUtils okHttpUtils = OkHttpUtils.getInstance();
    protected static final int STATE_NORMAL=0;
    protected static final int STATE_REFRESH=1;
    protected static final int STATE_MORE=2;
    protected int state = STATE_NORMAL;

    protected String name;
    protected String baseUrl;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initLayoutId();
        intent = getIntent();
        baseUrl = getBaseUrl();
        super.onCreate(savedInstanceState);
        initViews();
    }

    protected void initViews() {
        setContentView(layoutId);
        mImageView = (ImageView)findViewById(R.id.likes_back);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitleName = (TextView)findViewById(R.id.user_title_name);
        mRefreshLayout = (MaterialRefreshLayout)findViewById(R.id.user_info_refresh);
        mRecycleView = (RecyclerView)findViewById(R.id.user_info_rec);

        setActivityTitle();
        initRefreshLayout();
        getData();

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

    protected abstract void setActivityTitle();

    protected abstract void getData() ;

    protected abstract void showData();

    protected abstract String getBaseUrl();

    protected abstract void initLayoutId();

    protected String buildUrl() {
        String accessToken = getAccessToken();
        return  ( baseUrl + "?page=" + String.valueOf(page) + "&per_page=" + String.valueOf(per_page) + "&access_token=" + accessToken );
    }

    protected String getAccessToken() {

        String accessToken = UserLocalData.getToken(this);
        if (accessToken == null) {
            Intent intent = new Intent(this,LoginActivity.class);
            startActivityForResult(intent,REQUEST_CODE);
        }

        return  accessToken;
    }
}
