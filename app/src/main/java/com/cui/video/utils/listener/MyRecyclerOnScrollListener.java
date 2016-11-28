package com.cui.video.utils.listener;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;

/**
 * 使用SwipeToLoadLayout上拉到底后自动加载的listener
 * 如果需要用户到底后再上拉才进行加载则不需要设置这个listener
 * Created by cuiyang on 16/6/3.
 */
public class MyRecyclerOnScrollListener extends RecyclerView.OnScrollListener {
    private SwipeToLoadLayout swipeToLoadLayout;

    public MyRecyclerOnScrollListener(SwipeToLoadLayout swipeToLoadLayout) {
        this.swipeToLoadLayout = swipeToLoadLayout;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            if (!ViewCompat.canScrollVertically(recyclerView, 1)) {
                swipeToLoadLayout.setLoadingMore(true);
            }
        }
    }
}
