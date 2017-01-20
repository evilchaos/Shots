package com.dribbble.evilchaos.shots.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
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
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.dribbble.evilchaos.shots.R;
import com.dribbble.evilchaos.shots.adapter.CommListAdapter;
import com.dribbble.evilchaos.shots.adapter.CommentAdapter;
import com.dribbble.evilchaos.shots.entity.CommentItem;
import com.dribbble.evilchaos.shots.entity.LikeInfo;
import com.dribbble.evilchaos.shots.entity.ShotItem;
import com.dribbble.evilchaos.shots.http.BaseCallback;
import com.dribbble.evilchaos.shots.http.OkHttpUtils;
import com.dribbble.evilchaos.shots.http.SimpleCallback;
import com.dribbble.evilchaos.shots.util.API;
import com.dribbble.evilchaos.shots.util.InputMethodUtils;
import com.dribbble.evilchaos.shots.util.TimeUtils;
import com.dribbble.evilchaos.shots.util.UserLocalData;
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

public class ShotsDetailActivity extends BaseActivity implements View.OnClickListener{

    private OkHttpUtils okHttpUtils = OkHttpUtils.getInstance();
    public static final int DELETE_NO_CONTENT = 204;
    public static final int POST_LIKE_CREATED = 201;

    private ShotItem shotItem;
    private SimpleDraweeView shotPicAvatar;
    private TextView titleTextView;
    private TextView userNameTextView;
    private TextView timeTextView;
    private SimpleDraweeView shotPicDrawee;
    private DrawableCenterTextView expTextView;
    private DrawableCenterTextView likeTextView;
    private DrawableCenterTextView commTextView;
    private TextView desTextView;
    private RecyclerView commRecyclerView;
    private TextView tvBack;
    private EditText mInputBox;
    private LinearLayout mInputLayout;

    private LinearLayout detailShotsHeader;
    //NestedListView commListView;

    List<CommentItem> commentItemList = new ArrayList<>();
    CommentAdapter commentAdapter;
    private String accessToken;
    private boolean isLike = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shot_detail_activity);
        Bundle bundle = getIntent().getExtras();
        shotItem = (ShotItem) bundle.getSerializable("shots_data");
        accessToken = UserLocalData.getToken(this);
        initViews();
    }

    private void initViews() {
        detailShotsHeader = (LinearLayout)findViewById(R.id.detail_shots_header);
        shotPicAvatar = (SimpleDraweeView)findViewById(R.id.detail_author_avatar);
        titleTextView = (TextView)findViewById(R.id.detail_shots_title);
        userNameTextView = (TextView)findViewById(R.id.detail_shots_user_name);
        timeTextView = (TextView) findViewById(R.id.detail_shots_time);
        shotPicDrawee = (SimpleDraweeView)findViewById(R.id.detail_shot_pic);
        tvBack = (TextView)findViewById(R.id.back);

        expTextView = (DrawableCenterTextView)findViewById(R.id.detail_view_num);
        likeTextView = (DrawableCenterTextView)findViewById(R.id.detail_like_num);
        commTextView = (DrawableCenterTextView)findViewById(R.id.detail_comm_num);
        mInputBox = (EditText)findViewById(R.id.ed_input);
        mInputLayout = (LinearLayout)findViewById(R.id.ll_input);

        desTextView = (TextView)findViewById(R.id.detail_description);
        commRecyclerView = (RecyclerView) findViewById(R.id.rv_comment);

        tvBack.setOnClickListener(this);
        likeTextView.setOnClickListener(this);
        commTextView.setOnClickListener(this);

        fillShotsInfo();

    }

    private void fillShotsInfo() {

        if (shotItem.getUser() == null) {
            detailShotsHeader.setVisibility(View.GONE);
        } else {
            shotPicAvatar.setImageURI(shotItem.getUser().getAvatar_url());
            userNameTextView.setText(shotItem.getUser().getName());
        }

        titleTextView.setText(shotItem.getTitle());
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

        likeTextView.setText(String.valueOf(shotItem.getLikes_count()));
        expTextView.setText(String.valueOf(shotItem.getViews_count()));
        commTextView.setText(String.valueOf(shotItem.getComments_count()));

        if (accessToken != null) {
            setLikeTextViewStyle();
        }

        setLikeTextViewStyle();
        setDescriptionContent();
        //评论
        getComments();

    }

    private void setDescriptionContent() {

        String html = shotItem.getDescription();
        if (html != null) {
            setDescriptionLinks(desTextView,html);
            removeHyperLinkUnderline(desTextView);
        } else {
            desTextView.setText("(No Description)");
        }
    }

    private void setLikeTextViewStyle() {
        //判断该shots是否被用户like,改变like按钮的样式
        String url = buildLikeRequestUrl();
        okHttpUtils.get(url, new SimpleCallback<LikeInfo>() {

            @Override
            public void onSuccess(Response response, LikeInfo likeInfo) {
                setLike();
                setLikeStyle();
            }

            @Override
            public void OnError(Response response, int code, Exception e) {
                super.OnError(response, code, e);
                setUnlike();
            }
        });

    }

    private void showComment() {
        commentAdapter = new CommentAdapter(this,commentItemList);
        commRecyclerView.setAdapter(commentAdapter);
        commRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        commRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
        commRecyclerView.setNestedScrollingEnabled(false);
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

    private void getComments() {
        int comm_num = shotItem.getComments_count();
        String url = shotItem.getComments_url() +"?per_page=" + String.valueOf(comm_num) +  "&access_token=" + API.OAUTH_TOKEN;

        okHttpUtils.get(url,new SimpleCallback<List<CommentItem>>() {

            @Override
            public void onSuccess(Response response, List<CommentItem> commentItems) {
                commentItemList =  commentItems;
                showComment();
            }
        });

    }

    private void postLikeTag() {
        String url = buildLikeRequestUrl();
        okHttpUtils.post(url, null, new SimpleCallback<LikeInfo>() {
            @Override
            public void onSuccess(Response response, LikeInfo likeInfo) {
                if (response.code() == POST_LIKE_CREATED) {
                    //在后台操作成功后的回调，比如改变一些控件相关参数，比如评论数量等等
                    Log.i("tag","like success");
                }
            }
        });
    }

    private void deleteLikeTag() {
        String url = buildLikeRequestUrl();
        okHttpUtils.delete(url,new SimpleCallback<Object>(){
            @Override
            public void onSuccess(Response response, Object o) {
                if (response.code() == DELETE_NO_CONTENT) {
                    //在后台操作成功后的回调，比如改变一些控件相关参数，比如评论数量等等
                    Log.i("tag","unlike success");
                }
            }
        });
    }

    private void setLikeStyle() {
        likeTextView.setTextColor(getResources().getColor(R.color.project_pink));
        Drawable leftDrawable = getResources().getDrawable(R.drawable.iv_like_pink_24dp);
        leftDrawable.setBounds(0, 0, leftDrawable.getMinimumWidth(), leftDrawable.getMinimumHeight());
        likeTextView.setCompoundDrawables(leftDrawable,null,null,null);
    }

    private void setUnlikeStyle() {
        likeTextView.setTextColor(getResources().getColor(R.color.text_grey));
        Drawable leftDrawable = getResources().getDrawable(R.drawable.iv_like_grey_24dp);
        leftDrawable.setBounds(0, 0, leftDrawable.getMinimumWidth(), leftDrawable.getMinimumHeight());
        likeTextView.setCompoundDrawables(leftDrawable,null,null,null);
    }

    private String buildLikeRequestUrl() {
        return ( API.url + "shots/" + String.valueOf(shotItem.getId())
                + "/like" + "?access_token=" + accessToken ) ;
    }

    private void setLike() {
        isLike = true;
    }

    private void setUnlike() {
        isLike = false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;

            case R.id.detail_like_num:
                if (accessToken == null) {
                    Intent intent = new Intent(this,LoginActivity.class);
                    startActivity(intent);
                } else if (isLike){
                    //delete like
                    setUnlike();
                    decreaseLikeNum();
                    setUnlikeStyle();
                    deleteLikeTag();
                } else {
                    //post like
                    setLike();
                    increaseLikeNum();
                    setLikeStyle();
                    postLikeTag();
                }
                break;

            case R.id.detail_comm_num:
                showInputBox();
        }
    }

    private void showInputBox() {
        mInputLayout.setVisibility(View.VISIBLE);
        InputMethodUtils.showInputMethod(mInputBox);
    }

    private int getLikeNumFromTextView() {
        return Integer.parseInt(likeTextView.getText().toString());
    }

    private void increaseLikeNum() {
        int currNum = getLikeNumFromTextView();
        currNum++;
        likeTextView.setText(String.valueOf(currNum));
    }

    private void decreaseLikeNum() {
        int currNum = getLikeNumFromTextView();
        currNum--;
        likeTextView.setText(String.valueOf(currNum));
    }

}
