package com.jdjz.androidnews.ui.news.activity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jdjz.androidnews.R;
import com.jdjz.androidnews.app.AppConstant;
import com.jdjz.androidnews.bean.NewsDetail;
import com.jdjz.androidnews.ui.news.contract.NewsDetailContract;
import com.jdjz.androidnews.ui.news.model.NewsDetailModel;
import com.jdjz.androidnews.ui.news.presenter.NewsDetailPresenter;
import com.jdjz.androidnews.widget.URLImageGetter;
import com.jdjz.common.base.BaseActivity;
import com.jdjz.common.baserx.RxSchedulers;
import com.jdjz.common.commonutils.LogUtils;
import com.jdjz.common.commonutils.TimeUtil;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by tchl on 2016-12-08.
 */
public class NewsDetailActivity extends BaseActivity<NewsDetailPresenter, NewsDetailModel> implements NewsDetailContract.View {

    @Bind(R.id.news_detail_photo_iv)
    ImageView newsDetailPhotoIv;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.news_detail_from_tv)
    TextView newsDetailFromTv;
    @Bind(R.id.news_detail_body_tv)
    TextView newsDetailBodyTv;
    @Bind(R.id.progress_bar)
    ProgressBar progressBar;
    @Bind(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;


    private String postId;
    private URLImageGetter mUrlImageGetter;
    /**
     * 入口
     *
     * @param mContext
     * @param postId
     */
    public static void startAction(Context mContext, View view, String postId, String imgUrl) {
        Intent intent = new Intent(mContext, NewsDetailActivity.class);
        intent.putExtra(AppConstant.NEWS_POST_ID, postId);
        intent.putExtra(AppConstant.NEWS_IMG_RES, imgUrl);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions
                    .makeSceneTransitionAnimation((Activity) mContext, view, AppConstant.TRANSITION_ANIMATION_NEWS_PHOTOS);
            mContext.startActivity(intent, options.toBundle());
        } else {
            //让新的Activity从一个小的范围扩大到全屏
            ActivityOptionsCompat options = ActivityOptionsCompat
                    .makeScaleUpAnimation(view, view.getWidth() / 4, view.getHeight() / 4, 0, 0);
            ActivityCompat.startActivity((Activity) mContext, intent, options.toBundle());
        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_news_detail;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        postId = getIntent().getStringExtra(AppConstant.NEWS_POST_ID);
        mPresenter.getOneNewsDataRequest(postId);
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
    }

    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void showErrorTip(String msg) {

    }

    @Override
    public void returnOneNewsData(NewsDetail newsDetail) {
        String NewsImgSrc = getImgSrcs(newsDetail);
        String mNewsTitle = newsDetail.getTitle();
        String newsSource = newsDetail.getSource();
        String newsTime = TimeUtil.formatDate(newsDetail.getPtime());
        String newsBody = newsDetail.getBody();

        LogUtils.logd("newsSource:"+newsSource);
        setToolBarLayout(mNewsTitle);
        setNewsDetailBodyTv(newsDetail, newsBody);
        newsDetailFromTv.setText(getString(R.string.news_from,newsSource,newsTime));
        setNewsDetailPhotoIv(NewsImgSrc);
    }

    public void setToolBarLayout(String newsTitle) {
        toolbarLayout.setTitle(newsTitle);

    }

    private String getImgSrcs(NewsDetail newsDetail) {
        List<NewsDetail.ImgBean> imgSrcs = newsDetail.getImg();
        String imgSrc;

        if (imgSrcs != null && imgSrcs.size() > 0) {
            imgSrc = imgSrcs.get(0).getSrc();
        } else {
            imgSrc = getIntent().getStringExtra(AppConstant.NEWS_IMG_RES);
        }
        LogUtils.logd("NewsDetailActivity imgSrc:" + imgSrc);
        return imgSrc;
    }

    private void setNewsDetailPhotoIv(String imgSrc) {
        Glide.with(this).load(imgSrc)
                .fitCenter()
                .error(com.jdjz.common.R.drawable.ic_empty_picture)
                .crossFade().into(newsDetailPhotoIv);
    }

    private void setNewsDetailBodyTv(final NewsDetail newsDetail, final String newsBody){
        mRxManager.add(Observable.timer(500, TimeUnit.MILLISECONDS)
        .compose(RxSchedulers.<Long>io_main())
        .subscribe(new Subscriber<Long>() {
            @Override
            public void onCompleted() {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Throwable e) {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onNext(Long aLong) {
                setBody(newsDetail,newsBody);
            }
        }));
    }

    private void setBody(NewsDetail newsDetail, String newsBody) {
        int imgTotal = newsDetail.getImg().size();
        if(isShowBody(newsBody,imgTotal)){
            mUrlImageGetter = new URLImageGetter(newsDetailBodyTv, newsBody, imgTotal);
            newsDetailBodyTv.setText(Html.fromHtml(newsBody, mUrlImageGetter, null));
        }else{
            LogUtils.logd(newsBody);
            newsDetailBodyTv.setText(Html.fromHtml(newsBody));
        }
    }

    private boolean isShowBody(String newsBody, int imgTotal) {
        return imgTotal >=2 && newsBody !=null;
    }

}
