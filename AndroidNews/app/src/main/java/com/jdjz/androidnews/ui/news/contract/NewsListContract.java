package com.jdjz.androidnews.ui.news.contract;

import com.jdjz.androidnews.bean.NewsChannelTable;
import com.jdjz.common.base.BaseModel;
import com.jdjz.common.base.BasePresenter;
import com.jdjz.common.base.BaseView;

import java.util.List;

import rx.Observable;

/**
 * Created by tchl on 2016/11/20.
 */
public interface NewsListContract {
    interface Model extends BaseModel {
        //请求获取新闻
        Observable<List<NewsSummary>> getNewsListData(String type,final String id,int startPage);
    }

    interface View extends BaseView {
        //返回获取的新闻
        void returnNewsListData(List<NewsSummary> newsSummaries);
        //返回顶部
        void scrolltoTop();
    }
    abstract  static class  Presenter extends BasePresenter<View,Model> {
        //发起获取新闻请求
        public abstract void getNewsListDataRequest(String type,final String id,int startPage);
    }
}
