package com.jdjz.androidnews.ui.news.model;

import com.jdjz.androidnews.api.HostType;
import com.jdjz.androidnews.bean.NewsSummary;
import com.jdjz.androidnews.ui.news.contract.NewsListContract;

import java.util.List;

import rx.Observable;

/**
 * Created by tchl on 2016-11-21.
 */
public class NewsListModel extends NewsListContract.Model {
    @Override
    public Observable<List<NewsSummary>> getNewsListData(String type, String id, int startPage) {
        return Api.getDefault(HostType.NETEASE_NEWS_VIDEO).get
        return null;
    }
}
