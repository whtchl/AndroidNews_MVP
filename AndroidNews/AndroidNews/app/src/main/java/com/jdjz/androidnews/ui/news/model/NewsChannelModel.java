package com.jdjz.androidnews.ui.news.model;

import com.bumptech.glide.signature.ApplicationVersionSignature;
import com.jdjz.androidnews.app.AppApplication;
import com.jdjz.androidnews.app.AppConstant;
import com.jdjz.androidnews.bean.NewsChannelTable;
import com.jdjz.androidnews.db.NewsChannelTableManager;
import com.jdjz.androidnews.ui.news.contract.NewsChannelCtontract;
import com.jdjz.common.baserx.RxSchedulers;
import com.jdjz.common.commonutils.ACache;
import com.jdjz.common.commonutils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by tchl on 2016-12-23.
 */
public class NewsChannelModel implements NewsChannelCtontract.Model {
    @Override
    public Observable<List<NewsChannelTable>> lodeMineNewsChannels() {
        LogUtils.logd("we are in lodeMineNewsChannels");
        return Observable.create(new Observable.OnSubscribe<List<NewsChannelTable>>(){

            @Override
            public void call(Subscriber<? super List<NewsChannelTable>> subscriber) {
                ArrayList<NewsChannelTable> newsChannelTableList = (ArrayList<NewsChannelTable>) ACache.get(AppApplication.getAppContext())
                        .getAsObject(AppConstant.CHANNEL_MINE);
                if(newsChannelTableList == null){
                    LogUtils.logd("newsChannelTableList is null");
                    newsChannelTableList = (ArrayList<NewsChannelTable>) NewsChannelTableManager.loadNewsChannelsStatic();
                }
                subscriber.onNext(newsChannelTableList);
                subscriber.onCompleted();
            }
        }).compose(RxSchedulers.<List<NewsChannelTable>>io_main());
    }
}