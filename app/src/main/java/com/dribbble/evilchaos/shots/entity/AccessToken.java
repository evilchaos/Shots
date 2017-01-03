package com.dribbble.evilchaos.shots.entity;

import java.io.Serializable;

/**
 * Created by liujiachao on 2017/1/3.
 */

public class AccessToken implements Serializable{
    String access_token;
    String token_type;
    String scope;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
