package com.cws.cwsbaseapplication.controller.networks.retrofit;

import com.cws.cwsbaseapplication.controller.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Rest Api controller
 */

public class RestAppController {

    private static HttpLoggingInterceptor loggingInterceptor;
    private static OkHttpClient okHttpClient;
    private static Gson gson;
    private static Retrofit retrofit;
    private static WSController wsController;

    public static WSController getWsController() {
        if (null == wsController) wsController = getRetrofitinstance().create(WSController.class);
        return wsController;
    }

    private static Retrofit getRetrofitinstance() {
        if (null == retrofit) {
            loggingInterceptor = new HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor).build();
            gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                    .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.API_BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }




}
