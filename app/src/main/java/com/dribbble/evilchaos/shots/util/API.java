package com.dribbble.evilchaos.shots.util;

/**
 * Created by liujiachao on 2016/12/12.
 */

public class API {
    public static final String OAUTH_TOKEN = "bfca85ae4494581419c440a55187979b59affca89f581bc6fc9f07f6c3c71163";
    public static final String CLIENT_ID = "1a7416baf70f17e7e389c41e78e84857350446cf5d6b9f5a99b649529f7b9207";

    //后接access_token 如https://api.dribbble.com/v1/user?access_token=OAUTH_TOKEN
    // 后接username 如https://api.dribbble.com/v1/users/simplebits?access_token=OAUTH_TOKEN
    //所有API后面都要接ACCESS_TOKEN
    public static final String url = "https://api.dribbble.com/v1/";
    public static final String url_shots = "https://api.dribbble.com/v1/shots";
    public static final String url_buckets = "https://api.dribbble.com/v1/buckets";
    public static final String url_projects = "https://api.dribbble.com/v1/projects";
    public static final String url_search = "https://dribbble.com/search/";
    public static final String dribbble_call_back = "http://api.lovejade.com:80";
    public static final String dribbble_auth_base = "https://dribbble.com/oauth/authorize";

    public static String mState;

    public static final String dribbble_login_url = dribbble_auth_base + "?" +
            "client_id=" + CLIENT_ID + "&redirect_url" + dribbble_call_back +
            "&scope=" + "public+write+comment+upload" + "&state=" + mState;


}
