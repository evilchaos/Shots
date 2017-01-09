package com.dribbble.evilchaos.shots.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.dribbble.evilchaos.shots.R;
import com.dribbble.evilchaos.shots.http.OkHttpUtils;

/**
 * Created by liujiachao on 2017/1/9.
 */

public class UserInfoActivity extends BaseActivity {

    private TextView userTitleName;
    private MaterialRefreshLayout mRefreshLayout;
    private RecyclerView mRecycleView;

    private View mView;

    private int page = 1;
    private int per_page = 30;
    private int type;

    private OkHttpUtils okHttpUtils = OkHttpUtils.getInstance();
    private  static final int STATE_NORMAL=0;
    private  static final int STATE_REFREH=1;
    private  static final int STATE_MORE=2;
    private int state = STATE_NORMAL;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info_item);
        Intent intent = getIntent();


        initViews();
    }

    private void initViews() {
        userTitleName = (TextView)findViewById(R.id.user_title_name);
        mRefreshLayout = (MaterialRefreshLayout)findViewById(R.id.user_info_refresh);
        mRecycleView = (RecyclerView)findViewById(R.id.user_info_rec);
        initRefreshLayout();
        
    }

    private void initRefreshLayout() {
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

    private void refreshData() {
        page = 1;
        state = STATE_REFREH;
        getData();
    }

    private void loadMoreData() {
    }

    private void getData() {
    }
}
