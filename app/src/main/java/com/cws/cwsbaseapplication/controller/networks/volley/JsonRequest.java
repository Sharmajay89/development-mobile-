package com.cws.cwsbaseapplication.controller.networks.volley;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cws.cwsbaseapplication.controller.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Volley JSON Request. This class should be used only when we need JSON as reponse
 */
public class JsonRequest extends JsonObjectRequest implements IRequest<JSONObject>{
	private String acceptType;
	private int method;

	/**
	 * @param method
	 * @param url
	 * @param listener
	 * @param errorListener
	 */
	public JsonRequest(int method, String url, JSONObject jsonObject,
			Listener<JSONObject> listener, ErrorListener errorListener,
			String acceptType) {
		super(method, url, jsonObject, listener, errorListener);
		this.acceptType = acceptType;
		this.method = method;
		this.setRetryPolicy(getRetryPolicy());
	}

	/**
	 * @param method
	 * @param url
	 * @param listener
	 * @param errorListener
	 */
	public JsonRequest(int method, String url, JSONObject jsonObject,
			Listener<JSONObject> listener, ErrorListener errorListener) {
		super(method, url, jsonObject, listener, errorListener);
		this.acceptType = Constants.CONTENT_TYPE_JSON;
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
		if (this.method == Request.Method.GET) {
			headers.put("Content-Type", Constants.CONTENT_TYPE_JSON);
			headers.put("charset", Constants.CHARSET);
		}
		/*headers.put(REQUEST_HEADER_OTP, MercuryApplication.getInstance().getAccessOtp());
		headers.put(REQUEST_HEADER_AUTH_TOKEN,  MercuryApplication.getInstance().getAuthToken() != null ? MercuryApplication.getInstance().getAuthToken().getToken() : Constants.EMPTY_STRING);
		headers.put(REQUEST_HEADER_TXN_PIN,  MercuryApplication.getInstance().getAccessTxnPIN() != null ? MercuryApplication.getInstance().getAccessTxnPIN() : Constants.EMPTY_STRING);
		*/return headers;
	}

	@Override
	public Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
		try {
			String jsonString = new String(response.data,
					HttpHeaderParser.parseCharset(response.headers));
			if (this.method == Request.Method.GET) {
				return Response.success(
						new JSONObject(jsonString),HttpHeaderParser.parseCacheHeaders(response));
			} else {
				return Response.success(new JSONObject(jsonString),
						HttpHeaderParser.parseCacheHeaders(response));
			}

		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		} catch (JSONException je) {
			return Response.error(new ParseError(je));
		}
	}

	/**
	 * Overriding default timeout of Volley Network Socket Requests
	 */
	//@Override
	public RetryPolicy getRetryPolicy() {
		return new DefaultRetryPolicy(Constants.NETWORK_SOCKET_TIMEOUT,
				Constants.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
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
                    JSONObject errorR = new JSONObject(message);
                    message = errorR.optString("message");
                }
                CustomError error = new CustomError(volleyError.networkResponse.statusCode, message);
                volleyError = error;
            }
            return volleyError;
        } catch (Exception e) {
            return new ParseError(e);
        }
    }
}
