package com.jdjz.androidnews.ui.main.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jdjz.androidnews.R;
import com.jdjz.androidnews.ui.news.activity.AboutActivity;
import com.jdjz.common.base.BaseFragment;
import com.jdjz.common.commonwidget.NormalTitleBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by tchl on 2017-01-18.
 */
public class CareMainFragment extends BaseFragment {
    @Bind(R.id.ll_friend_zone)
    LinearLayout llFriendZone;
    @Bind(R.id.ll_daynight_toggle)
    LinearLayout llDaynightToggle;
    @Bind(R.id.ll_daynight_about)
    LinearLayout llDaynightAbout;
    @Bind(R.id.iv_add)
    ImageView ivAdd;
    @Bind(R.id.ntb)
    NormalTitleBar ntb;


    @Override
    protected int getLayoutResource() {
        return R.layout.fra_care_main;
    }

    @Override
    public void initPresenter() {


    }

    @Override
    protected void initView() {
        ntb.setTvLeftVisiable(false);
        ntb.setTitleText(getContext().getString(R.string.care_main_title));
    }


    @OnClick({R.id.ll_friend_zone, R.id.ll_daynight_toggle, R.id.ll_daynight_about, R.id.iv_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_friend_zone:
                break;
            case R.id.ll_daynight_toggle:
                break;
            case R.id.ll_daynight_about:
                AboutActivity.startAction(this.getContext());
                break;
            case R.id.iv_add:
                break;
        }
    }


}
