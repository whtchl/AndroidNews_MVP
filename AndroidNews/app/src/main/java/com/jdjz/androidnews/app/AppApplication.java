package com.jdjz.androidnews.app;


//port com.jdjz.common.BuildConfig;
import com.jdjz.androidnews.BuildConfig;
import com.jdjz.common.base.BaseApplication;
import com.jdjz.common.commonutils.LogUtils;

/**
 * Created by tchl on 2016-10-31.
 */
public class AppApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化logger
        LogUtils.logInit(BuildConfig.LOG_DEBUG);
    }
}