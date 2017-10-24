package com.cws.cwsbaseapplication.controller.networks.volley;

import com.android.volley.VolleyError;

/**
 * Custom Error thrown when Server returns status = 'FAILURE' with code and message
 * Created by avishvakarma on 7/7/17.
 */
public class CustomError extends VolleyError {
    private int code;
    private  String message;

    public CustomError() {
        super();
    }

    public CustomError(int code,String message) {
        super();
        this.code = code;
        this.message = message;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
