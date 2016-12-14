package com.jdjz.androidnews.ui.news.activity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jdjz.androidnews.R;
import com.jdjz.androidnews.app.AppConstant;
import com.jdjz.androidnews.bean.NewsDetail;
import com.jdjz.androidnews.ui.news.contract.NewsDetailContract;
import com.jdjz.androidnews.ui.news.model.NewsDetailModel;
import com.jdjz.androidnews.ui.news.presenter.NewsDetailPresenter;
import com.jdjz.common.base.BaseActivity;
import com.jdjz.common.commonutils.LogUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by tchl on 2016-12-08.
 */
public class NewsDetailActivity extends BaseActivity<NewsDetailPresenter, NewsDetailModel> implements NewsDetailContract.View {

    @Bind(R.id.news_detail_photo_iv)
    ImageView newsDetailPhotoIv;
    private String postId;
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
        setNewsDetailPhotoIv(NewsImgSrc);
    }
    private String getImgSrcs(NewsDetail newsDetail) {
        List<NewsDetail.ImgBean> imgSrcs = newsDetail.getImg();
        String imgSrc;

        if (imgSrcs != null && imgSrcs.size() > 0) {
            imgSrc = imgSrcs.get(0).getSrc();
        } else {
            imgSrc = getIntent().getStringExtra(AppConstant.NEWS_IMG_RES);
        }
        LogUtils.logd("NewsDetailActivity imgSrc:"+imgSrc);
        return imgSrc;
    }
    private void setNewsDetailPhotoIv(String imgSrc) {
        Glide.with(this).load(imgSrc)
                .fitCenter()
                .error(com.jdjz.common.R.drawable.ic_empty_picture)
                .crossFade().into(newsDetailPhotoIv);
    }


}
