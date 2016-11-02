package com.jdjz.androidnews.bean;

import com.flyco.tablayout.listener.CustomTabEntity;

/**
 * Created by happen on 2016/10/31.
 */
public class TabEntity implements CustomTabEntity {
    public String title;
    public int selectedIcon;
    public int unSelectedIcon;

    public TabEntity(String title,int selectedIcon,int unSelectIcon){
        this.title = title;
        this.selectedIcon = selectedIcon;
        this.unSelectedIcon = unSelectIcon;
    }
    @Override
    public String getTabTitle() {
        return title;
    }

    @Override
    public int getTabSelectedIcon() {
        return selectedIcon;
    }

    @Override
    public int getTabUnselectedIcon() {
        return unSelectedIcon;
    }
}
