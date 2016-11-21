package com.jdjz.androidnews.ui.news.presenter;

import com.jdjz.androidnews.R;
import com.jdjz.androidnews.bean.NewsSummary;
import com.jdjz.androidnews.ui.news.contract.NewsListContract;
import com.jdjz.common.baserx.RxSubscriber;

import java.util.List;

/**
 * Created by tchl on 2016-11-21.
 */
public class NewsListPresenter extends NewsListContract.Presenter {
    @Override
    public void onStart() {
        super.onStart();
    }

    /**
     * 请求获取列表数据
     * @param type
     * @param id
     * @param startPage
     */
    @Override
    public void getNewsListDataRequest(String type, String id, int startPage) {
        mRxManager.add(mModel.getNewsListData(type,id,startPage).subscribe(new RxSubscriber<List<NewsSummary>>(mContext,false) {
            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading(mContext.getString(R.string.loading));
            }

            @Override
            protected void _onNext(List<NewsSummary> newsSummaries) {
                mView.returnNewsListData(newsSummaries);
                mView.stopLoading();
            }

            @Override
            protected void _onError(String t) {
                mView.showErrorTip(t);
            }
        }));
    }
}
