package com.dribbble.evilchaos.shots.util;

/**
 * Created by liujiachao on 2016/12/12.
 */

public class API {
    public static final String OAUTH_TOKEN = "bfca85ae4494581419c440a55187979b59affca89f581bc6fc9f07f6c3c71163";

    //后接access_token 如https://api.dribbble.com/v1/user?access_token=OAUTH_TOKEN
    // 后接username 如https://api.dribbble.com/v1/users/simplebits?access_token=OAUTH_TOKEN
    //所有API后面都要接ACCESS_TOKEN
    public static final String url = "https://api.dribbble.com/v1/";
    public static final String url_shots = "https://api.dribbble.com/v1/shots";
    public static final String url_buckets = "https://api.dribbble.com/v1/buckets";
    public static final String url_projects = "https://api.dribbble.com/v1/projects";

}
