package com.jdjz.common.base;

/**
 * Created by happen on 2016/11/6.
 */
public interface BaseView {
    /*******内嵌加载*******/
    void showLoading(String title);
    void stopLoading();
    void showErrorTip(String msg);
}
