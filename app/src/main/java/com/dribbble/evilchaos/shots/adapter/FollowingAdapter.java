package com.dribbble.evilchaos.shots.adapter;

import android.content.Context;

import com.dribbble.evilchaos.shots.R;
import com.dribbble.evilchaos.shots.entity.FollowingData;
import com.dribbble.evilchaos.shots.entity.User;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by liujiachao on 2017/1/13.
 */

public class FollowingAdapter extends SimpleAdapter<FollowingData> {

    public FollowingAdapter(Context context, List<FollowingData> datas) {
        super(context, R.layout.user_follow, datas);
    }

    @Override
    public void bindData(BaseViewHolder viewHolder, FollowingData followingData) {
        User followee = followingData.getFollowee();
        SimpleDraweeView mDraweeView = (SimpleDraweeView)viewHolder.getView(R.id.follow_author_avatar);
        String followee_image_url = followee.getAvatar_url();
        mDraweeView.setImageURI(followee_image_url);

        viewHolder.getTextView(R.id.follow_name).setText(followee.getName());
        viewHolder.getTextView(R.id.follow_username).setText(followee.getUsername());
        viewHolder.getTextView(R.id.follow_shots_num).setText(followee.getShots_count());
        viewHolder.getTextView(R.id.follow_follower_num).setText(followee.getFollowers_count());
    }
}
