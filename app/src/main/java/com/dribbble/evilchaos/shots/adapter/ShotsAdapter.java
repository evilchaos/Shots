package com.dribbble.evilchaos.shots.adapter;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.dribbble.evilchaos.shots.R;
import com.dribbble.evilchaos.shots.app.MyApplication;
import com.dribbble.evilchaos.shots.entity.ShotItem;
import com.dribbble.evilchaos.shots.widget.NoUnderlineSpan;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import uk.co.chrisjenx.calligraphy.CalligraphyTypefaceSpan;
import uk.co.chrisjenx.calligraphy.TypefaceUtils;

/**
 * Created by liujiachao on 2016/12/12.
 */

public class ShotsAdapter extends SimpleAdapter<ShotItem> {

    public ShotsAdapter(Context context, List<ShotItem> datas) {
        super(context, R.layout.shot_item,datas);
    }

    @Override
    public void bindData(BaseViewHolder viewHolder, ShotItem shotItem) {

        viewHolder.getTextView(R.id.likes_number).setText(String.format("%d likes",shotItem.getLikes_count()));
        viewHolder.getTextView(R.id.comments_number).setText(String.format("%d comments",shotItem.getComments_count()));
        viewHolder.getTextView(R.id.buckets_number).setText(String.format("%d buckets",shotItem.getBuckets_count()));
        viewHolder.getTextView(R.id.views_number).setText(String.format("%d views",shotItem.getViews_count()));

        String html = shotItem.getDescription();
        TextView tvDes = viewHolder.getTextView(R.id.description);

        if (html != null) {
            tvDes.setText(Html.fromHtml(html).toString().trim());
            removeHyperLinkUnderline(tvDes);
            tvDes.setMovementMethod(ScrollingMovementMethod.getInstance());
            tvDes.setMovementMethod(LinkMovementMethod.getInstance());
        } else {
            tvDes.setVisibility(View.GONE);
        }

        SimpleDraweeView authorDraweeView = (SimpleDraweeView) viewHolder.getView(R.id.cv_author_img);
        authorDraweeView.setImageURI(shotItem.getUser().getAvatar_url());
        viewHolder.getTextView(R.id.tv_works_name).setText(shotItem.getTitle());

        String name = "by " + shotItem.getUser().getName();
        SpannableString spanName = changeNameColor(name);
        viewHolder.getTextView(R.id.tv_author_name).setText(spanName);


        SimpleDraweeView shotDrawView = (SimpleDraweeView)viewHolder.getView(R.id.drawee_view_shot);
        String shots_image_url = shotItem.getImages().getHeightImageUri();

        if (!shotItem.isAnimated()) {
            shotDrawView.setImageURI(shots_image_url);
        } else {
            DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                    .setAutoPlayAnimations(true)
                    .setUri(shots_image_url)
                    .build();

            shotDrawView.setController(draweeController);
        }
    }

    private void removeHyperLinkUnderline(TextView tvDes) {
        CharSequence text = tvDes.getText();
        if (text instanceof Spannable) {
            Spannable spannable = (Spannable)tvDes.getText();
            NoUnderlineSpan noUnderlineSpan = new NoUnderlineSpan();
            spannable.setSpan(noUnderlineSpan,0,text.length(),Spanned.SPAN_MARK_MARK);
        }
    }

    private SpannableString changeNameColor(String name) {
        SpannableString spanString = new SpannableString(name);
        ForegroundColorSpan span = new ForegroundColorSpan(MyApplication.mApplicationContext.getResources().getColor(R.color.colorAccent));
        spanString.setSpan(span,3,spanString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spanString;
    }
}
