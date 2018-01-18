package com.cws.cwsbaseapplication.controller.config;

import android.app.Application;
import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.cws.cwsbaseapplication.controller.networks.volley.LruBitmapCache;
import com.google.android.gms.ads.MobileAds;
import com.service.taptofind.R;

import java.io.File;

/**
 * Created by ajaya on 28/9/17.
 * Base class for maintaining global application state. The Application class, or your subclass of the Application class, is instantiated before any other class when the process for your application/package is created.
 * Used for Application level configuration like Setting Default Custom font,Initializing third party sdks like Splunk/ACRA/Crashlytics for crash reporting, Volley - Networking library etc.
 */

public class CWSBaseApplication extends Application {
    public static final String TAG = CWSBaseApplication.class.getSimpleName();

    /**
     * Request Queue object for holding Volley Network Requests
     */
    private RequestQueue mRequestQueue;

    /**
     * Volley ImageLoader to load/cache images
     */
    private ImageLoader mImageLoader;

    private File cacheDir;
    private File filesDir;

    private static CWSBaseApplication mInstance;
   // private DeviceInfo deviceInfo;
    //public Locale systemLocale = Locale.getDefault();


    /**
     * Application LifeCycle State listener
     * Used for identifying if Application is in background or user is using it
     */
    private static BaseLifeCycleCallbacks lifeCycleCallback;

    /**
     * Unique ID of android device
     */
    public String android_id;
   // private TouchDetector touchDetector;

    @Override
    public void onCreate() {
        super.onCreate();

        // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
        MobileAds.initialize(this, "ca-app-pub-5931405166920070~5582305537");

        android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        mInstance = this;

        //LocaleHelper.onCreate(this);

        // Register for listening Activity Lifecycle events (Used for checking
        // if app is in foreground or background)
        lifeCycleCallback = new BaseLifeCycleCallbacks();
        registerActivityLifecycleCallbacks(lifeCycleCallback);
        //Init Custom Fonts using Calligraphy
        initCalligraphy();

        /**
         * Initialize SPLUNK for crash reporting
         */
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                // Set the application environment
                Mint.setApplicationEnvironment(BuildConfig.MINT_APP_ENVIRONMENT);
                Mint.initAndStartSession(CWSBaseApplication.this, BuildConfig.MINT_SESSION_ID);
            }
        }).start();*/
    }

    /**
     * Method used to initialize third party sdk for using custom fonts
     */
    private void initCalligraphy() {
       /* CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath(getString(R.string.font_roboto_light))
                .setFontAttrId(R.attr.fontPath)
                .build()

        );*/
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //MultiDex.install(this);
    }


    /**
     * This method returns singleton instance of MercuryApplication class
     *
     * @return
     */
    public static synchronized CWSBaseApplication getInstance() {
        return mInstance;
    }


    /**
     * Method used to get volley RequestQueue object
     *
     * @return
     */
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    /**
     * return Volley ImageLoader object
     *
     * @return
     */
    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    new LruBitmapCache());
        }
        return this.mImageLoader;
    }

    /***
     * Adds Volley request object to Queue
     *
     * @param req
     * @param tag
     * @param <T>
     */
    public <T> void addToRequestQueue(Request<T> req,
                                      String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        VolleyLog.d("Adding request to queue: %s", req.getUrl());
        getRequestQueue().add(req);
    }

    /***
     * Adds Volley request object to Queue
     *
     * @param req
     * @param <T>
     */
    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }


    /***
     * To cancel pending Volley requests
     *
     * @param tag
     */
    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    @Override
    public File getCacheDir() {
        if (cacheDir == null) {
            cacheDir = (getExternalCacheDir() != null) ? getExternalCacheDir()
                    : super.getCacheDir();
        }

        return cacheDir;
    }

    @Override
    public File getFilesDir() {
        if (filesDir == null) {
            filesDir = (getExternalFilesDir(null) != null) ? getExternalFilesDir(null)
                    : super.getFilesDir();
        }

        return filesDir;
    }

    /**
     * Method to identify if app is in background
     *
     * @return
     */
    public boolean isInBackGround() {
        if (lifeCycleCallback != null) {
            return lifeCycleCallback.isBackGround();
        }
        return false;
    }
}
