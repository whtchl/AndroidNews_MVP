package com.jdjz.androidnews.ui.news.model;

import com.bumptech.glide.signature.ApplicationVersionSignature;
import com.jdjz.androidnews.R;
import com.jdjz.androidnews.api.ApiConstants;
import com.jdjz.androidnews.app.AppApplication;
import com.jdjz.androidnews.app.AppConstant;
import com.jdjz.androidnews.bean.NewsChannelTable;
import com.jdjz.androidnews.db.NewsChannelTableManager;
import com.jdjz.androidnews.ui.news.contract.NewsChannelCtontract;
import com.jdjz.common.baserx.RxSchedulers;
import com.jdjz.common.commonutils.ACache;
import com.jdjz.common.commonutils.LogUtils;

import java.util.ArrayList;
import java.util.Arrays;
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
                    LogUtils.logd("newsChannelTableList is null------");
                    newsChannelTableList = (ArrayList<NewsChannelTable>) NewsChannelTableManager.loadNewsChannelsStatic();
                }
                subscriber.onNext(newsChannelTableList);
                subscriber.onCompleted();
            }
        }).compose(RxSchedulers.<List<NewsChannelTable>>io_main());
    }

    @Override
    public Observable<List<NewsChannelTable>> lodeMoreNewsChannels() {
        return Observable.create(new Observable.OnSubscribe<List<NewsChannelTable>>(){

            @Override
            public void call(Subscriber<? super List<NewsChannelTable>> subscriber) {
                ArrayList<NewsChannelTable> newsChannelTableArrayList = (ArrayList<NewsChannelTable>) ACache.get(AppApplication.getAppContext())
                        .getAsObject(AppConstant.CHANNEL_MORE);
                if(newsChannelTableArrayList==null){
                    LogUtils.logd("call lodeMoreNewsChannels newsChannelTableArrayList==null");
                    List<String> channelName = Arrays.asList(AppApplication.getAppContext().getResources().getStringArray(R.array.news_channel_name));
                    List<String> channelId = Arrays.asList(AppApplication.getAppContext().getResources().getStringArray(R.array.news_channel_id));
                    newsChannelTableArrayList = new ArrayList<NewsChannelTable>();
                    for(int i=0; i<channelName.size();i++){
                        NewsChannelTable entity = new NewsChannelTable(channelName.get(i),channelId.get(i), ApiConstants.getType(channelId.get(i)),i<=5,i,false);
                        newsChannelTableArrayList.add(entity);
                    }
                }
                subscriber.onNext(newsChannelTableArrayList);
                subscriber.onCompleted();
            }
        }).compose(RxSchedulers.<List<NewsChannelTable>>io_main());
    }

    @Override
    public Observable<String> updateDb(final ArrayList<NewsChannelTable> mineChannelTableList, final ArrayList<NewsChannelTable> moreChannelTableList) {
        return Observable.create(new Observable.OnSubscribe<String>(){

            @Override
            public void call(Subscriber<? super String> subscriber) {
                ACache.get(AppApplication.getAppContext()).put(AppConstant.CHANNEL_MINE,mineChannelTableList);
                ACache.get(AppApplication.getAppContext()).put(AppConstant.CHANNEL_MORE,moreChannelTableList);
                subscriber.onNext("");
                subscriber.onCompleted();
            }
        }).compose(RxSchedulers.<String>io_main());
    }
}
