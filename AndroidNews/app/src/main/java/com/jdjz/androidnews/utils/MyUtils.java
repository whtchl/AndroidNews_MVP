package com.jdjz.androidnews.utils;

import android.app.Activity;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.view.ViewGroup;

import com.jdjz.androidnews.app.AppApplication;

/**
 * Created by tchl on 2016-11-14.
 */
public class MyUtils {
    public static void dynamicSetTabLayoutMode(TabLayout tabLayout){
        int tabWidth = calculateTabWidth(tabLayout);
        int screenWidth = getScreenWith();
        if(tabWidth<=screenWidth){
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
        }else{
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }
    }

    private static int calculateTabWidth(TabLayout tabLayout){
        int tabWidth = 0;
        for(int i=0; i < tabLayout.getChildCount(); i++){
            final View view = tabLayout.getChildAt(i);
            view.measure(0,0);
            tabWidth += view.getMeasuredWidth();
        }
        return tabWidth;
    }

    public static int getScreenWith(){
        return AppApplication.getAppContext().getResources().getDisplayMetrics().widthPixels;
    }
    public static View getRootView(Activity context){
        return ((ViewGroup) context.findViewById(android.R.id.content)).getChildAt(0);
    }
}
