package com.cui.video.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.cui.video.AbstractBaseActivity;
import com.cui.video.R;
import com.cui.video.adapter.ShareSimpleListAdapter;
import com.cui.video.databinding.VideoDetailActBinding;
import com.cui.video.entity.ShareSimpleListItem;
import com.cui.video.entity.PlayerVideoEntity;
import com.cui.video.entity.ItemList;
import com.cui.video.presenter.iml.VideoDetailPresenter;
import com.cui.video.ui.activity.video.PlayerHelperActivity;
import com.cui.video.ui.fragment.FeaturedFrament;
import com.cui.video.utils.TimeUtils;
import com.cui.video.utils.img.ImageLoaderDisplay;
import com.cui.video.ui.activity.video.PlayerVideoActivity;
import com.cui.video.view.iml.VideoDetailContract;

public class VideoDeatilActivity extends AbstractBaseActivity<VideoDetailActBinding, VideoDetailPresenter>
        implements VideoDetailContract.VideoDetailView {
    private ItemList item;
    private final int Fliptxt_duration = 500;
    private PlayerVideoEntity playerVideoEntity;


    @Override
    protected void onCreated(Bundle savedInstanceState) {
        super.setFLAG_TRANSLUCENT_STATUS();
        super.setBindingTranstionAnim();
        playerVideoEntity = getIntent().getParcelableExtra(FeaturedFrament.FEATURED_DETAIL_ENTITY);
        item = playerVideoEntity.getList().get(0);
        ImageLoaderDisplay.imageLoaderCallback(this, binding.imgViewpageBackground, item.data.cover.feed, new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                binding.imgViewpageBackground.setImageBitmap(resource);
                binding.txtTitle.setText(item.data.title);
                binding.txtSubtitle.setText("#" + item.data.category + "   /   " + TimeUtils.secToTime(item.data.duration));
                binding.txtDesc.setFlipText(item.data.description, Fliptxt_duration);
            }
        });
        ImageLoaderDisplay.imageLoader(VideoDeatilActivity.this, binding.imgBackground, item.data.cover.blurred);
        super.setViewsClickListener(binding.imgBack, binding.imgViewpageBackground, binding.imgPlay,
                binding.txtShare);
    }

    /**
     * 初始化P层类
     */
    @Override
    protected VideoDetailPresenter initPresenter() {
        return new VideoDetailPresenter(this);
    }

    /**
     * 载入layout布局
     */
    @Override
    protected int setDataBindingContentViewId() {
        return R.layout.video_detail_act;
    }

    @Override
    public void onBackPressed() {
        binding.txtDesc.cancle();
        Glide.get(this).clearMemory();
        supportFinishAfterTransition();
        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_play:
            case R.id.img_viewpage_background:
                Intent i = new Intent(this, PlayerVideoActivity.class);
                i.putExtra(PlayerHelperActivity.PLAYER_ENTITY, playerVideoEntity);
                startActivity(i);
                break;
            case R.id.img_back:
                onBackPressed();
                break;
            case R.id.txt_share:
                showShareDialog();
                break;
        }
    }

    @Override
    public void showShareDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("分享");
        final ShareSimpleListAdapter adapter = new ShareSimpleListAdapter(this);
        String[] array = getResources().getStringArray(R.array.share_dialog_text);
        adapter.add(new ShareSimpleListItem.Builder(this)
                .content(array[0])
                .icon(R.drawable.ic_action_share_wechat_grey)
                .build());
        adapter.add(new ShareSimpleListItem.Builder(this)
                .content(array[1])
                .icon(R.drawable.ic_action_share_weibo_grey)
                .build());
        adapter.add(new ShareSimpleListItem.Builder(this)
                .content(array[2])
                .icon(R.drawable.ic_action_share_qq_grey)
                .build());
        adapter.add(new ShareSimpleListItem.Builder(this)
                .content(array[3])
                .icon(R.drawable.ic_action_more_grey)
                .build());
        builder.setAdapter(adapter, (dialog, which) -> {
            switch (which) {
                case 0:
                    getActivityHelper().shareToWeChat(item.data.playUrl);
                    break;
                case 1:
                    getActivityHelper().shareToWeibo(item.data.playUrl);
                    break;
                case 2:
                    getActivityHelper().shareToQQ(item.data.playUrl);
                    break;
                case 3:
                    getActivityHelper().shareMore(item.data.playUrl);
            }
        });
        builder.show();
    }
}
