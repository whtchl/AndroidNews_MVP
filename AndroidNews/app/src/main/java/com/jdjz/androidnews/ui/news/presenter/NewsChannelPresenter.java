package com.jdjz.androidnews.ui.news.presenter;

import com.jdjz.androidnews.app.AppConstant;
import com.jdjz.androidnews.bean.NewsChannelTable;
import com.jdjz.androidnews.ui.news.contract.NewsChannelCtontract;
import com.jdjz.common.baserx.RxSubscriber;
import com.jdjz.common.commonutils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tchl on 2016-12-23.
 */
public class NewsChannelPresenter extends NewsChannelCtontract.Presenter {
    @Override
    public void loadChannelsRequest() {
        mRxManager.add(mModel.lodeMineNewsChannels().subscribe(
                        new RxSubscriber<List<NewsChannelTable>>(mContext,false)
                        {
            @Override
            protected void _onNext(List<NewsChannelTable> newsChannelTables) {
                mView.returnMineNewsChannels(newsChannelTables);
            }

            @Override
            protected void _onError(String t) {

            }
        }));

        mRxManager.add(mModel.lodeMoreNewsChannels().subscribe(new RxSubscriber<List<NewsChannelTable>>(mContext,false) {
            @Override
            protected void _onNext(List<NewsChannelTable> newsChannelTables) {
                mView.returnMoreNewsChannels(newsChannelTables);
            }

            @Override
            protected void _onError(String t) {

            }
        }));
    }

    @Override
    public void onItemAddOrRemove(final  ArrayList<NewsChannelTable> mineChannelTableList, final ArrayList<NewsChannelTable> moreChannelTableList) {
        mRxManager.add(mModel.updateDb(mineChannelTableList,moreChannelTableList)
        .subscribe(new RxSubscriber<String>(mContext,false){

            @Override
            protected void _onNext(String s) {
                mRxManager.post(AppConstant.NEWS_CHANNEL_CHANGED,mineChannelTableList);
                //mRxManager.post(AppConstant.NEWS_CHANNEL_CHANGED,moreChannelTableList);
            }

            @Override
            protected void _onError(String t) {

            }
        }));
    }


}
