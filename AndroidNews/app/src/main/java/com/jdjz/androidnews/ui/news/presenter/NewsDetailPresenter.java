package com.jdjz.androidnews.ui.news.presenter;

import android.widget.Toast;

import com.jdjz.androidnews.R;
import com.jdjz.androidnews.bean.NewsDetail;
import com.jdjz.androidnews.ui.news.contract.NewsDetailContract;
import com.jdjz.common.baserx.RxSubscriber;
import com.jdjz.common.commonutils.ToastUitl;

/**
 * Created by tchl on 2016-12-14.
 */
public class NewsDetailPresenter extends NewsDetailContract.Presenter{
    @Override
    public void getOneNewsDataRequest(String postId) {
        mRxManager.add(mModel.getOneNewsData(postId).subscribe(new RxSubscriber<NewsDetail>(mContext) {
            @Override
            protected void _onNext(NewsDetail newsDetail) {
                mView.returnOneNewsData(newsDetail);
            }

            @Override
            protected void _onError(String t) {
                ToastUitl.showToastWithImg(t, R.drawable.ic_wrong);
            }
        }));
    }
}
