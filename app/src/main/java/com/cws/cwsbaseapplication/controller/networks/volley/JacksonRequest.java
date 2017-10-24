package com.cws.cwsbaseapplication.controller.networks.volley;

import android.text.TextUtils;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.cws.cwsbaseapplication.BuildConfig;
import com.cws.cwsbaseapplication.controller.utils.Constants;

import org.json.JSONObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Volley JSON Request. This class should be used only when we need JSON as reponse
 */
public class JacksonRequest<T> extends com.android.volley.toolbox.JsonRequest<T> implements IRequest {

    private Class<T> responseType;
    private String acceptType;
    private int method;
    /**
     * Content type for request.
     */
    private static final String PROTOCOL_CONTENT_TYPE =
            String.format("application/json; charset=%s", "");//PROTOCOL_CHARSET

    /**
     * Creates a new request.
     *
     * @param method        the HTTP method to use
     * @param url           URL to fetch the JSON from
     * @param jsonObject    A {@link Object} to post and convert into json as the request. Null is allowed and indicates no parameters will be posted along with request.
     * @param listener      Listener to receive the JSON response
     * @param errorListener Error listener, or null to ignore errors.
     */
    public JacksonRequest(int method, String url, JSONObject jsonObject, Class<T> responseType, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, url, (jsonObject == null) ? null : jsonObject.toString(), listener,
                errorListener);
        this.responseType = responseType;
        this.acceptType = Constants.CONTENT_TYPE_JSON;
        this.method = method;
        this.setRetryPolicy(getRetryPolicy());
    }

    /**
     * @param method
     * @param url
     * @param listener
     * @param errorListener
     */
    public JacksonRequest(int method, String url, JSONObject jsonObject, Class<T> responseType,
                          Response.Listener<T> listener, Response.ErrorListener errorListener,
                          String acceptType) {
        super(method, url, (jsonObject == null) ? null : jsonObject.toString(), listener,
                errorListener);
        this.responseType = responseType;
        this.acceptType = acceptType;
        this.method = method;
        this.setRetryPolicy(getRetryPolicy());
    }

    /*
     * (non-Javadoc)
     *
     * @see com.android.volley.Request#getHeaders()
     */
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = super.getHeaders();
        if (headers == null || headers.equals(Collections.emptyMap())) {
            headers = new HashMap<String, String>();
        }
        headers.put("Accept", this.acceptType);
        headers.put("Content-Type", getBodyContentType());
        headers.put("charset", Constants.CHARSET);
       // headers.put(REQUEST_HEADER_OTP, MercuryApplication.getInstance().getAccessOtp());
       // headers.put(REQUEST_HEADER_AUTH_TOKEN, MercuryApplication.getInstance().getAuthToken() != null ? MercuryApplication.getInstance().getAuthToken().getToken() : Constants.EMPTY_STRING);
     //   headers.put(REQUEST_HEADER_TXN_PIN,  MercuryApplication.getInstance().getAccessTxnPIN() != null ? MercuryApplication.getInstance().getAccessTxnPIN() : Constants.EMPTY_STRING);
        return headers;
    }

    @Override
    public Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            printDebugString(jsonString);
            HttpResponse httpResponse = Mapper.read(jsonString, responseType);
            if (httpResponse.getStatus().equals("SUCCESS")) {
                return Response.success((T) httpResponse.getResult(), HttpHeaderParser.parseCacheHeaders(response));
            } else {
                return Response.error(new CustomError(httpResponse.getCode(), httpResponse.getMessage()));
            }
        } catch (Exception e) {
            printDebugString(Log.getStackTraceString(e));
            return Response.error(new ParseError(e));
        }
    }

    //In your extended request class
    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError) {
        try {
            if (volleyError instanceof CustomError) {
                return volleyError;
            }
            if (volleyError.networkResponse != null && volleyError.networkResponse.data != null && volleyError.networkResponse.statusCode == 401) {
                String message = new String(volleyError.networkResponse.data);
                if(message.startsWith("{") && message.endsWith("}")){
                    HttpResponse httpResponse = Mapper.read(message, responseType);
                    message = httpResponse.getMessage();
                }
                CustomError error = new CustomError(volleyError.networkResponse.statusCode, message);
                volleyError = error;
            }
            return volleyError;
        } catch (Exception e) {
            printDebugString(Log.getStackTraceString(e));
            return new ParseError(e);
        }
    }

    /**
     * Overriding default timeout of Volley Network Socket Requests
     */
    @Override
    public RetryPolicy getRetryPolicy() {
        return new DefaultRetryPolicy(Constants.NETWORK_SOCKET_TIMEOUT,
                Constants.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    }

    @Override
    public String getBodyContentType() {
        return PROTOCOL_CONTENT_TYPE;
    }

    public static class HttpResponse<T> {
        private String status;
        private int code;
        private String message;
        private T result;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public T getResult() {
            return result;
        }

        public void setResult(T result) {
            this.result = result;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    private void printDebugString(String string){
        if(TextUtils.isEmpty(string))return;
        if(BuildConfig.DEBUG){
            Log.i("Debug String : === ", string);
        }
    }
}