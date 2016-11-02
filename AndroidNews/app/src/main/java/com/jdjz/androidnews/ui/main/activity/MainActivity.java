package com.jdjz.androidnews.ui.main.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.jdjz.androidnews.R;
import com.jdjz.androidnews.bean.TabEntity;
import com.jdjz.common.base.BaseActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    @Bind(R.id.tab_layout)
    CommonTabLayout tabLayout;

    private ArrayList<Fragment> mFragments = new ArrayList<>(); //tchl
    private static int tabLayoutHeight;
    private String[] mTitles = {"首页", "撩妹", "视频", "关注"};
    private int[] mIconUnselectIds = {
            R.mipmap.ic_home_normal, R.mipmap.ic_girl_normal, R.mipmap.ic_video_normal, R.mipmap.ic_care_normal
    };
    private int[] mIconSelectIds = {
            R.mipmap.ic_home_selected, R.mipmap.ic_girl_selected, R.mipmap.ic_video_selected, R.mipmap.ic_care_selected
    };
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private  NewsMainFragment newsMainFragment;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        ButterKnife.bind(this);
        tabLayout.measure(0,0);
        tabLayoutHeight=tabLayout.getMeasuredHeight();

        tabLayout.setTabData(mTabEntities, this, R.id.tab_layout, mFragments);// tchl
        tabLayout.showDot(1);//tchl

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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

    private void initTab() {
        //tchl begin
        for (String title : mTitles) {
            mFragments.add(SimpleCardFragment.getInstance( title));
        }
        //tchl end

        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        tabLayout.setTabData(mTabEntities);
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
            }
            @Override
            public void onTabReselect(int position){

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
