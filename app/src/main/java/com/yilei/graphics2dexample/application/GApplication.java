package com.yilei.graphics2dexample.application;

import android.app.Application;
import android.content.Context;

/**
 * Created by lei on 2018/2/28.
 */

public class GApplication extends Application{
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext(){
        return mContext;
    }
}
