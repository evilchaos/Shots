package com.dribbble.evilchaos.shots.activity;

import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;

import com.dribbble.evilchaos.shots.R;
import com.dribbble.evilchaos.shots.entity.Tab;
import com.dribbble.evilchaos.shots.fragment.CategoryFragment;
import com.dribbble.evilchaos.shots.fragment.HomeFragment;
import com.dribbble.evilchaos.shots.fragment.HomeShotFragment;
import com.dribbble.evilchaos.shots.fragment.ProfileFragment;
import com.dribbble.evilchaos.shots.fragment.SearchFragment;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private List<Tab> mTabs = new ArrayList<>(4);
    private LayoutInflater mInflater;
    private FragmentTabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);

        initTab();
    }

    private void initTab() {
        Tab home_tab = new Tab(R.drawable.selector_icon_home,"home",HomeShotFragment.class);
        //Tab search_tab = new Tab(R.drawable.selector_icon_search,"search",SearchFragment.class);
        Tab category_tab = new Tab(R.drawable.selector_icon_explore,"explore",CategoryFragment.class);
        Tab profile_tab = new Tab(R.drawable.selector_icon_person,"profile",ProfileFragment.class);

        mTabs.add(home_tab);
        //mTabs.add(search_tab);
        mTabs.add(category_tab);
        mTabs.add(profile_tab);

        mInflater = LayoutInflater.from(this);
        mTabHost = (FragmentTabHost)this.findViewById(android.R.id.tabhost);
        mTabHost.setup(this,getSupportFragmentManager(),R.id.content_layout);

        for (Tab tab : mTabs) {
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(tab.getTag());
            tabSpec.setIndicator(buildIndicator(tab));
            mTabHost.addTab(tabSpec,tab.getFragment(),null);
        }

        mTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
        mTabHost.setCurrentTab(0);
    }

    private View buildIndicator(Tab tab) {
        View view = mInflater.inflate(R.layout.tab_indicator,null);
        ImageView imageView = (ImageView)view.findViewById(R.id.icon_tab);
        imageView.setBackgroundResource(tab.getIcon());

        return view;
    }
}
