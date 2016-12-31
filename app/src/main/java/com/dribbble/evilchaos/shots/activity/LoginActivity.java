package com.dribbble.evilchaos.shots.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.Toast;

import com.dribbble.evilchaos.shots.R;
import com.dribbble.evilchaos.shots.util.API;

/**
 * Created by liujiachao on 2016/12/30.
 */

public class LoginActivity extends BaseActivity {

    public static final String DRIBBBLE_SHARE = "dri_share";

    private ImageButton closeLoginBtn;
    private WebView loginPage ;
    private SharedPreferences mDribbbleShare;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login);
        initViews();
        loadLoginWeb();
    }

    private void initViews() {
        closeLoginBtn = (ImageButton)findViewById(R.id.login_close);
        loginPage = (WebView)findViewById(R.id.webview_login);
        loginPage.getSettings().setJavaScriptEnabled(true);


        closeLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void loadLoginWeb() {
        mDribbbleShare = getSharedPreferences(DRIBBBLE_SHARE, Context.MODE_PRIVATE);
        String accessToken = mDribbbleShare.getString(DRIBBBLE_SHARE,null);

        if (TextUtils.isEmpty(accessToken)) {
            loginPage.setWebViewClient(new WebViewClient(){
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    if (url.startsWith(API.dribbble_call_back)) {
                        String returnCode = null;

                        if (url.contains("code=") ) {
                            returnCode = getCodeFromUrl(url);
                        }

                        if (!TextUtils.isEmpty(returnCode)) {
                            requestAccessToken();
                        } else {
                            Toast.makeText(LoginActivity.this,"Please try again",Toast.LENGTH_SHORT).show();
                        }

                        finish();
                        return true;
                    }
                    return super.shouldOverrideUrlLoading(view, url);
                }

            });

            loginPage.loadUrl(API.dribbble_login_url);
        } else {
            fetchUserInfo();
        }

    }

    private void fetchUserInfo() {
    }

    private void requestAccessToken() {

    }

    private String getCodeFromUrl(String url) {
        int startIndex = url.indexOf("code=") + "code=".length();
        int endIndex = url.indexOf("&state");
        String code = url.substring(startIndex,endIndex);
        Log.i("code = ",code);
        return code;
    }


}
