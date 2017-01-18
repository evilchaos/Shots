package com.dribbble.evilchaos.shots.http;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by liujiachao on 2017/1/18.
 */

public abstract class SimpleCallback<T> extends BaseCallback<T> {

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
    public void OnError(Response response, int code, Exception e) {

    }
}
