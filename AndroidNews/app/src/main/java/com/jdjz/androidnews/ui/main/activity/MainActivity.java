package com.jdjz.androidnews.ui.main.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.BoolRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jdjz.androidnews.R;
import com.jdjz.androidnews.app.AppConstant;
import com.jdjz.androidnews.bean.TabEntity;
import com.jdjz.androidnews.ui.main.fragment.CareMainFragment;
import com.jdjz.androidnews.ui.main.fragment.NewsMainFragment;
import com.jdjz.androidnews.ui.main.fragment.VideoMainFragment;
import com.jdjz.common.base.BaseActivity;
import com.jdjz.common.commonutils.LogUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.functions.Action1;

public class MainActivity extends BaseActivity {

    @Bind(R.id.fl_body)
    FrameLayout flBody;
    @Bind(R.id.tab_layout)
    CommonTabLayout tabLayout;
    private static int tabLayoutHeight;
/*
    private String[] mTitles = {"首页", "美女", "视频", "关注"};
    private int[] mIconUnselectIds = {
            R.mipmap.ic_home_normal, R.mipmap.ic_girl_normal, R.mipmap.ic_video_normal, R.mipmap.ic_care_normal};
    private int[] mIconSelectIds = {
            R.mipmap.ic_home_selected, R.mipmap.ic_girl_selected, R.mipmap.ic_video_selected, R.mipmap.ic_care_selected};
*/


    private String[] mTitles = {"首页",  "视频", "关注"};
    private int[] mIconUnselectIds = {
            R.mipmap.ic_home_normal,  R.mipmap.ic_video_normal, R.mipmap.ic_care_normal};
    private int[] mIconSelectIds = {
            R.mipmap.ic_home_selected, R.mipmap.ic_video_selected, R.mipmap.ic_care_selected};

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private NewsMainFragment newsMainFragment;
    private SimpleCardFragment newsMainFragment2;
    private VideoMainFragment videoMainFragment;
    private CareMainFragment careMainFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tabLayout.measure(0,0);
        tabLayoutHeight=tabLayout.getMeasuredHeight();
        initFragment(savedInstanceState);

        //监听菜单显示或隐藏
        mRxManager.on(AppConstant.MENU_SHOW_HIDE,new Action1<Boolean>(){

            @Override
            public void call(Boolean aBoolean) {
                startAnimation(aBoolean);
            }
        });
    }

    private void startAnimation(boolean showOrHide){
        final ViewGroup.LayoutParams layoutParams = tabLayout.getLayoutParams();
        final ValueAnimator valueAnimator;
        ObjectAnimator alpha;
        if(!showOrHide){
            valueAnimator = ValueAnimator.ofInt(tabLayoutHeight,0);
            alpha = ObjectAnimator.ofFloat(tabLayout,"alpha",1,0);
        }else{
            valueAnimator = ValueAnimator.ofInt(0,tabLayoutHeight);
            alpha = ObjectAnimator.ofFloat(tabLayout,"alpha",0,1);
        }
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                layoutParams.height = (int)valueAnimator.getAnimatedValue();
                tabLayout.setLayoutParams(layoutParams);
            }
        });
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(500);
        animatorSet.playTogether(valueAnimator,alpha);
        animatorSet.start();
    }

    @Override
    public int getLayoutId() {
        return R.layout.act_main;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        initTab();
    }

    /**
     * 初始化tab
     */
    private void initTab() {
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        tabLayout.setTabData(mTabEntities);
        //点击监听
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                SwitchTo(position);
            }
            @Override
            public void onTabReselect(int position) {
            }
        });
    }

    /**
     * 入口
     * @param activity
     */
    public static void startAction(Activity activity){
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                com.jdjz.common.R.anim.fade_out);
    }

    /*
    *   初始化碎片
    * */
    private  void initFragment(Bundle savedInstanceState){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        int currentTabPosition  = 0;
        if(savedInstanceState != null){
            newsMainFragment = (NewsMainFragment)getSupportFragmentManager().findFragmentByTag("NewsMainFragment");
            newsMainFragment2 = (SimpleCardFragment)getSupportFragmentManager().findFragmentByTag("newsMainFragment2");
            videoMainFragment = (VideoMainFragment)getSupportFragmentManager().findFragmentByTag("VideoMainFragment");
            careMainFragment = (CareMainFragment) getSupportFragmentManager().findFragmentByTag("careMainFragment");

            currentTabPosition = savedInstanceState.getInt(AppConstant.HOME_CURRENT_TAB_POSITION);

        }else{
            newsMainFragment  =  new NewsMainFragment();
            newsMainFragment2 =  SimpleCardFragment.getInstance( "2");
            videoMainFragment =  new VideoMainFragment();
            careMainFragment = new CareMainFragment();
            //newsMainFragment4 =  SimpleCardFragment.getInstance( "4");
            transaction.add(R.id.fl_body,newsMainFragment,"newsMainFragment");
            transaction.add(R.id.fl_body,newsMainFragment2,"newsMainFragment2");
            transaction.add(R.id.fl_body,videoMainFragment,"videoMainFragment");
            transaction.add(R.id.fl_body,careMainFragment,"careMainFragment");
            //transaction.add(R.id.fl_body,newsMainFragment4,"newsMainFragment4");
        }
        transaction.commit();
        SwitchTo(currentTabPosition);
        tabLayout.setCurrentTab(currentTabPosition);
    }


    /**
     * 切换
     */
    private void SwitchTo(int position){
        LogUtils.logd("主页菜单 position："+position);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (position){
            //首页
            case 0:
                transaction.show(newsMainFragment);
                transaction.hide(newsMainFragment2);
                transaction.hide(videoMainFragment);
                transaction.hide(careMainFragment);
                //transaction.hide(newsMainFragment4);
                transaction.commitAllowingStateLoss();
                break;
/*            case 1:
                transaction.hide(newsMainFragment);
                transaction.show(newsMainFragment2);
                transaction.hide(videoMainFragment);
                transaction.hide(careMainFragment);
                //transaction.hide(newsMainFragment4);
                transaction.commitAllowingStateLoss();
                break;*/
            case 1:
                transaction.hide(newsMainFragment);
                //transaction.hide(newsMainFragment2);
                transaction.show(videoMainFragment);
                transaction.hide(careMainFragment);
                //transaction.hide(newsMainFragment4);
                transaction.commitAllowingStateLoss();
                break;
            case 2:
                transaction.hide(newsMainFragment);
                //transaction.hide(newsMainFragment2);
                transaction.hide(videoMainFragment);
                transaction.show(careMainFragment);
                //transaction.show(newsMainFragment4);
                transaction.commitAllowingStateLoss();
                break;
            default:
                break;
        }

    }
}