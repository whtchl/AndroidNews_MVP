package com.jdjz.androidnews.ui.news.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.OnLoadMoreListener;
import com.aspsine.irecyclerview.OnRefreshListener;
import com.aspsine.irecyclerview.widget.LoadMoreFooterView;
import com.jdjz.androidnews.R;
import com.jdjz.androidnews.app.AppConstant;
import com.jdjz.androidnews.bean.NewsSummary;
import com.jdjz.androidnews.ui.news.adapter.NewListAdapter;
import com.jdjz.androidnews.ui.news.contract.NewsListContract;
import com.jdjz.androidnews.ui.news.model.NewsListModel;
import com.jdjz.androidnews.ui.news.presenter.NewsListPresenter;
import com.jdjz.common.base.BaseFragment;
import com.jdjz.common.commonutils.LogUtils;
import com.jdjz.common.commonwidget.LoadingTip;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.Observable;

/**
 * des:新闻 Fragment
 * Created by tchl on 2016-11-21.
 */
public class NewsFrament extends BaseFragment<NewsListPresenter,NewsListModel> implements NewsListContract.View,OnRefreshListener,OnLoadMoreListener {
    //@Bind(R.id.irc)
    IRecyclerView irc;

    //@Bind(R.id.loadedTip)
    LoadingTip loadedTip;

    private NewListAdapter newListAdapter;
    private List<NewsSummary> datas = new ArrayList<>();

    private String mNewsId;
    private String mNewsType;
    private int mStartPage=0;
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
        irc =(IRecyclerView) rootView.findViewById(R.id.irc);
        loadedTip = (LoadingTip) rootView.findViewById(R.id.loadedTip);
        if(getArguments()!=null){
            mNewsId  =getArguments().getString(AppConstant.NEWS_ID);
            mNewsType = getArguments().getString(AppConstant.NEWS_TYPE);
        }
        irc.setLayoutManager(new LinearLayoutManager(getContext()));
        newListAdapter = new NewListAdapter(getContext(),datas);
        irc.setAdapter(newListAdapter);
        irc.setOnRefreshListener(this);
        irc.setOnLoadMoreListener(this);
        //数据为空才重新发起请求
        if(newListAdapter.getSize()<=0){
            mStartPage = 0;
            mPresenter.getNewsListDataRequest(mNewsType,mNewsId,mStartPage);
        }
    }

    @Override
    public void returnNewsListData(List<NewsSummary> newsSummaries){
        if(newsSummaries !=null){
            mStartPage +=20;
            if(newListAdapter.getPageBean().isRefresh()){
                irc.setRefreshing(false);
                newListAdapter.replaceAll(newsSummaries);
            }else{
                if(newsSummaries.size()>0){
                    irc.setLoadMoreStatus(LoadMoreFooterView.Status.GONE);
                    newListAdapter.addAll(newsSummaries);
                }else{
                    irc.setLoadMoreStatus(LoadMoreFooterView.Status.THE_END);
                }
            }
        }
    }

    @Override
    public void onRefresh(){
        LogUtils.logd("NewsFragment onRefresh");
        newListAdapter.getPageBean().setRefresh(true);
        mStartPage=0;
        //发起请求
        irc.setRefreshing(true);
        mPresenter.getNewsListDataRequest(mNewsType,mNewsId,mStartPage);
    }

    @Override
    public void onLoadMore(View loadMoreView) {
        newListAdapter.getPageBean().setRefresh(false);
        //发起请求
        irc.setLoadMoreStatus(LoadMoreFooterView.Status.LOADING);
        mPresenter.getNewsListDataRequest(mNewsType,mNewsId,mStartPage);
    }

    @Override
    public void showLoading(String title){
        if(newListAdapter.getPageBean().isRefresh()){
            loadedTip.setLoadingTip(LoadingTip.LoadStatus.loading);
            LogUtils.logd("show load tip");
        }
    }


    @Override
    public void stopLoading() {
         loadedTip.setLoadingTip(LoadingTip.LoadStatus.finish);
    }

    @Override
    public void showErrorTip(String msg) {
        if( newListAdapter.getPageBean().isRefresh()) {
            loadedTip.setLoadingTip(LoadingTip.LoadStatus.error);
            loadedTip.setTips(msg);
            irc.setRefreshing(false);
        }else{
            irc.setLoadMoreStatus(LoadMoreFooterView.Status.ERROR);
        }
    }

    @Override
    public void scrolltoTop() {

    }
}
