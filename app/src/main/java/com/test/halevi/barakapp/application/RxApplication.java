package com.test.halevi.barakapp.application;

import android.app.Application;

/**
 * Created by Barak Halevi on 07/11/2018.
 */
public class RxApplication extends Application {

    private static RxApplication mInstance;
    private NetworkService networkService;

    public static RxApplication getInstance() {
        return mInstance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        networkService = new NetworkService();
    }


    public NetworkService getNetworkService(){
        return networkService;
    }
}