package com.jdjz.androidnews.ui.news.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.aspsine.irecyclerview.universaladapter.ViewHolderHelper;
import com.aspsine.irecyclerview.universaladapter.recyclerview.MultiItemRecycleViewAdapter;
import com.aspsine.irecyclerview.universaladapter.recyclerview.MultiItemTypeSupport;
import com.jdjz.androidnews.bean.NewsSummary;
import com.jdjz.common.commonutils.LogUtils;

import java.util.List;

/**
 * Created by tchl on 2016-11-23.
 */
public class NewListAdapter extends MultiItemRecycleViewAdapter<NewsSummary> {
    public static final int TYPE_ITEM = 0;
    public static final int TYPE_PHOTO_ITEM = 1;

    public NewListAdapter(Context context, final List<NewsSummary> datas) {
        super(context, datas, new MultiItemTypeSupport<NewsSummary>() {
            @Override
            public int getLayoutId(int itemType) {
                if (type == TYPE_PHOTO_ITEM)
                    return R.layout.item_news_photo;
                else return R.Layout.item_news;
            }

            @Override
            public int getItemViewType(int position, NewsSummary newsSummary) {
                if(!TextUtils.isEmpty(newsSummary.getDigest())){
                    return TYPE_ITEM;
                }
                return TYPE_PHOTO_ITEM;
            }
        });
    }

    @Override
    public void convert(ViewHolderHelper holder,final NewsSummary newsSummary,final int position){
        switch (holder.getLayoutId()){
            case R.layout.item_news;
                setItemValues(holder,newsSummary,getPosition(holder));
                break;
            case R.layout.item_news_photo:
                setPhotoItemValues(holder,newsSummary,getPosition(holder));
                break;
        }
    }

    /**
     * 普通样式
     * @param holder
     * @param newsSummary
     * @param position
     */
    private void setItemValues(final ViewHolderHelper holder,final NewsSummary newsSummary,final int position){
        String title = newsSummary.getTitle();
        if(title = null){
            title= newsSummary.getTitle();
        }
        String ptime = newsSummary.getPtime();
        String digest = newsSummary.getDigest();
        String imgSrc = newsSummary.getImgsrc();

        holder.setText(R.id.news_summary_title_tv,title);
        holder.setText(R.id.news_summary_ptime_tv,ptime);
        holder.setText(R.id.news_summary_digest_tv,digest);
        holder.setImageUrl(R.id.news_summary_photo_iv,imgSrc);
        holder.setOnClickListener(R.id.rl_root,new View.OnClickListener(){
           @Override
            public void onClick(View view){
               LogUtils.logd("start Action NewsDetailActivity");
           }
        });
    }

    /**
     * 图文样式
     * @param holder
     * @param newsSummary
     * @param position
     */
    private void setPhotoItemValues(ViewHolderHelper holder,final NewsSummary newsSummary,int position){
        String title = newsSummary.getTitle();
        String ptime = newsSummary.getPtime();
        holder.setText(R.id.news_summary_title_tv,title);
        holder.setText(R.id.news_summary_ptime_tv,ptime);
        setImageView(holder,newsSummary);
        holder.setOnClickListener(R.id.11_root,new View.OnClickListener(){
            LogUtils.logd("start Action NewsPhotoDetailActivity");
        });
    }


}
