package com.jdjz.androidnews.ui.news.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.jdjz.androidnews.R;
import com.jdjz.androidnews.app.AppConstant;
import com.jdjz.androidnews.bean.NewsPhotoDetail;
import com.jdjz.androidnews.ui.news.fragment.PhotoDetailFragment;
import com.jdjz.common.base.BaseActivity;
import com.jdjz.common.base.BaseFragmentStatePagerAdapter;
import com.jdjz.common.commonutils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.functions.Action1;

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
        LogUtils.logd("startAction");
        Intent intent = new Intent(context, NewsPhotoDetailActivity.class);
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
    protected void onStart(){
        super.onStart();
        mRxManager.on(AppConstant.PHOTO_TAB_CLICK, new Action1<Object>() {
            @Override
            public void call(Object o) {
                LogUtils.logd(AppConstant.PHOTO_TAB_CLICK+ "  thread:"+Thread.currentThread().getName());
                if (photoDetailTitleTv.getVisibility() == View.VISIBLE) {
                    startAnimation(View.GONE, 0.9f, 0.5f);
                } else {
                    photoDetailTitleTv.setVisibility(View.VISIBLE);
                    startAnimation(View.VISIBLE, 0.5f, 0.9f);
                }
            }
        });
    }
    private void startAnimation(final int endState, float startValue, float endValue) {
        ObjectAnimator animator = ObjectAnimator
                .ofFloat(photoDetailTitleTv, "alpha", startValue, endValue)
                .setDuration(200);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                photoDetailTitleTv.setVisibility(endState);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }
    @Override
    public void initView() {
        mNewsPhotoDetail = getIntent().getParcelableExtra(AppConstant.PHOTO_DETAIL);
        createFragment(mNewsPhotoDetail);
        initViewPager();
        setPhotoDetailTitle(0);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    finishAfterTransition();
                }else{
                    finish();
                }
            }
        });

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
                setPhotoDetailTitle(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void createFragment(NewsPhotoDetail mNewsPhotoDetail) {
        mPhotoDetailFragmentList.clear();
        for(NewsPhotoDetail.Picture picture: mNewsPhotoDetail.getPictures()){
            PhotoDetailFragment fragment = new PhotoDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putString(AppConstant.PHOTO_DETAIL_IMGSRC,  picture.getImgSrc());
            LogUtils.logd("pic src:"+picture.getImgSrc());
            fragment.setArguments(bundle);
            mPhotoDetailFragmentList.add(fragment);
        }
    }

    public void setPhotoDetailTitle(int position) {
        String title = getTitle(position);
        photoDetailTitleTv.setText(getString(R.string.photo_detail_title, position + 1,
                mPhotoDetailFragmentList.size(), title));
    }

    private String getTitle(int position) {
        String title = mNewsPhotoDetail.getPictures().get(position).getTitle();
        if (title == null) {
            title = mNewsPhotoDetail.getTitle();
        }
        return title;
    }
}
