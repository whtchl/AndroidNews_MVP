package com.jdjz.androidnews.ui.news.contract;

import com.jdjz.androidnews.bean.NewsChannelTable;
import com.jdjz.common.base.BaseModel;
import com.jdjz.common.base.BasePresenter;
import com.jdjz.common.base.BaseView;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by tchl on 2016-12-23.
 */
public interface NewsChannelCtontract {
    interface Model extends BaseModel{
        Observable<List<NewsChannelTable>>  lodeMineNewsChannels();
        Observable<List<NewsChannelTable>> lodeMoreNewsChannels();
        Observable<String> swapDb(ArrayList<NewsChannelTable> newsChannelTableList,int fromPosition, int toPosition);
        Observable<String> updateDb(ArrayList<NewsChannelTable> mineChannelTableList,ArrayList<NewsChannelTable> moreChannelTableList);
    }

    interface View extends BaseView {
        void returnMineNewsChannels(List<NewsChannelTable> newsChannelsMine);
        void returnMoreNewsChannels(List<NewsChannelTable> newsChannelsMore);
    }

    abstract static class Presenter extends BasePresenter<View,Model>{
        public abstract void loadChannelsRequest();
        public abstract void onItemSwap(ArrayList<NewsChannelTable> newsChannelTableList, int fromPosition, final int toPosition);
        public abstract void onItemAddOrRemove(ArrayList<NewsChannelTable>mineChannelTableList,
                                               ArrayList<NewsChannelTable>moreChannelTableList);
    }
}
