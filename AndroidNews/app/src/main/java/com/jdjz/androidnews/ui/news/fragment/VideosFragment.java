package com.jdjz.androidnews.ui.news.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.universaladapter.ViewHolderHelper;
import com.aspsine.irecyclerview.universaladapter.recyclerview.CommonRecycleViewAdapter;
import com.jdjz.androidnews.R;
import com.jdjz.androidnews.app.AppApplication;
import com.jdjz.androidnews.app.AppConstant;
import com.jdjz.androidnews.bean.VideoData;
import com.jdjz.androidnews.ui.news.contract.VideosListContract;
import com.jdjz.androidnews.ui.news.model.VideosListModel;
import com.jdjz.androidnews.ui.news.presenter.VideoListPresenter;
import com.jdjz.common.base.BaseFragment;
import com.jdjz.common.commonwidget.LoadingTip;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by tchl on 2017-01-16.
 */
public class VideosFragment extends BaseFragment<VideoListPresenter, VideosListModel> implements VideosListContract.View {

    @Bind(R.id.irc)
    IRecyclerView irc;
    @Bind(R.id.loadedTip)
    LoadingTip loadedTip;

    private CommonRecycleViewAdapter<VideoData> videoListAdapter;
    private String mVideoType;
    private int mStartPage=0;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this, view);
        return view;

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_news;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    protected void initView() {
        if(getArguments() != null){
            mVideoType = getArguments().getString(AppConstant.VIDEO_TYPE);
        }
        irc.setLayoutManager(new LinearLayoutManager(getContext()));
        videoListAdapter = new CommonRecycleViewAdapter<VideoData>(getContext(),R.layout.item_video_list) {
            @Override
            public void convert(ViewHolderHelper helper, VideoData videoData) {
                helper.setImageUrl(R.id.iv_logo,videoData.getTopicImg());
                helper.setText(R.id.tv_from,)
            }
        };
    }

    @Override
    public void returnVideosListData(List<VideoData> newSummaries) {

    }

    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void showErrorTip(String msg) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
