package com.dribbble.evilchaos.shots.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.dribbble.evilchaos.shots.R;
import com.dribbble.evilchaos.shots.adapter.CommListAdapter;
import com.dribbble.evilchaos.shots.adapter.CommentAdapter;
import com.dribbble.evilchaos.shots.entity.CommentItem;
import com.dribbble.evilchaos.shots.entity.ShotItem;
import com.dribbble.evilchaos.shots.http.BaseCallback;
import com.dribbble.evilchaos.shots.http.OkHttpUtils;
import com.dribbble.evilchaos.shots.util.API;
import com.dribbble.evilchaos.shots.util.TimeUtils;
import com.dribbble.evilchaos.shots.widget.AdaptableLinearLayout;
import com.dribbble.evilchaos.shots.widget.DividerItemDecoration;
import com.dribbble.evilchaos.shots.widget.DrawableCenterTextView;
import com.dribbble.evilchaos.shots.widget.MyLinearLayoutManager;
import com.dribbble.evilchaos.shots.widget.MyListView;
import com.dribbble.evilchaos.shots.widget.NestedListView;
import com.dribbble.evilchaos.shots.widget.NoUnderlineSpan;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyTypefaceSpan;
import uk.co.chrisjenx.calligraphy.TypefaceUtils;

/**
 * Created by liujiachao on 2016/12/24.
 */

public class ShotsDetailActivity extends BaseActivity {

    private OkHttpUtils okHttpUtils = OkHttpUtils.getInstance();

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
    //RecyclerView commRecyclerView;
    NestedListView commListView;

    List<CommentItem> commentItemList = new ArrayList<>();
    //CommentAdapter commentAdapter;
    CommListAdapter commListAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shot_detail_activity);
        Bundle bundle = getIntent().getExtras();
        shotItem = (ShotItem) bundle.getSerializable("shots_data");
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
        commListView = (NestedListView) findViewById(R.id.rv_comment);

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
        likeTextView.setText(String.valueOf(shotItem.getLikes_count()));
        commTextView.setText(String.valueOf(shotItem.getComments_count()));

        //处理description
        String html = shotItem.getDescription();

        if (html != null) {
            setDescriptionLinks(desTextView,html);
            removeHyperLinkUnderline(desTextView);
        } else {
            desTextView.setText("(No Description)");
        }

        //评论
         getComments();
    }

    private void getComments() {
        int comm_num = shotItem.getComments_count();
        String url = shotItem.getComments_url() +"?per_page=" + String.valueOf(comm_num) +  "&access_token=" + API.OAUTH_TOKEN;

        okHttpUtils.get(url,new BaseCallback<List<CommentItem>>() {
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
            public void onSuccess(Response response, List<CommentItem> commentItems) {
                commentItemList =  commentItems;
                showComment();
            }

            @Override
            public void OnError(Response response, int code, Exception e) {

            }
        });

    }

    private void showComment() {
        commListAdapter = new CommListAdapter(this,commentItemList);
//        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
//        layoutManager.setAutoMeasureEnabled(true);
//        commRecyclerView.setLayoutManager(layoutManager);
        commListView.setAdapter(commListAdapter);
        //setListViewHeightBasedOnChildren(commListView);
        //commRecyclerView.setLayoutManager(new MyLinearLayoutManager(this));
        //commRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
        //commRecyclerView.setNestedScrollingEnabled(false);
        //commRecyclerView.setHasFixedSize(false);
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
            Spannable spannable = (Spannable)tvDes.getText();
            NoUnderlineSpan noUnderlineSpan = new NoUnderlineSpan();
            spannable.setSpan(noUnderlineSpan,0,text.length(), Spanned.SPAN_MARK_MARK);
        }
    }

    private   void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }


}
