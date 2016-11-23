package com.jdjz.androidnews.ui.news.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.OnLoadMoreListener;
import com.jdjz.androidnews.R;
import com.jdjz.androidnews.app.AppConstant;
import com.jdjz.androidnews.bean.NewsSummary;
import com.jdjz.androidnews.ui.news.contract.NewsListContract;
import com.jdjz.androidnews.ui.news.model.NewsListModel;
import com.jdjz.androidnews.ui.news.presenter.NewsListPresenter;
import com.jdjz.common.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.Observable;

/**
 * des:新闻 Fragment
 * Created by tchl on 2016-11-21.
 */
public class NewsFrament extends BaseFragment<NewsListPresenter,NewsListModel> implements NewsListContract.Model,onRefreshListener,OnLoadMoreListener {
    @Bind(R.id.irc)
    IRecyclerView irc;

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
        if(newListAdapter.getSzie()<=0){
            mStartPage = 0;
            mPresenter.getNewsListDataRequest(mNewsType,mNewsId,mStatePage);
        }
    }

    @Override
    public Observable<List<NewsSummary>> getNewsListData(String type, String id, int startPage) {
        return null;
    }

    @Override
    public void onLoadMore(View loadMoreView) {

    }
}
