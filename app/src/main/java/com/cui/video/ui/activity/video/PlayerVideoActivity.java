package com.cui.video.ui.activity.video;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cui.video.R;
import com.cui.video.adapter.EndingAdapter;
import com.cui.video.entity.PlayerVideoEntity;
import com.cui.video.ui.activity.VideoDeatilActivity;
import com.cui.video.utils.img.ImageLoaderDisplay;
import com.devbrackets.android.exomedia.listener.OnCompletionListener;
import com.devbrackets.android.exomedia.listener.OnPreparedListener;

import cn.iwgang.familiarrecyclerview.FamiliarRecyclerView;


public class PlayerVideoActivity extends PlayerHelperActivity implements OnPreparedListener, OnCompletionListener, FamiliarRecyclerView.OnItemClickListener {
    private EndingAdapter adapter;
    private int index_position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置标题
        cVideoControlsView.setTitle(playerEntity.list.get(index_position).data.title);
        //设置缓冲完成监听
        emVideoView.setOnPreparedListener(this);
        //设置播放地址
        emVideoView.setVideoURI(Uri.parse(playerEntity.list.get(index_position).data.playUrl));
        //设置播放完成监听
        emVideoView.setOnCompletionListener(this);

        //ending 画面的设置
        binding.txtEndTitle.setText(playerEntity.list.get(index_position).data.title);
        adapter = new EndingAdapter(this, null);
        binding.mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.HORIZONTAL, true));
        binding.mRecyclerView.setAdapter(adapter);
        adapter.setNewData(playerEntity.getList());
        binding.mRecyclerView.setOnItemClickListener(this);
        ImageLoaderDisplay.imageLoader(this, binding.imgBackground, playerEntity.getList().get(index_position).data.cover.feed);
        binding.imgClose.setOnClickListener(this);

        setViewsClickListener(binding.imgShareMore, binding.imgShareQq, binding.imgShareWeichat, binding.imgShareWeibo);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.img_share_weichat:
                getActivityHelper().shareToWeChat(playerEntity.getList().get(index_position).data.playUrl);
                break;
            case R.id.img_share_weibo:
                getActivityHelper().shareToWeibo(playerEntity.getList().get(index_position).data.playUrl);
                break;
            case R.id.img_share_qq:
                getActivityHelper().shareToQQ(playerEntity.getList().get(index_position).data.playUrl);
                break;
            case R.id.img_share_more:
                getActivityHelper().shareMore(playerEntity.getList().get(index_position).data.playUrl);
                break;
        }

    }

    @Override
    public void onPrepared() {
        mVideoApi.play();
        binding.setShowEnding(false);
        binding.txtEndTitle.setText(playerEntity.list.get(index_position).data.title);
        ImageLoaderDisplay.imageLoader(this, binding.imgBackground, playerEntity.getList().get(index_position).data.cover.feed);
    }

    @Override
    public void onCompletion() {
        binding.setShowEnding(true);
        goFullscreen();
        binding.mRecyclerView.smoothScrollToPosition(index_position);
    }

    @Override
    public void onItemClick(FamiliarRecyclerView familiarRecyclerView, View view, int position) {
        this.index_position = position;
        //设置标题
        cVideoControlsView.setTitle(playerEntity.getList().get(position).data.title);
        //设置播放地址
        emVideoView.setVideoURI(Uri.parse(playerEntity.list.get(position).data.playUrl));

    }
}
