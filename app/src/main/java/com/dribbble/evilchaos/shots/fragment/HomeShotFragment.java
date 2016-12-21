package com.dribbble.evilchaos.shots.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.dribbble.evilchaos.shots.R;
import com.dribbble.evilchaos.shots.adapter.BriefAdapter;
import com.dribbble.evilchaos.shots.adapter.ShotsAdapter;
import com.dribbble.evilchaos.shots.entity.ShotItem;
import com.dribbble.evilchaos.shots.http.BaseCallback;
import com.dribbble.evilchaos.shots.http.OkHttpUtils;
import com.dribbble.evilchaos.shots.util.API;
import com.dribbble.evilchaos.shots.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by liujiachao on 2016/12/20.
 */

public class HomeShotFragment extends Fragment {

    private OkHttpUtils okHttpUtils = OkHttpUtils.getInstance();
    private  static final int STATE_NORMAL=0;
    private  static final int STATE_REFREH=1;
    private  static final int STATE_MORE=2;
    private int state = STATE_NORMAL;

    private View mView;
    private MaterialRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private BriefAdapter adapter;

    private int page = 1;
    private int per_page = 30;

    private List<ShotItem> shotItems = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.home_fragment,container,false);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
    }

    private void initViews() {
        mRefreshLayout = (MaterialRefreshLayout)mView.findViewById(R.id.home_fragment_refresh);
        mRecyclerView = (RecyclerView)mView.findViewById(R.id.default_shot_content);
        initRefreshLayout();
        getData();
    }

    private void initRefreshLayout() {
        mRefreshLayout.setLoadMore(true);
        mRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                refreshData();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                loadMoreData();
            }
        });
    }

    private void refreshData() {
        page = 1;
        state = STATE_REFREH;
        getData();
    }

    private void loadMoreData() {
        page++;
        state = STATE_MORE;
        getData();
    }

    private void getData() {
        String url = API.url_shots + "?page=" + String.valueOf(page) + "&per_page=" + String.valueOf(per_page) + "&access_token=" + API.OAUTH_TOKEN;
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
                adapter = new BriefAdapter(getContext(),shotItems);
                mRecyclerView.setAdapter(adapter);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                //mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL_LIST));
                break;

            case STATE_REFREH:
                adapter.clear();
                adapter.addData(shotItems);
                mRecyclerView.scrollToPosition(0);
                mRefreshLayout.finishRefresh();
                break;

            case STATE_MORE:
                adapter.addData(adapter.getDatas().size(),shotItems);
                mRecyclerView.scrollToPosition(adapter.getDatas().size());
                mRefreshLayout.finishRefreshLoadMore();
        }
    }
}
