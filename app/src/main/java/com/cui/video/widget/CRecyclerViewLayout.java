package com.cui.video.widget;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.cui.video.R;
import com.cui.video.utils.listener.MyRecyclerOnScrollListener;

import java.util.List;

import cn.iwgang.familiarrecyclerview.FamiliarRecyclerView;

/**
 * Created by cuiyang on 16/6/5.
 */
public class CRecyclerViewLayout extends FamiliarRecyclerView {

    public int page;
    SwipeToLoadLayout swipeToLoadLayout;

    public CRecyclerViewLayout(Context context) {
        super(context);
        init();
    }

    public CRecyclerViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CRecyclerViewLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private void init() {
        this.setItemAnimator(new DefaultItemAnimator());
    }

    /**
     * 初始化上拉和下拉刷新 默认到底自动加载
     *  不需要下拉或上拉直接传null
     */
    public void initRefreshSwipe(SwipeToLoadLayout swipeToLoadLayout, OnRefreshListener listener, OnLoadMoreListener listener1) {
        this.swipeToLoadLayout = swipeToLoadLayout;
        if (listener == null)
            swipeToLoadLayout.setRefreshEnabled(false);
        else
            swipeToLoadLayout.setOnRefreshListener(listener);

        if (listener1 == null) {
            swipeToLoadLayout.setLoadMoreEnabled(false);
        } else {
            swipeToLoadLayout.setOnLoadMoreListener(listener1);
            setIsNeedAutoLoderMore(true);
        }

    }

    /**
     * 是否需要到底自动加载
     *
     * @param isNeed
     */
    public void setIsNeedAutoLoderMore(boolean isNeed) {
        if (isNeed) {
            if (swipeToLoadLayout != null) {
                CRecyclerViewLayout.this.setOnScrollListener(new MyRecyclerOnScrollListener(swipeToLoadLayout));
            } else {
                throw new IllegalStateException("need swipeToLoadLayout");
            }
        } else {
            CRecyclerViewLayout.this.setOnScrollListener(null);
        }
    }

    /**
     * list为空则视为分页全部加载完毕不再有上拉加载
     */
    public boolean isBottom(List list) {
        if (list == null || list.size() == 0) {
            if (swipeToLoadLayout != null) {
                swipeToLoadLayout.setLoadMoreEnabled(false);
            } else {
                throw new IllegalStateException("need swipeToLoadLayout");
            }
            return true;
        }
        return false;
    }

    /**
     * 设置为空提示
     *
     * @return
     */
    public void setSimpleEmpty(boolean isKeeyHeaderFooter) {
        setEmptyView(LayoutInflater.from(this.getContext()).inflate(R.layout.list_empty, this, false), isKeeyHeaderFooter);
    }

    public View setEmptyViewSetTxt(int txtResrouceId, boolean isKeeyHeaderFooter) {
        return setEmptyViewSetTxt(this.getContext().getResources().getString(txtResrouceId), isKeeyHeaderFooter);
    }

    public View setEmptyViewSetTxt(String txtResrouce, boolean isKeeyHeaderFooter) {
        View v = LayoutInflater.from(this.getContext()).inflate(R.layout.list_empty, this, false);
        TextView txt = (TextView) v;
        txt.setText(txtResrouce);
        setEmptyView(v, isKeeyHeaderFooter);
        return v;
    }

    public void refresComplete() {
        swipeToLoadLayout.setRefreshing(false);
        swipeToLoadLayout.setLoadingMore(false);
    }

    /**
     * 下拉刷新传入true
     * 上拉加载传入false
     *
     * @param isRefresh
     * @return
     */
    public int getPage(boolean isRefresh) {
        if (isRefresh) {
            page = 1;
        } else {
            page++;
        }
        return page;
    }

}
