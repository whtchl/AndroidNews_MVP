package com.jdjz.androidnews.ui.news.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.jdjz.androidnews.R;
import com.jdjz.androidnews.app.AppConstant;
import com.jdjz.androidnews.bean.NewsPhotoDetail;
import com.jdjz.androidnews.ui.news.fragment.PhotoDetailFragment;
import com.jdjz.common.base.BaseActivity;
import com.jdjz.common.base.BaseFragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by tchl on 2016-12-20.
 */
public class NewsPhotoDetailActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.photo_detail_title_tv)
    TextView photoDetailTitleTv;

    private List<Fragment> mPhotoDetailFragmentList = new ArrayList<>();
    private NewsPhotoDetail mNewsPhotoDetail;

    public static void startAction(Context context, NewsPhotoDetail mNewsPhotoDetail) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
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
        mNewsPhotoDetail = getIntent().getParcelableExtra(AppConstant.PHOTO_DETAIL);
        createFragment(mNewsPhotoDetail);
        initViewPager();
    }

    private void initViewPager() {
        BaseFragmentStatePagerAdapter photoPagerAdapter = new BaseFragmentStatePagerAdapter(getSupportFragmentManager(),mPhotoDetailFragmentList);
        viewpager.setAdapter(photoPagerAdapter);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void createFragment(NewsPhotoDetail mNewsPhotoDetail) {
        mPhotoDetailFragmentList.clear();
        for(NewsPhotoDetail.Picture picture: mNewsPhotoDetail.getPicures()){
            PhotoDetailFragment fragment = new PhotoDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putString(AppConstant.PHOTO_DETAIL_IMGSRC,  picture.getImgSrc());
            fragment.setArguments(bundle);
            mPhotoDetailFragmentList.add(fragment);
        }
    }
}
