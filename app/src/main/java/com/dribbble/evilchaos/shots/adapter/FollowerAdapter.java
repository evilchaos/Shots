package com.dribbble.evilchaos.shots.adapter;

import android.content.Context;

import com.dribbble.evilchaos.shots.R;
import com.dribbble.evilchaos.shots.entity.FollowerData;
import com.dribbble.evilchaos.shots.entity.User;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by liujiachao on 2017/1/13.
 */

public class FollowerAdapter extends SimpleAdapter<FollowerData> {


    public FollowerAdapter(Context context, List<FollowerData> datas) {
        super(context, R.layout.user_follow, datas);
    }

    @Override
    public void bindData(BaseViewHolder viewHolder, FollowerData followerData) {
        User follower = followerData.getFollower();
        SimpleDraweeView mDraweeView = (SimpleDraweeView)viewHolder.getView(R.id.follow_author_avatar);
        String follower_image_url = follower.getAvatar_url();
        mDraweeView.setImageURI(follower_image_url);

        viewHolder.getTextView(R.id.follow_name).setText(follower.getName());
        viewHolder.getTextView(R.id.follow_username).setText(follower.getUsername());
        viewHolder.getTextView(R.id.follow_shots_num).setText(follower.getShots_count());
        viewHolder.getTextView(R.id.follow_follower_num).setText(follower.getFollowers_count());
    }
}
