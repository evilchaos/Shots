package com.dribbble.evilchaos.shots.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.dribbble.evilchaos.shots.R;
import com.dribbble.evilchaos.shots.entity.ShotItem;
import com.dribbble.evilchaos.shots.util.TimeUtils;
import com.dribbble.evilchaos.shots.widget.DrawableCenterTextView;
import com.dribbble.evilchaos.shots.widget.NoUnderlineSpan;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

import uk.co.chrisjenx.calligraphy.CalligraphyTypefaceSpan;
import uk.co.chrisjenx.calligraphy.TypefaceUtils;

/**
 * Created by liujiachao on 2016/12/24.
 */

public class ShotsDetailActivity extends BaseActivity {
    
    ShotItem shotItem;
    SimpleDraweeView shotPicAvatar;
    TextView titleTextView;
    TextView userNameTextView;
    TextView timeTextView;
    SimpleDraweeView shotPicDrawee;
    DrawableCenterTextView expTextView;
    DrawableCenterTextView likeTextView;
    DrawableCenterTextView commTextView;
    TextView desTextView;
    RecyclerView commRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shot_detail_activity);
        Bundle bundle = getIntent().getExtras();
        shotItem = (ShotItem) bundle.getSerializable("shot_data");
        initViews();
    }

    private void initViews() {
        shotPicAvatar = (SimpleDraweeView)findViewById(R.id.detail_author_avatar);
        titleTextView = (TextView)findViewById(R.id.detail_shots_title);
        userNameTextView = (TextView)findViewById(R.id.detail_shots_user_name);
        timeTextView = (TextView) findViewById(R.id.detail_shots_time);
        shotPicDrawee = (SimpleDraweeView)findViewById(R.id.detail_shot_pic);

        expTextView = (DrawableCenterTextView)findViewById(R.id.detail_view_num);
        likeTextView = (DrawableCenterTextView)findViewById(R.id.detail_like_num);
        commTextView = (DrawableCenterTextView)findViewById(R.id.detail_comm_num);

        desTextView = (TextView)findViewById(R.id.detail_description);
        commRecyclerView = (RecyclerView)findViewById(R.id.rv_comment);

    }

    @Override
    protected void onStart() {
        super.onStart();
        fillShotsInfo();
    }

    private void fillShotsInfo() {
        shotPicAvatar.setImageURI(shotItem.getUser().getAvatar_url());
        titleTextView.setText(shotItem.getTitle());
        userNameTextView.setText(shotItem.getUser().getName());
        timeTextView.setText(TimeUtils.getTimeFromStandardFormat(shotItem.getUpdated_at()));

        String shots_image_url = shotItem.getImages().getHeightImageUri();

        if (!shotItem.isAnimated()) {
            shotPicDrawee.setImageURI(shots_image_url);
        } else {
            DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                    .setAutoPlayAnimations(true)
                    .setUri(shots_image_url)
                    .build();

            shotPicDrawee.setController(draweeController);
        }

        expTextView.setText(String.valueOf(shotItem.getViews_count()));
        expTextView.setText(String.valueOf(shotItem.getLikes_count()));
        expTextView.setText(String.valueOf(shotItem.getComments_count()));

        //处理description
        String html = shotItem.getDescription();
        if (html != null) {
            desTextView.setText(Html.fromHtml(html).toString().trim());
            removeHyperLinkUnderline(desTextView);
            setCustomFonts(desTextView);
            desTextView.setMovementMethod(ScrollingMovementMethod.getInstance());
            desTextView.setMovementMethod(LinkMovementMethod.getInstance());
        } else {
            desTextView.setVisibility(View.GONE);
        }

        //评论


    }

    private void removeHyperLinkUnderline(TextView tvDes) {
        CharSequence text = tvDes.getText();
        if (text instanceof Spannable) {
            Spannable spannable = (Spannable)tvDes.getText();
            NoUnderlineSpan noUnderlineSpan = new NoUnderlineSpan();
            spannable.setSpan(noUnderlineSpan,0,text.length(), Spanned.SPAN_MARK_MARK);
        }
    }

    private void setCustomFonts(TextView desTextView) {
        CalligraphyTypefaceSpan typefaceSpan = new CalligraphyTypefaceSpan(TypefaceUtils.load(getAssets(), "fonts/YatraOne-Regular.ttf"));
        SpannableStringBuilder sBuilder = (SpannableStringBuilder)desTextView.getText();
        sBuilder.setSpan(typefaceSpan,0,sBuilder.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        desTextView.setText(sBuilder, TextView.BufferType.SPANNABLE);
    }

}
