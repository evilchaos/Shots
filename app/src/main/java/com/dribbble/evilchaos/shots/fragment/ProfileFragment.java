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
import com.dribbble.evilchaos.shots.entity.User;
import com.dribbble.evilchaos.shots.http.BaseCallback;
import com.dribbble.evilchaos.shots.http.OkHttpUtils;
import com.dribbble.evilchaos.shots.util.API;
import com.dribbble.evilchaos.shots.util.UserLocalData;
import com.dribbble.evilchaos.shots.widget.AppleItemView;
import com.facebook.drawee.view.SimpleDraweeView;

import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by liujiachao on 2016/12/7.
 */

public class ProfileFragment extends Fragment implements View.OnClickListener {

    public static final int REQUEST_CODE = 1;
    public OkHttpUtils okHttpUtils = OkHttpUtils.getInstance();

    private Context mContext;
    private View mView;
    private TextView mUserTitle;
    private SimpleDraweeView mDraweeView;
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
        mDraweeView = (SimpleDraweeView)mView.findViewById(R.id.user_img);
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
        String url = API.dribbble_user_info + "?access_token=" + accessToken;
        okHttpUtils.get(url,new BaseCallback<User>() {
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
            public void onSuccess(Response response, User user) {
                saveUserInfo(user);
                showUserInfo(user);
            }

            @Override
            public void OnError(Response response, int code, Exception e) {

            }
        });

    }

    private void saveUserInfo(User user) {
        UserLocalData.putUser(mContext,user);
    }

    private void showUserInfo(User user) {
        mUserTitle.setText(user.getUsername());
        mDraweeView.setImageURI(user.getAvatar_url());
        mUserName.setText(user.getUsername());
        mFollowerNum.setText(String.valueOf(user.getFollowers_count()));
        mFollowingNum.setText(String.valueOf(user.getFollowings_count()));

        if (mLocation == null) {
            mFollowingNum.setVisibility(View.GONE);
        } else {
            mLocation.setText(user.getLocation());
        }

        mLikesNum.setText(String.valueOf(user.getLikes_received_count()));
        mAppShots.setItemNum(String.valueOf(user.getShots_count()));
        mAppShots.setTitleText("shot");
        mAppLikes.setItemNum(String.valueOf(user.getLikes_count()));
        mAppLikes.setTitleText("like");
        mAppProjects.setItemNum(String.valueOf(user.getProjects_count()));
        mAppProjects.setTitleText("project");
        mAppBuckets.setItemNum(String.valueOf(user.getBuckets_count()));
        mAppBuckets.setTitleText("bucket");
        mAppTeams.setItemNum(String.valueOf(user.getTeams_count()));
        mAppTeams.setTitleText("team");

        mAppShots.setOnClickListener(this);
        mAppLikes.setOnClickListener(this);
        mAppProjects.setOnClickListener(this);
        mAppBuckets.setOnClickListener(this);
        mAppTeams.setOnClickListener(this);

    }

    private String getAccessToken() {
        return UserLocalData.getToken(mContext);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //得到新Activity关闭后返回的数据
        User user = UserLocalData.getUser(mContext);
        if (user != null) {
            showUserInfo(user);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.app_shots:

                break;
            case R.id.app_likes:
                //传tag
                break;
            case R.id.app_projects:
                break;
            case R.id.app_buckets:
                break;
            case R.id.app_Teams:
                break;

            default:
                break;
        }
    }
}
