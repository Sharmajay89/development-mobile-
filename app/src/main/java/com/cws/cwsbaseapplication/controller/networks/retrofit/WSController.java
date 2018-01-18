package com.cws.cwsbaseapplication.controller.networks.retrofit;



import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;


/**
 * this is use to call api and response type
 */

public interface WSController<T>  {
    @FormUrlEncoded
    @POST("{api_path}")
    Call<Object> makeHttpFormDataPostRequest(@FieldMap Map<String, String> postData, @Path("api_path") String url);


}
