package com.dribbble.evilchaos.shots.adapter;

import android.content.Context;
import android.text.Html;

import com.dribbble.evilchaos.shots.R;
import com.dribbble.evilchaos.shots.entity.CommentItem;
import com.dribbble.evilchaos.shots.util.TimeUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by liujiachao on 2016/12/26.
 */

public class CommentAdapter extends SimpleAdapter<CommentItem> {


    public CommentAdapter(Context context, List<CommentItem> datas) {
        super(context, R.layout.comment_item,datas);
    }

    @Override
    public void bindData(BaseViewHolder viewHolder, CommentItem commentItem) {
        SimpleDraweeView  commenterAvatar = (SimpleDraweeView)viewHolder.getView(R.id.comment_author_avatar);
        commenterAvatar.setImageURI(commentItem.getUser().getAvatar_url());

        viewHolder.getTextView(R.id.commenter_name).setText(commentItem.getUser().getName());
        viewHolder.getTextView(R.id.comment_content).setText(Html.fromHtml(commentItem.getBody()).toString().trim());

        viewHolder.getTextView(R.id.comment_time).setText(TimeUtils.getTimeFromStandardFormat(commentItem.getUpdated_at()));

        viewHolder.getTextView(R.id.comment_like_number).setText(String.valueOf(commentItem.getLikes_count()));
    }
}
