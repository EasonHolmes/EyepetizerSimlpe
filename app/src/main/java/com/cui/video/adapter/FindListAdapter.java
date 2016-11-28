package com.cui.video.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.cui.video.AbstractBaseAdapter;
import com.cui.video.R;
import com.cui.video.databinding.ItemFindBinding;
import com.cui.video.entity.FindListEntity;
import com.cui.video.utils.img.ImageLoaderDisplay;

/**
 * Created by cuiyang on 2016/11/22.
 */

public class FindListAdapter extends AbstractBaseAdapter<FindListEntity.ItemListEntity> {
    /**
     * 不需要上拉下拉时refreshLayout可传null
     *
     * @param context
     * @param refreshLayout
     */
    public FindListAdapter(Context context, SwipeToLoadLayout refreshLayout) {
        super(context, refreshLayout);
    }

    @Override
    protected int setItemLayoutResource() {
        return R.layout.item_find;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        BindingViewHolder viewHolder = (BindingViewHolder) holder;
        ItemFindBinding binding = (ItemFindBinding) viewHolder.getBinding();
//        super.OpenAnimation(super.SCALE_ANIMA, viewHolder);
        FindListEntity.ItemListEntity entity = getItem(position);
        binding.txtTitle.setText(entity.getData().getTitle());
        ImageLoaderDisplay.imageLoader(mContext, binding.imgItem, entity.getData().getImage());
    }
}
