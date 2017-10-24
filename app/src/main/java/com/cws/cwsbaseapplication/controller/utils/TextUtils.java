package com.cws.cwsbaseapplication.controller.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by ajaya on 1/10/17.
 */

public class TextUtils {

    public  static void showToast(Context context,String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
