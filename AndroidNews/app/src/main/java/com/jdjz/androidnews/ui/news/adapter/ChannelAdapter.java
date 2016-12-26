package com.jdjz.androidnews.ui.news.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.aspsine.irecyclerview.universaladapter.ViewHolderHelper;
import com.aspsine.irecyclerview.universaladapter.recyclerview.CommonRecycleViewAdapter;
import com.jdjz.androidnews.R;
import com.jdjz.androidnews.bean.NewsChannelTable;

/**
 * Created by tchl on 2016-12-23.
 */
public class ChannelAdapter extends CommonRecycleViewAdapter<NewsChannelTable>{
    private OnItemClickListener mOnItemClickListener;
    @Override
    public void convert(ViewHolderHelper helper, NewsChannelTable newsChannelTable) {
        helper.setText(R.id.news_channel_tv,newsChannelTable.getNewsChannelName());
        if(newsChannelTable.getNewsChannelFixed()){
            helper.setTextColor(R.id.news_channel_tv, ContextCompat.getColor(mContext,R.color.gray));
        }else{
            helper.setTextColor(R.id.news_channel_tv, ContextCompat.getColor(mContext,R.color.gray_deep));
        }
    }

    public ChannelAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }
}
