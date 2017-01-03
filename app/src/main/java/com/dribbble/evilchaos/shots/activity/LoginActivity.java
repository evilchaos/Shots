package com.dribbble.evilchaos.shots.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.Toast;

import com.dribbble.evilchaos.shots.R;
import com.dribbble.evilchaos.shots.entity.AccessToken;
import com.dribbble.evilchaos.shots.entity.User;
import com.dribbble.evilchaos.shots.http.BaseCallback;
import com.dribbble.evilchaos.shots.http.OkHttpUtils;
import com.dribbble.evilchaos.shots.util.API;
import com.dribbble.evilchaos.shots.util.UserLocalData;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by liujiachao on 2016/12/30.
 */

public class LoginActivity extends BaseActivity {

    private OkHttpUtils okHttpUtils = OkHttpUtils.getInstance();
    private ImageButton closeLoginBtn;
    private WebView loginPage ;

    private AccessToken mAccessToken;

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

        String accessToken = UserLocalData.getToken(LoginActivity.this);

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
                            requestAccessToken(returnCode);
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

    private void requestAccessToken(String returnCode) {
        Map<String,String> mAuthMap = new HashMap<>();
        mAuthMap.put("client_id",API.CLIENT_ID);
        mAuthMap.put("client_secret",API.CLIENT_SECRET);
        mAuthMap.put("code",returnCode);
        mAuthMap.put("state",API.mState);
        okHttpUtils.post(API.dribbble_token_url,mAuthMap,new BaseCallback<AccessToken>() {
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
            public void onSuccess(Response response, AccessToken accessToken) {
                mAccessToken = accessToken;
                UserLocalData.putToken(LoginActivity.this,mAccessToken.getAccess_token());

                Toast.makeText(LoginActivity.this,"Authorization success",Toast.LENGTH_SHORT).show();
                CookieManager.getInstance().removeAllCookie();

                fetchUserInfo();
            }

            @Override
            public void OnError(Response response, int code, Exception e) {
                Toast.makeText(LoginActivity.this, "Please try again", Toast.LENGTH_SHORT).show();
            }
        } );

    }

    private void fetchUserInfo() {
        String accessToken = UserLocalData.getToken(LoginActivity.this);
        String url = API.dribbble_user_info + "?access_token=" + accessToken;

        okHttpUtils.get(url,new BaseCallback<User>() {
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
            public void onSuccess(Response response, User user) {
                saveUserInfo(user);
            }

            @Override
            public void OnError(Response response, int code, Exception e) {

            }
        });


    }

    private void saveUserInfo(User user) {
        UserLocalData.putUser(LoginActivity.this,user);
    }

    private String getCodeFromUrl(String url) {
        int startIndex = url.indexOf("code=") + "code=".length();
        int endIndex = url.indexOf("&state");
        String code = url.substring(startIndex,endIndex);
        Log.i("code = ",code);
        return code;
    }


}
