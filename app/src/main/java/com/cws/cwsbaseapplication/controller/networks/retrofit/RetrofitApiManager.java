package com.cws.cwsbaseapplication.controller.networks.retrofit;

/**
 * Created by cws on 14/11/17.
 */

public class RetrofitApiManager {

   /* public <T> void makeHttpListList(final RetrofitResponseListener<List<T>> listener)
    {
        Call<ResponseBody> request = WSController.fetchBirdsAds("","");

        //...other stuff you might need...

        request.enqueue(new Callback<ResponseBody>()
        {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> rawResponse)
            {
                try
                {
                    String response = rawResponse.body().string();
                    //...other stuff you might need...
                    //...do something with the response, convert it to
                    //return the correct object...
                    SomeObject object = new SomeObject(response);
                    listener.getResult(object);

                }
                catch (Exception e)
                {
                    // .. the response was no good...
                    listener.getResult(null);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable)
            {
                // .. the response was no good...
                listener.getResult(null);
            }
        });
    }*/
}
