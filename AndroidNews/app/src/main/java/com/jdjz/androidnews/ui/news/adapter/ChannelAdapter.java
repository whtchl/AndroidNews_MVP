package com.jdjz.androidnews.ui.news.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.aspsine.irecyclerview.universaladapter.ViewHolderHelper;
import com.aspsine.irecyclerview.universaladapter.recyclerview.CommonRecycleViewAdapter;
import com.jdjz.androidnews.R;
import com.jdjz.androidnews.app.AppConstant;
import com.jdjz.androidnews.bean.NewsChannelTable;
import com.jdjz.androidnews.ui.news.event.ChannelItemMoveEvent;
import com.jdjz.androidnews.widget.ItemDragHelperCallback;
import com.jdjz.common.baserx.RxBus;
import com.jdjz.common.commonutils.LogUtils;
import com.jdjz.common.commonwidget.OnNoDoubleClickListener;

import java.util.Collection;
import java.util.Collections;

/**
 * Created by tchl on 2016-12-23.
 */
public class ChannelAdapter extends CommonRecycleViewAdapter<NewsChannelTable> implements
        ItemDragHelperCallback.OnItemMoveListener{
    private OnItemClickListener mOnItemClickListener;
    private ItemDragHelperCallback mItemDragHelperCallback;
    @Override
    public void convert(ViewHolderHelper helper, NewsChannelTable newsChannelTable) {
        helper.setText(R.id.news_channel_tv,newsChannelTable.getNewsChannelName());
        if(newsChannelTable.getNewsChannelFixed()){
            LogUtils.logd("grap channel");
            helper.setTextColor(R.id.news_channel_tv, ContextCompat.getColor(mContext,R.color.gray));
        }else{
            LogUtils.logd("grap deep channel");
            helper.setTextColor(R.id.news_channel_tv, ContextCompat.getColor(mContext,R.color.gray_deep));
        }
        handleOnClick(helper,newsChannelTable);
        handleLongPress(helper,newsChannelTable);
    }
    private void handleLongPress(ViewHolderHelper helper,final NewsChannelTable newsChannelTable){
        if(mItemDragHelperCallback!=null){
            helper.itemView.setOnTouchListener(new View.OnTouchListener(){

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    mItemDragHelperCallback.setLongPressEnabled(newsChannelTable.getNewsChannelIndex()==0?false:true);
                    return false;
                }
            });
        }
    }
    private void handleOnClick(final ViewHolderHelper helper, final NewsChannelTable newsChannelTable) {
        if(mOnItemClickListener != null){
            helper.itemView.setOnClickListener(new OnNoDoubleClickListener() {
                @Override
                protected void onNoDoubleClick(View v) {
                    if(!newsChannelTable.getNewsChannelFixed()){
                        mOnItemClickListener.onItemClick(v,helper.getLayoutPosition());
                    }
                }
            });
        }
    }

    public ChannelAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setItemDragHelperCallback(ItemDragHelperCallback itemDragHelperCallback){
        mItemDragHelperCallback = itemDragHelperCallback;
    }

    @Override
    public boolean onItemMove(int fromProsition, int toProsition) {
        if(isChannelFixed(fromProsition,toProsition)){
            LogUtils.logd("onItemMove false");
            return false;
        }
        Collections.swap(getAll(),fromProsition,toProsition);
        notifyItemMoved(fromProsition,toProsition);
        RxBus.getInstance().post(AppConstant.CHANNEL_SWAP,
                new ChannelItemMoveEvent(fromProsition,toProsition));
        LogUtils.logd("onItemMove true");
        return true;
    }

    private boolean isChannelFixed(int fromProsition, int toProsition) {
        return (getAll().get(fromProsition).getNewsChannelFixed()||
                getAll().get(toProsition).getNewsChannelFixed())&&(fromProsition==0 || toProsition == 0);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
