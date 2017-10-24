package com.cws.cwsbaseapplication.controller.networks.volley;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;

import java.util.Map;

/**
 * Created by avishvakarma on 16/5/17.
 */
public interface IRequest<T> {
    public static final String REQUEST_HEADER_AUTH_TOKEN = "X-Auth-Token";
    public static final String REQUEST_HEADER_OTP = "X-HEADER-OTP";
    public static final String REQUEST_HEADER_TXN_PIN = "X-HEADER-TXN-PIN";

    public Map<String, String> getHeaders() throws AuthFailureError;
    public RetryPolicy getRetryPolicy();
    public Response<T> parseNetworkResponse(NetworkResponse response);
}
