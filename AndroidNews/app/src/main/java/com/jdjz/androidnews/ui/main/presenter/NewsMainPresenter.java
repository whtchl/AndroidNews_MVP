package com.jdjz.androidnews.ui.main.presenter;

import com.jdjz.androidnews.app.AppConstant;
import com.jdjz.androidnews.bean.NewsChannelTable;
import com.jdjz.androidnews.ui.main.contract.NewsMainContract;
import com.jdjz.common.baserx.RxSchedulers;
import com.jdjz.common.commonutils.LogUtils;

import java.util.List;

import rx.functions.Action1;

/**
 * Created by happen on 2016/11/6.
 */
public class NewsMainPresenter extends  NewsMainContract.Presenter{


    @Override
    public void onStart() {
        super.onStart();
        LogUtils.logd("NewsMainPresenter  onStart");
        //监听新闻频道变化刷新
/*        mRxManager.on(AppConstant.NEWS_CHANNEL_CHANGED, new Action1<List<NewsChannelTable>>() {
           @Override
            public void call(List<NewsChannelTable> newsChannelTables){
               if (newsChannelTables != null){
                   mView.returnMineNewsChannels(newsChannelTables);
               }
           }
        });*/
    }

    /**
     * -- new RxSubscriber<List<NewsChannelTable>>(mContext,false)  --》是个订阅者；
     * -- subscribe(new RxSubscriber<List<NewsChannelTable>>(mContext,false) -->表示被观察者（observable）订阅（subscribe）的订阅者（RxSubscriber）
     *   （这句话事件上应该理解为订阅者订阅了被观察者，但是和button 的意思一样要反正来理解）。
     * -- lodeMineNewsChannels()执行这函数中的subscriber.onNext(newsChannelTableList)，然后这个onNext就执行了RxSubscriber(订阅者)的_onNext()
     */
    @Override
    public void lodeMineChannelsRequest() {
        mRxManager.add(mModel.lodeMineNewsChannels().subscribe(new RxSchedulers<List <NewsChannelTable>>(mContext,false){
            @Override
            protected void  _onNext(List<NewsChannelTable> newsChannelTables){
                LogUtils.logd("lodeMineChannelsRequest onStart（）: thread id and name:"+Thread.currentThread().getId()+" , "+Thread.currentThread().getName());
                mView.returnMineNewsChannels(newsChannelTables);
            }
        }));

    }

}

