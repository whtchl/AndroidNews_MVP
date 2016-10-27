package com.jdjz.common.base;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by tchl on 2016-10-27.
 */
public class BaseApplication extends Application {
    private static BaseApplication baseApplication;

    @Override
    public void onCreate(){
        super.onCreate();
        baseApplication = this;
    }

    public static Context getAppContext(){ return baseApplication;}
    public static Resources getAppResources(){
        return baseApplication.getResources();
    }
    @Override
    public void onTerminate(){
        super.onTerminate();
    }
}
