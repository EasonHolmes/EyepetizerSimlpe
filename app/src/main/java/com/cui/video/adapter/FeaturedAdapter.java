package com.cui.video.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.cui.video.AbstractBaseAdapter;
import com.cui.video.R;
import com.cui.video.databinding.ItemFeaturedBinding;
import com.cui.video.entity.fetured.ItemList;
import com.cui.video.utils.TimeUtils;
import com.cui.video.utils.img.ImageLoaderDisplay;

/**
 * Created by cuiyang on 2016/11/20.
 */

public class FeaturedAdapter extends AbstractBaseAdapter<ItemList> {
    /**
     * 不需要上拉下拉时refreshLayout可传null
     *
     * @param context
     * @param refreshLayout
     */
    public FeaturedAdapter(Context context, SwipeToLoadLayout refreshLayout) {
        super(context, refreshLayout);
    }

    @Override
    protected int setItemLayoutResource() {
        return R.layout.item_featured;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        BindingViewHolder viewHolder = (BindingViewHolder) holder;
        ItemFeaturedBinding binding = (ItemFeaturedBinding) viewHolder.getBinding();
        ItemList entity = getItem(position);
        ImageLoaderDisplay.imageLoaderOverride800(mContext, binding.imgItem, entity.data.cover.feed);
        binding.txtTitle.setText(entity.data.title);
        binding.txtClasses.setText("#" + entity.data.category);
        binding.txtSubtitle.setText(String.valueOf(TimeUtils.secToTime( entity.data.duration)));
    }
}
