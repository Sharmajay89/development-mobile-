package com.cws.cwsbaseapplication.controller.networks.retrofit;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

/**
 * this is use to call api and response type
 */

public interface WSController {

    String BASE_URL = "https://api.myjson.com/bins/";//"http://api.bizzgain.com/api";
    @POST("{api_path}")
    <T> Call<T> makeHttpPostRequest(@Path("api_path") String url);

   /* @GET("{api_path}")
    Call<ArrayList<Image>> fetchImageData(@Path("api_path") String path);*/
    /*@GET("{api_path}")
    Call<T> fetchImageData(@Path("api_path") String path);

    @POST("/{save_captured_data}")
    @Multipart
    void postCapturedData(@Path("save_captured_data") String path, @PartMap Map<String, RequestBody> map, Callback<String> response);
*/



}
