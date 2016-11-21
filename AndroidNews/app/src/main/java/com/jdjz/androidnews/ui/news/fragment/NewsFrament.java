package com.jdjz.androidnews.ui.news.fragment;

import com.jdjz.androidnews.R;
import com.jdjz.androidnews.ui.news.contract.NewsListContract;
import com.jdjz.androidnews.ui.news.presenter.NewsListPresenter;
import com.jdjz.common.base.BaseFragment;

/**
 * des:新闻 Fragment
 * Created by tchl on 2016-11-21.
 */
public class NewsFrament extends BaseFragment<NewsListPresenter,NewsListModel> implements NewsListContract.Model,onRefreshListener,OnLoadMoreListener{

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_news;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this,mModel);
    }

    @Override
    protected void initView() {

    }
}
