package com.cui.video;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.cui.video.adapter.BindingViewHolder;

import java.util.List;

/**
 * Created by cuiyang on 16/6/3.
 */
public abstract class AbstractBaseAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected List<T> mData;
    protected Context mContext;
    private DecelerateInterpolator mDecelerateInterpolator = new DecelerateInterpolator(2f);
    private SwipeToLoadLayout refreshLayout;
    private int mDuration = 300;
    private int mLastPosition = -1;
    private boolean isFirstOnly = true;
    protected final int SCALE_ANIMA = 0;
    protected ViewDataBinding binding;

    /**
     * 不需要上拉下拉时refreshLayout可传null
     *
     * @param context
     * @param refreshLayout
     */
    public AbstractBaseAdapter(Context context, SwipeToLoadLayout refreshLayout) {
        this.mContext = context;
        this.refreshLayout = refreshLayout;
    }

    public void setNewData(List<T> data) {
        this.mData = data;
//        notifyItemInserted(0);
        notifyDataSetChanged();
        setLoadMore(data);
    }

    public void addData(List<T> data) {
        this.mData.addAll(data);
//        notifyItemInserted(this.mData.size());
        notifyDataSetChanged();
        setLoadMore(data);
    }

    /**
     * 当没有数据时不再上拉加载
     */
    private void setLoadMore(List<T> data) {
        if ((data == null || data.size() == 0) && refreshLayout != null) {
            refreshLayout.setLoadMoreEnabled(false);
        } else if (data != null && data.size() >= 10 && refreshLayout != null) {//数据大于10条时再恢复
            refreshLayout.setLoadMoreEnabled(true);
        }
    }

    public void addDataByPosition(T data, int position) {
        this.mData.add(position, data);
        notifyItemInserted(position);
    }

    public void removeOneData(int position) {
        this.mData.remove(position);
        notifyItemRemoved(position);
    }

    public List getData() {
        return mData;
    }

    public T getItem(int position) {
        return mData.get(position);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                setItemLayoutResource(), parent, false);
        BindingViewHolder holder = new BindingViewHolder(binding);
        return holder;
    }

    protected abstract int setItemLayoutResource();

    /**
     * item动画
     *
     * @param anim
     * @param holder
     */
    protected void openAnimation(int anim, RecyclerView.ViewHolder holder) {
        //第一次进入或者没有被显示过的item才会显示动画.否则显示过的就不再次显示为了性能着想
        if (!isFirstOnly || holder.getLayoutPosition() > mLastPosition) {
            switch (anim) {
                case SCALE_ANIMA:
                    for (Animator animator : getScaleAnimators(holder.itemView)) {
                        startAnim(animator, holder.getLayoutPosition());
                    }
                    break;
            }
            mLastPosition = holder.getLayoutPosition();//存储已经显示过的最大position,最后一个显示过的动画位置
        }
    }

    /**
     * 缩放动画
     *
     * @param view
     * @return
     */
    public Animator[] getScaleAnimators(View view) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 0.8f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 0.8f, 1f);
        return new ObjectAnimator[]{scaleX, scaleY};
    }

    /**
     * set anim to start when loading
     */
    protected void startAnim(Animator anim, int index) {
        anim.setDuration(mDuration).start();
        anim.setInterpolator(mDecelerateInterpolator);
    }

    /**
     * 是否每个item只显示一次动画.默认为true
     *
     * @param firstOnly
     */
    protected void setFirstOnly(boolean firstOnly) {
        this.isFirstOnly = firstOnly;
    }
}