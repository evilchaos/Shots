package com.dribbble.evilchaos.shots.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dribbble.evilchaos.shots.R;
import com.dribbble.evilchaos.shots.activity.LoginActivity;
import com.dribbble.evilchaos.shots.util.PreferencesUtils;
import com.dribbble.evilchaos.shots.util.UserLocalData;
import com.dribbble.evilchaos.shots.widget.AppleItemView;

/**
 * Created by liujiachao on 2016/12/7.
 */

public class ProfileFragment extends Fragment {

    public static final int REQUEST_CODE = 1;

    private Context mContext;
    private View mView;
    private TextView mUserTitle;
    private TextView mUserName;
    private TextView mFollowerNum;
    private TextView mFollowingNum;
    private TextView mLocation;
    private TextView mLikesNum;

    private AppleItemView mAppShots;
    private AppleItemView mAppLikes;
    private AppleItemView mAppProjects;
    private AppleItemView mAppBuckets;
    private AppleItemView mAppTeams;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       mView = inflater.inflate(R.layout.user_layout,container,false);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
    }

    private void initViews() {
        mContext = getContext();
        mUserTitle = (TextView)mView.findViewById(R.id.app_user_name);
        mUserName = (TextView)mView.findViewById(R.id.user_name);
        mFollowerNum = (TextView)mView.findViewById(R.id.user_followers_num);
        mFollowingNum = (TextView)mView.findViewById(R.id.user_followings_num);
        mLocation = (TextView)mView.findViewById(R.id.location);
        mLikesNum = (TextView)mView.findViewById(R.id.user_likes_num);

        mAppShots = (AppleItemView)mView.findViewById(R.id.app_shots);
        mAppLikes = (AppleItemView)mView.findViewById(R.id.app_likes);
        mAppProjects = (AppleItemView)mView.findViewById(R.id.app_projects);
        mAppBuckets = (AppleItemView)mView.findViewById(R.id.app_buckets);
        mAppTeams = (AppleItemView)mView.findViewById(R.id.app_Teams);
        
        getUserInfo();
    }

    private void getUserInfo() {
        String accessToken = getAccessToken();
        if (accessToken == null) {
            Intent intent = new Intent(mContext,LoginActivity.class);
            startActivityForResult(intent,REQUEST_CODE);
        } else {
            getData(accessToken);
        }
    }

    private void getData(String accessToken) {

    }

    private String getAccessToken() {
        return UserLocalData.getToken(mContext);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //得到新Activity关闭后返回的数据

    }
}
