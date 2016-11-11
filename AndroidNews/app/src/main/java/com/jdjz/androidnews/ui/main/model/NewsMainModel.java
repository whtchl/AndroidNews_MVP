package com.jdjz.androidnews.ui.main.model;

import com.jdjz.androidnews.app.AppApplication;
import com.jdjz.androidnews.app.AppConstant;
import com.jdjz.androidnews.bean.NewsChannelTable;
import com.jdjz.androidnews.db.NewsChannelTableManager;
import com.jdjz.androidnews.ui.main.contract.NewsMainContract;
import com.jdjz.common.baserx.RxSchedulers;
import com.jdjz.common.commonutils.ACache;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by tchl on 2016-11-11.
 */
public class NewsMainModel implements NewsMainContract.Model{
    @Override
    public Observable<List<NewsChannelTable>> loadMineNewsChannedls() {

        return Observable.create(new Observable.OnSubscribe<List<NewsChannelTable>>(){
            @Override
            public void call(Subscriber<? super List<NewsChannelTable>> subscriber){
                ArrayList<NewsChannelTable> newsChannelTableList = (ArrayList<NewsChannelTable>) ACache.get(AppApplication.getAppContext()).getAsObject(AppConstant.CHANNEL_MINE);
                if(newsChannelTableList == null){
                    newsChannelTableList = (ArrayList<NewsChannelTable>) NewsChannelTableManager.loadNewsChannelsStatic();
                    ACache.get(AppApplication.getAppContext()).put(AppConstant.CHANNEL_MINE,newsChannelTableList);
                }
                subscriber.onNext(newsChannelTableList);
                subscriber.onCompleted();
            }
        }).compose(RxSchedulers.<List<NewsChannelTable>>io_main());
    }
}
