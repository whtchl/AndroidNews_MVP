package com.jdjz.androidnews.ui.main.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jdjz.androidnews.R;
import com.jdjz.common.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
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

    }
}
