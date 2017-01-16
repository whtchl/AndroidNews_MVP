package com.jdjz.androidnews.ui.news.presenter;

import com.jdjz.androidnews.R;
import com.jdjz.androidnews.bean.VideoData;
import com.jdjz.androidnews.ui.news.contract.VideosListContract;
import com.jdjz.common.baserx.RxSubscriber;

import java.util.List;

/**
 * Created by tchl on 2017-01-16.
 */
public class VideoListPresenter extends VideosListContract.Presenter {
    @Override
    public void getVidoesListDataRequest(String type, int startPage) {
        mRxManager.add(mModel.getVideosListData(type,startPage).subscribe(new RxSubscriber<List<VideoData>>(mContext,false) {
            @Override
            public void onStart(){
                super.onStart();
                mView.showLoading(mContext.getString(R.string.loading));
            }
            @Override
            protected void _onNext(List<VideoData> videoDatas) {
                mView.returnVideosListData(videoDatas);
                mView.stopLoading();
            }

            @Override
            protected void _onError(String t) {
                mView.showErrorTip(t);
            }
        }));
    }
}
