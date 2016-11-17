package com.jdjz.androidnews.ui.main.contract;

import com.jdjz.androidnews.bean.NewsChannelTable;
import com.jdjz.common.base.BaseModel;
import com.jdjz.common.base.BasePresenter;
import com.jdjz.common.base.BaseView;

import java.util.List;

import rx.Observable;

/**
 * Created by happen on 2016/11/6.
 */
public interface NewsMainContract {
    interface Model extends BaseModel{
        Observable<List<NewsChannelTable>> lodeMineNewsChannels();
    }

    interface View extends BaseView {
        void returnMineNewsChannels(List<NewsChannelTable> newChannelsMine);
    }
    abstract  static class  Presenter extends BasePresenter<View,Model>{
        public abstract void lodeMineChannelsRequest();
    }
}
