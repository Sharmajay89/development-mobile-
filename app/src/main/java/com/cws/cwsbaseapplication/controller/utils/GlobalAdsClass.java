package com.cws.cwsbaseapplication.controller.utils;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.service.taptofind.BuildConfig;

public class GlobalAdsClass {

    public static void loadAds(AdView mAdView) {

        if (BuildConfig.DEBUG) {//for testing use this line of statement for google ads
            AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
            mAdView.loadAd(adRequest);
        } else {//for live use this line of statement for google ads
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        }
    }
}