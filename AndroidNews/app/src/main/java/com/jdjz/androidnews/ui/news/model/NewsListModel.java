package com.jdjz.androidnews.ui.news.model;

import android.util.TimeUtils;

import com.jdjz.androidnews.api.Api;
import com.jdjz.androidnews.api.ApiConstants;
import com.jdjz.androidnews.api.HostType;
import com.jdjz.androidnews.bean.NewsSummary;
import com.jdjz.androidnews.ui.news.contract.NewsListContract;
import com.jdjz.common.baserx.RxSchedulers;
import com.jdjz.common.commonutils.LogUtils;
import com.jdjz.common.commonutils.TimeUtil;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Created by tchl on 2016-11-21.
 */
public class NewsListModel implements NewsListContract.Model {
    @Override
    public Observable<List<NewsSummary>> getNewsListData(String type, final String id, int startPage) {
        return Api.getDefault(HostType.NETEASE_NEWS_VIDEO).getNewsList(Api.getCacheControl(),type,id,startPage)
                .flatMap(new Func1<Map<String, List<NewsSummary>>, Observable<NewsSummary>>() {
                    @Override
                    public Observable<NewsSummary> call(Map<String, List<NewsSummary>> stringListMap) {
                        if(id.endsWith(ApiConstants.HOUSE_ID)){
                            // 房产实际上针对地区的它的id与返回key不同
                            return Observable.from(stringListMap.get("北京"));
                        }
                        return  Observable.from(stringListMap.get(id));
                    }
                })
                //转化事件
                .map(new Func1<NewsSummary, NewsSummary>() {
                    @Override
                    public NewsSummary call(NewsSummary newsSummary) {
                        String ptime = TimeUtil.formatDate(newsSummary.getPtime());
                        newsSummary.setPtime(ptime);
                        LogUtils.logd("Ptime:"+ptime);
                        return newsSummary;
                    }
                })
                .distinct()
                .toSortedList(new Func2<NewsSummary, NewsSummary, Integer>() {
                    @Override
                    public Integer call(NewsSummary newsSummary1, NewsSummary newsSummary2) {
                        return newsSummary1.getPtime().compareTo(newsSummary2.getPtime());
                    }
                })
                .compose(RxSchedulers.<List<NewsSummary>>io_main());

    }
}
