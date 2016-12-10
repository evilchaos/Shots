package com.dribbble.evilchaos.shots.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cjj.MaterialRefreshLayout;
import com.dribbble.evilchaos.shots.R;

/**
 * Created by liujiachao on 2016/12/7.
 */

public class HomeFragment extends Fragment {

    private View mView;
    private MaterialRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.home_fragment,container,false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
    }

    private void initViews() {
        mRefreshLayout = (MaterialRefreshLayout)mView.findViewById(R.id.home_fragment_refresh);
        mRecyclerView = (RecyclerView)mView.findViewById(R.id.default_shot_content);
    }
}
