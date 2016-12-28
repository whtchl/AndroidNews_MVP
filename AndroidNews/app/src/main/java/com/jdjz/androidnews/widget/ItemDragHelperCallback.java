package com.jdjz.androidnews.widget;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.jdjz.androidnews.ui.news.adapter.ChannelAdapter;

/**
 * Created by tchl on 2016-12-28.
 */
public class ItemDragHelperCallback extends ItemTouchHelper.Callback {
    private OnItemMoveListener mOnItemMoveListener;
    private boolean mIsLongPressEnabled;
    public interface OnItemMoveListener{
        boolean onItemMove(int fromProsition,int toProsition);
    }

    public ItemDragHelperCallback(OnItemMoveListener onItemMoveListener){
        mOnItemMoveListener = onItemMoveListener;
    }

    public boolean isLongPressDragEnabled(){return mIsLongPressEnabled;}

    private int setDragFlags(RecyclerView recyclerView){
        int dragFlags;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if(layoutManager instanceof GridLayoutManager || layoutManager instanceof StaggeredGridLayoutManager){
            dragFlags = ItemTouchHelper.DOWN | ItemTouchHelper.UP | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        }else{
            dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        }
        return dragFlags;
    }

    private boolean isDifferentItemViewType(RecyclerView.ViewHolder viewHolder,RecyclerView.ViewHolder target) {
        return viewHolder.getItemViewType() != target.getItemViewType();

    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = setDragFlags(recyclerView);
        int swipteFlags = 0;
        return makeMovementFlags(dragFlags,swipteFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }
}
