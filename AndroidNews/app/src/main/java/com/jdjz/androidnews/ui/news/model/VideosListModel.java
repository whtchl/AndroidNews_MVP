package com.jdjz.androidnews.ui.news.model;

import com.jdjz.androidnews.api.Api;
import com.jdjz.androidnews.api.HostType;
import com.jdjz.androidnews.bean.VideoData;
import com.jdjz.androidnews.ui.news.contract.VideosListContract;
import com.jdjz.common.baserx.RxSchedulers;
import com.jdjz.common.commonutils.TimeUtil;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Created by tchl on 2017-01-17.
 */
public class VideosListModel implements VideosListContract.Model {
    @Override
    public Observable<List<VideoData>> getVideosListData(final String type, int startPage) {
        return Api.getDefault(HostType.NETEASE_NEWS_VIDEO).getVideoList(Api.getCacheControl(),type,startPage)
                .flatMap(new Func1<Map<String, List<VideoData>>, Observable<VideoData>>() {
                    @Override
                    public Observable<VideoData> call(Map<String, List<VideoData>> stringListMap) {
                        return Observable.from(stringListMap.get(type));
                    }
                }).map(new Func1<VideoData, VideoData>() {
                    @Override
                    public VideoData call(VideoData videoData) {
                        String ptime = TimeUtil.formatDate(videoData.getPtime());
                        videoData.setPtime(ptime);
                        return videoData;
                    }
                })
                .distinct()
                .toSortedList(new Func2<VideoData, VideoData, Integer>() {
                    @Override
                    public Integer call(VideoData videoData, VideoData videoData2) {
                        return videoData2.getPtime().compareTo(videoData.getPtime());;
                    }
                }).compose(RxSchedulers.<List<VideoData>>io_main());
    }
}
