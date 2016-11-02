package com.jdjz.androidnews.ui.main.fragment;

import android.view.View;

import com.jdjz.androidnews.R;
import com.jdjz.androidnews.app.AppConstant;
import com.jdjz.common.base.BaseFragment;

/**
 * Created by tchl on 2016-11-01.
 */
public class NewsMainFragment extends BaseFragment<NewsMainPresenter,NewsMainModel> implements NewsMainContactView.View{


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
        fab.setOnClickListener(new View.OnClickListener(){
           @Override
            public void onClick(View view){
               mRxManager.post(AppConstant.NEWS_LIST_TO_TOP,"");
           }
        });
    }
}
