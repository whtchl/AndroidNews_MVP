package com.jdjz.androidnews.ui.news.contract;

import com.jdjz.androidnews.bean.VideoData;
import com.jdjz.common.base.BaseModel;
import com.jdjz.common.base.BasePresenter;
import com.jdjz.common.base.BaseView;

import java.util.List;

import rx.Observable;

/**
 * Created by tchl on 2017-01-16.
 */
public interface VideosListContract {
    interface Model extends BaseModel{
        Observable<List<VideoData>> getVideosListData(String type, int startPage);
    }
    interface View extends BaseView{
        void returnVideosListData(List<VideoData> newSummaries);

    }

    abstract static class Presenter extends BasePresenter<View,Model>{
        public abstract void getVidoesListDataRequest(String type,int startPage);
    }
}
