package com.jdjz.androidnews.ui.news.contract;

import com.jdjz.androidnews.bean.NewsChannelTable;
import com.jdjz.common.base.BaseModel;
import com.jdjz.common.base.BasePresenter;
import com.jdjz.common.base.BaseView;

import java.util.List;

import rx.Observable;

/**
 * Created by tchl on 2016-12-23.
 */
public interface NewsChannelCtontract {
    interface Model extends BaseModel{
        Observable<List<NewsChannelTable>>  lodeMineNewsChannels();
    }

    interface View extends BaseView {
        void returnMineNewsChannels(List<NewsChannelTable> newsChannelsMine);
    }

    abstract static class Presenter extends BasePresenter<View,Model>{
        public abstract void loadChannelsRequest();
    }
}
