package com.dribbble.evilchaos.shots.adapter;

import android.content.Context;
import android.view.View;

import com.dribbble.evilchaos.shots.R;
import com.dribbble.evilchaos.shots.entity.ShotItem;
import com.dribbble.evilchaos.shots.util.TimeUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by liujiachao on 2017/1/12.
 */

public class UserShotsAdapter extends SimpleAdapter<ShotItem> {

    public UserShotsAdapter(Context context, List<ShotItem> datas) {
        super(context, R.layout.user_shot_brief, datas);
    }

    @Override
    public void bindData(BaseViewHolder viewHolder, ShotItem shotItem) {

        SimpleDraweeView shotDrawView = (SimpleDraweeView)viewHolder.getView(R.id.user_shot_pic);
        String shots_image_url = shotItem.getImages().getHeightImageUri();
        viewHolder.getView(R.id.shots_gif).setVisibility(shotItem.isAnimated() ? View.VISIBLE : View.INVISIBLE);
        shotDrawView.setImageURI(shots_image_url);

        viewHolder.getTextView(R.id.user_shot_title).setText(shotItem.getTitle());
        viewHolder.getTextView(R.id.user_shot_time).setText(TimeUtils.getTimeFromStandardFormat(shotItem.getUpdated_at()));

        viewHolder.getTextView(R.id.user_shots_like).setText(String.valueOf(shotItem.getLikes_count()));
        viewHolder.getTextView(R.id.user_shots_comment).setText(String.valueOf(shotItem.getComments_count()));
        viewHolder.getTextView(R.id.user_shots_view).setText(String.valueOf(shotItem.getViews_count()));
    }
}
