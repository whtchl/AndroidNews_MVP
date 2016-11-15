package com.jdjz.androidnews.ui.main.presenter;

import com.jdjz.androidnews.app.AppConstant;
import com.jdjz.androidnews.bean.NewsChannelTable;
import com.jdjz.androidnews.ui.main.contract.NewsMainContract;

import java.util.List;

import rx.functions.Action1;

/**
 * Created by happen on 2016/11/6.
 */
public class NewsMainPresenter extends  NewsMainContract.Presenter{


    @Override
    public void onStart() {
        super.onStart();
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

    @Override
    public void lodeMineChannelsRequest() {

    }

}

