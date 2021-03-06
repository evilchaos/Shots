package com.dribbble.evilchaos.shots.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dribbble.evilchaos.shots.R;
import com.dribbble.evilchaos.shots.activity.ProfileActivity;
import com.dribbble.evilchaos.shots.entity.ShotItem;
import com.dribbble.evilchaos.shots.util.TimeUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by liujiachao on 2016/12/20.
 */

public class BriefAdapter extends SimpleAdapter<ShotItem> {

    public BriefAdapter(Context context, List<ShotItem> datas) {
        super(context, R.layout.brief_shots_item,datas);
    }

    @Override
    public void bindData(BaseViewHolder viewHolder, final ShotItem shotItem) {

        SimpleDraweeView authorDraweeView = (SimpleDraweeView) viewHolder.getView(R.id.cv_author_avatar);
        authorDraweeView.setImageURI(shotItem.getUser().getAvatar_url());

        viewHolder.getTextView(R.id.shots_title).setText(shotItem.getTitle());
        viewHolder.getTextView(R.id.shots_user_name).setText(shotItem.getUser().getName());
        viewHolder.getTextView(R.id.shots_time).setText(TimeUtils.getTimeFromStandardFormat(shotItem.getUpdated_at()));

        SimpleDraweeView shotDrawView = (SimpleDraweeView)viewHolder.getView(R.id.drawee_view_shot_pic);
        String shots_image_url = shotItem.getImages().getHeightImageUri();

        viewHolder.getView(R.id.shots_gif).setVisibility(shotItem.isAnimated() ? View.VISIBLE : View.INVISIBLE);
        shotDrawView.setImageURI(shots_image_url);

        viewHolder.getTextView(R.id.tv_item_shots_like).setText(String.valueOf(shotItem.getLikes_count()));
        viewHolder.getTextView(R.id.tv_item_shots_comment).setText(String.valueOf(shotItem.getComments_count()));
        viewHolder.getTextView(R.id.tv_item_shots_view).setText(String.valueOf(shotItem.getViews_count()));

        viewHolder.getView(R.id.shots_header).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ProfileActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("user_name",shotItem.getUser().getUsername());
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }
}
