package com.dribbble.evilchaos.shots.http;

import com.google.gson.internal.$Gson$Types;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by liujiachao on 2016/12/12.
 */

public abstract class BaseCallback<T> {
    public Type mType;

    static Type getSuperclassTypeParameter(Class<?> subclass)
    {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class)
        {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }

    public BaseCallback() {
        mType = getSuperclassTypeParameter(getClass());
    }


    /**
     *发送请求前调用
     * @param request
     */
    public abstract void onBeforeRequest(Request request);

    /**
     * 请求成功时调用
     * @param response
     */
    public abstract void onResponse(Response response);

    /**
     * 请求失败时调用
     * @param request
     * @param e
     */
    public abstract void onFailure(Request request,Exception e);

    /**
     * 响应的状态码大于等于200，小于300时调用，表示响应成功
     * @param response
     * @param t
     */
    public abstract void onSuccess(Response response,T t);

    /**
     * 状态码为400，403，404等时调用，表示响应失败
     * @param response
     * @param code
     * @param e
     */
    public abstract void OnError(Response response,int code,Exception e);
}
