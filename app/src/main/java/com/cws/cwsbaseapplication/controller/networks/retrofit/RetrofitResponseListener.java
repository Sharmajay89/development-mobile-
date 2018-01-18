package com.cws.cwsbaseapplication.controller.networks.retrofit;


import javax.security.auth.callback.Callback;

/**
 * Created by cws on 13/11/17.
 */

public interface RetrofitResponseListener<T> extends Callback {
    void showProgress(boolean isProgress);
    <T> void onError(T error, String tag);
    <T> void onSuccess(T response, String tag);
}
