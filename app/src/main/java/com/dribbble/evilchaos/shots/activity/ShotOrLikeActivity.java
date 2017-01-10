package com.dribbble.evilchaos.shots.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.dribbble.evilchaos.shots.R;
import com.dribbble.evilchaos.shots.adapter.BaseAdapter;
import com.dribbble.evilchaos.shots.adapter.BriefAdapter;
import com.dribbble.evilchaos.shots.entity.ShotItem;
import com.dribbble.evilchaos.shots.http.BaseCallback;
import com.dribbble.evilchaos.shots.http.OkHttpUtils;
import com.dribbble.evilchaos.shots.util.UserLocalData;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by liujiachao on 2017/1/9.
 */

public class ShotOrLikeActivity extends BaseActivity {

    public static final int REQUEST_CODE = 1;

    private TextView userTitleName;
    private MaterialRefreshLayout mRefreshLayout;
    private RecyclerView mRecycleView;
    private ImageView mImageView;

    private View mView;

    private int page = 1;
    private int per_page = 30;

    private OkHttpUtils okHttpUtils = OkHttpUtils.getInstance();
    private  static final int STATE_NORMAL=0;
    private  static final int STATE_REFRESH=1;
    private  static final int STATE_MORE=2;
    private int state = STATE_NORMAL;

    private String baseUrl;
    private String tag;
    private BriefAdapter adapter;

    private List<ShotItem> shotItems = new ArrayList<>();
    private String userName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info_item);
        Intent intent = getIntent();
        tag = intent.getStringExtra("tag");
        userName = intent.getStringExtra("username");
        getBaseUrl(intent);

        initViews();
    }

    private void getBaseUrl(Intent intent) {

        if (tag.equals("shots_url")  ) {
            baseUrl = intent.getStringExtra("shots_url");
        } else if (tag.equals("likes_url")) {
            baseUrl = intent.getStringExtra("likes_url");
        }
    }

    private void initViews() {
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
        state = STATE_REFRESH;
        getData();
    }

    private void loadMoreData() {
        page++;
        state = STATE_MORE;
        getData();
    }

    private void getData() {
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

    private void showData() {
        switch (state) {
            case STATE_NORMAL:
                adapter = new BriefAdapter(this, shotItems);
                mRecycleView.setAdapter(adapter);
                mRecycleView.setLayoutManager(new LinearLayoutManager(this));
                //mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL_LIST));
                adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(ShotOrLikeActivity.this,ShotsDetailActivity.class);
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

    private String buildUrl() {
        String accessToken = getAccessToken();
        return  ( baseUrl + "?page=" + String.valueOf(page) + "&per_page=" + String.valueOf(per_page) + "&access_token=" + accessToken );
    }

    private String getAccessToken() {
        String accessToken = UserLocalData.getToken(this);
        if (accessToken == null) {
            Intent intent = new Intent(this,LoginActivity.class);
            startActivityForResult(intent,REQUEST_CODE);
        }

        return  accessToken;
    }

}
