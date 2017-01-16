package com.dribbble.evilchaos.shots.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.dribbble.evilchaos.shots.R;
import com.dribbble.evilchaos.shots.fragment.ProfileFragment;

/**
 * Created by liujiachao on 2017/1/16.
 */

public class ProfileActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        setContentView(R.layout.activity_profile);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment userFragment = new ProfileFragment();
        userFragment.setArguments(bundle);
        ft.replace(R.id.profile_fragment,userFragment);
        ft.commit();
    }
}
