package com.jdjz.androidnews.ui.main.fragment;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toolbar;

import com.jdjz.androidnews.R;
import com.jdjz.androidnews.app.AppConstant;
import com.jdjz.androidnews.bean.NewsChannelTable;
import com.jdjz.androidnews.ui.main.contract.NewsMainContract;
import com.jdjz.androidnews.ui.main.model.NewsMainModel;
import com.jdjz.androidnews.ui.main.presenter.NewsMainPresenter;
import com.jdjz.common.base.BaseFragment;
import android.support.v4.view.ViewPager;
import android.support.design.widget.TabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by tchl on 2016-11-01.
 */
public class NewsMainFragment extends BaseFragment<NewsMainPresenter,NewsMainModel> implements NewsMainContract.View{

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tabs)
    TabLayout tabs;
    @Bind(R.id.add_channel_iv)
    ImageView addChannelIv;
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    private BaseFragmentAdapter fragmentAdapter;
    @Override
    protected int getLayoutResource() {
        return  R.layout.app_bar_news;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this,mModel);
    }

    @Override
    protected void initView() {
        mPresenter.lodeMineChannelsRequest();
        /*
        fab.setOnClickListener(new View.OnClickListener(){
           @Override
            public void onClick(View view){
               mRxManager.post(AppConstant.NEWS_LIST_TO_TOP,"");
           }
        });*/
    }

    @OnClick(R.id.add_channel_iv)
    public void clickAdd(){
        //NewsChannelActivity.startAction(getContext());
    }

    @Override
    public void returnMineNewsChannels(List<NewsChannelTable> newsChannelsMine){
        if(newsChannelsMine != null){
            List<String> channelNames = new ArrayList<>();
            List<FragmentActivity> mNewsFragmentList = new ArrayList<>();
            for(int  i=0;i<newsChannelsMine.size();i++){
                channelNames.add(newsChannelsMine.get(i).getNewsChannelName());
                mNewsFragmentList.add(createListFragments(newsChannelsMine.get(i)));
            }
            fragmentAdapter = new BaseFragmentAdapter(getChildFragmentManager(),mNewsFragmentList,channelNames);
            viewPager.setAdapter(fragmentAdapter);
            tabs.setupWithViewPager(viewPager);
            MyUtils.dynamicSetTabLayoutMode(tabs);
            setPageChangeListener();
        }
    }

    private void setPageChangeListener(){
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){

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

    private NewsFrament createListFragments(NewsChannelTable newsChannel){
        NewsFrament fragment = new NewsFrament();
    }

}
