package com.jdjz.androidnews.ui.news.contract;

import com.jdjz.androidnews.bean.NewsDetail;
import com.jdjz.androidnews.bean.NewsSummary;
import com.jdjz.common.base.BaseModel;
import com.jdjz.common.base.BasePresenter;
import com.jdjz.common.base.BaseView;

import java.util.List;

import rx.Observable;

/**
 * Created by tchl on 2016-12-08.
 */
public interface NewsDetailContract {
    interface Model extends BaseModel {
        //请求获取新闻
        Observable<NewsDetail> getOneNewsData(String postId);
    }

    interface View extends BaseView {
        //返回获取的新闻
        void returnOneNewsData(NewsDetail newsDetail);
    }
    abstract  static class  Presenter extends BasePresenter<View,Model> {
        //发起获取单条新闻请求
        public abstract void getOneNewsDataRequest(String postId);
    }
}
