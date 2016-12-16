package com.dribbble.evilchaos.shots.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dribbble.evilchaos.shots.R;
import com.dribbble.evilchaos.shots.adapter.MyFragmentAdapter;
import com.dribbble.evilchaos.shots.util.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liujiachao on 2016/12/7.
 */

public class CategoryFragment extends Fragment {
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private View mView;
    private TextView mCategoryTitle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.explore_layout,container,false);
        mViewPager = (ViewPager)mView.findViewById(R.id.vp_category);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mCategoryTitle = (TextView)mView.findViewById(R.id.category_title);
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Pacifico.ttf");
        mCategoryTitle.setTypeface(typeface);
        setupViewPager();
    }

    private void setupViewPager() {
        mTabLayout = (TabLayout)mView.findViewById(R.id.category_tabs);
        List<String> titles = new ArrayList<>();
        titles.add("Popular");
        titles.add("Recent");
        titles.add("Animated");
        titles.add("Attachments");
        titles.add("Debuts");
        titles.add("Playoffs");
        titles.add("Rebounds");
        titles.add("Teams");

        List<Fragment> fragments = new ArrayList<>();

        fragments.add(ListFragment.newInstance(Constant.POPULAR_TYPE));
        fragments.add(ListFragment.newInstance(Constant.RECENT_TYPE));
        fragments.add(ListFragment.newInstance(Constant.ANIMATED_TYPE));
        fragments.add(ListFragment.newInstance(Constant.ATTACHMENTS_TYPE));
        fragments.add(ListFragment.newInstance(Constant.DEBUTS_TYPE));
        fragments.add(ListFragment.newInstance(Constant.PLAYOFFS_TYPE));
        fragments.add(ListFragment.newInstance(Constant.REBOUNDS_TYPE));
        fragments.add(ListFragment.newInstance(Constant.TEAMS_SUCCESS));

        MyFragmentAdapter adapter = new MyFragmentAdapter(
                getChildFragmentManager(),fragments,titles);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(adapter);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

    }
}
