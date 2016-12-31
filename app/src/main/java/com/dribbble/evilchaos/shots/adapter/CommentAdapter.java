package com.dribbble.evilchaos.shots.adapter;

import android.content.Context;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.widget.TextView;

import com.dribbble.evilchaos.shots.R;
import com.dribbble.evilchaos.shots.entity.CommentItem;
import com.dribbble.evilchaos.shots.util.TimeUtils;
import com.dribbble.evilchaos.shots.widget.NoUnderlineSpan;
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
        //viewHolder.getTextView(R.id.comment_content).setText(Html.fromHtml(commentItem.getBody()).toString().trim());

        TextView commTextView = viewHolder.getTextView(R.id.comment_content);
        String html = commentItem.getBody().toString().trim();

        setDescriptionLinks(commTextView,html);
        removeHyperLinkUnderline(commTextView);

        viewHolder.getTextView(R.id.comment_time).setText(TimeUtils.getTimeFromStandardFormat(commentItem.getUpdated_at()));

        viewHolder.getTextView(R.id.comment_like_number).setText(String.valueOf(commentItem.getLikes_count()));
    }

    private void setDescriptionLinks(TextView desTextView,String str) {
        Spanned text = Html.fromHtml(str);
        URLSpan[] currentSpans = text.getSpans(0, text.length(), URLSpan.class);
        SpannableString buffer = new SpannableString(text);
        Linkify.addLinks(buffer, Linkify.ALL);
        for (URLSpan span : currentSpans) {
            int end = text.getSpanEnd(span);
            int start = text.getSpanStart(span);
            buffer.setSpan(span, start, end, 0);
        }
        desTextView.setText(buffer);
        desTextView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void removeHyperLinkUnderline(TextView tvDes) {
        CharSequence text = tvDes.getText();
        if (text instanceof Spannable) {
            Spannable spannable = (Spannable) tvDes.getText();
            NoUnderlineSpan noUnderlineSpan = new NoUnderlineSpan();
            spannable.setSpan(noUnderlineSpan, 0, text.length(), Spanned.SPAN_MARK_MARK);
        }
    }
}
