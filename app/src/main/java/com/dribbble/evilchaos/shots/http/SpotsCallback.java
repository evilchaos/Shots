package com.dribbble.evilchaos.shots.http;

import android.content.Context;

import dmax.dialog.SpotsDialog;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by liujiachao on 2017/1/11.
 */

public abstract class SpotsCallback<T> extends BaseCallback<T>  {

    private SpotsDialog mDialog;
    private Context mContext;

    public SpotsCallback(Context context) {
        super();
        mContext = context;
        initSpotsDialog();
    }

    private void initSpotsDialog() {
        mDialog = new SpotsDialog(mContext,"Loading....");
    }

    public void showDialog(){
        mDialog.show();
    }

    public void dismissDialog(){
        mDialog.dismiss();
    }

    @Override
    public void OnError(Response response, int code, Exception e) {

    }

    @Override
    public void onFailure(Request request, Exception e) {

    }

    @Override
    public void onResponse(Response response) {
        dismissDialog();
    }

    @Override
    public void onBeforeRequest(Request request) {
        showDialog();
    }
}
