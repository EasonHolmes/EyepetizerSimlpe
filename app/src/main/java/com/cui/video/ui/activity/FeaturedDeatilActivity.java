package com.cui.video.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.cui.video.AbstractBaseActivity;
import com.cui.video.R;
import com.cui.video.databinding.FeturedDetailActBinding;
import com.cui.video.entity.PlayerVideoEntity;
import com.cui.video.entity.ItemList;
import com.cui.video.presenter.iml.FeaturedDetailPresenter;
import com.cui.video.ui.activity.video.PlayerHelperActivity;
import com.cui.video.ui.fragment.FeaturedFrament;
import com.cui.video.utils.TimeUtils;
import com.cui.video.utils.img.ImageLoaderDisplay;
import com.cui.video.ui.activity.video.PlayerVideoActivity;
import com.cui.video.view.iml.FeaturedDetailContract;

public class FeaturedDeatilActivity extends AbstractBaseActivity<FeturedDetailActBinding, FeaturedDetailPresenter>
        implements FeaturedDetailContract.FeaturedDetailView {
    private ItemList item;
    private final int Fliptxt_duration = 500;

    @Override
    protected void onCreated(Bundle savedInstanceState) {
        super.setFLAG_TRANSLUCENT_STATUS();
        super.setBindingTranstionAnim();
        item = getIntent().getParcelableExtra(FeaturedFrament.FEATURED_DETAIL_ENTITY);
        Glide.with(this)
                .load(item.data.cover.detail)
                .asBitmap()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        binding.imgViewpageBackground.setImageBitmap(resource);
                        startLollipopAnim();
                    }
                });
        ImageLoaderDisplay.imageLoader(FeaturedDeatilActivity.this, binding.imgBackground, item.data.cover.blurred);
        super.setViewsClickListener(binding.imgBack, binding.imgViewpageBackground, binding.imgPlay);
    }

    private void startLollipopAnim() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //方式一使用系统的 需要img_share.getTransitionName()系统来定位是哪两个view
            ViewCompat.setTransitionName(binding.imgViewpageBackground, getResources().getString(R.string.featured_item_share_txt));
        }
        binding.txtTitle.setText(item.data.title);
        binding.txtSubtitle.setText("#" + item.data.category + "   /   " + TimeUtils.secToTime(item.data.duration));
        binding.txtDesc.setFlipText(item.data.description, Fliptxt_duration);
    }

    /**
     * 初始化P层类
     */
    @Override
    protected FeaturedDetailPresenter initPresenter() {
        return new FeaturedDetailPresenter(this);
    }

    /**
     * 载入layout布局
     */
    @Override
    protected int setDataBindingContentViewId() {
        return R.layout.fetured_detail_act;
    }

    @Override
    public void onBackPressed() {
        supportFinishAfterTransition();
        binding.txtDesc.cancle();
        Glide.get(this).clearMemory();
        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_play:
            case R.id.img_viewpage_background:
                Intent i = new Intent(this, PlayerVideoActivity.class);
                PlayerVideoEntity entity = new PlayerVideoEntity();
                entity.setUrl(item.data.playUrl);
                entity.setName(item.data.title);
                entity.setTitle(item.data.title);
                i.putExtra(PlayerHelperActivity.PLAYER_ENTITY, entity);
                startActivity(i);
                break;
            case R.id.img_back:
                onBackPressed();
                break;
        }
    }
}
