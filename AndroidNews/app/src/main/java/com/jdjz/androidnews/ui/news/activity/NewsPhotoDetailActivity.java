package com.jdjz.androidnews.ui.news.activity;

import android.content.Context;
import android.content.Intent;

import com.jdjz.androidnews.R;
import com.jdjz.androidnews.app.AppConstant;
import com.jdjz.androidnews.bean.NewsPhotoDetail;
import com.jdjz.common.base.BaseActivity;

/**
 * Created by tchl on 2016-12-20.
 */
public class NewsPhotoDetailActivity extends BaseActivity{
    public static void startAction(Context context, NewsPhotoDetail mNewsPhotoDetail){
        Intent intent = new Intent(context,NewsDetailActivity.class);
        intent.putExtra(AppConstant.PHOTO_DETAIL, mNewsPhotoDetail);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.act_news_photo_detail;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {

    }
}
