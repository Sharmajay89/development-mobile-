package com.cws.cwsbaseapplication.controller.networks.retrofit;

import com.cws.cwsbaseapplication.controller.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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
    private static RetrofitResponseListener listener;

    public RestAppController(RetrofitResponseListener listener) {
        this.listener = listener;
    }

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

    public static <T> void makeHttpStringPostRequest(String url, HashMap postData, final String tag)
    {

        Call<Object> request = getWsController().makeHttpFormDataPostRequest(postData,url);

        //...other stuff you might need...

        request.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

                try
                {
                    //...other stuff you might need...
                    //...do something with the response, convert it to
                    //return the correct object...
                    listener.onSuccess(response.body(),tag);

                }
                catch (Exception e)
                {
                    // .. the response was no good...
                    listener.onSuccess(null,tag);
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable throwable)
            {
                // .. the response was no good...
                listener.onSuccess(throwable,tag);
            }
        });

    }
}
