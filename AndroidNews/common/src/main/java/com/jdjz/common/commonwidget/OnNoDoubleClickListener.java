package com.jdjz.common.commonwidget;

import android.view.View;
import android.view.View.OnClickListener;

import java.util.Calendar;

/**
 *  des:防止重复点击
 * Created by tchl on 2016-12-27.
 */
public abstract class OnNoDoubleClickListener implements OnClickListener{
    public static final int MIN_CLICK_DELAY_TIME = 1000;
    private long lastClickTime = 0;

    @Override
    public void onClick(View v){
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if(currentTime-lastClickTime>MIN_CLICK_DELAY_TIME){
            lastClickTime = currentTime;
            onNoDoubleClick(v);
        }
    }
    protected abstract void onNoDoubleClick(View v);
}
