package com.cui.video.ui.activity.video;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.LinearLayout;

import com.cui.video.adapter.EndingAdapter;
import com.cui.video.adapter.FeaturedAdapter;
import com.cui.video.utils.img.ImageLoaderDisplay;
import com.devbrackets.android.exomedia.listener.OnCompletionListener;
import com.devbrackets.android.exomedia.listener.OnPreparedListener;


public class PlayerVideoActivity extends PlayerHelperActivity implements OnPreparedListener, OnCompletionListener {
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

        adapter = new EndingAdapter(this, null);
        binding.mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.HORIZONTAL, true));
        binding.mRecyclerView.setAdapter(adapter);
        adapter.setNewData(playerEntity.getList());
        ImageLoaderDisplay.imageLoader(this, binding.imgBackground, playerEntity.getList().get(index_position).data.cover.feed);
        binding.mRecyclerView.smoothScrollToPosition(0);
    }

    @Override
    public void onPrepared() {
        mVideoApi.play();
    }

    @Override
    public void onCompletion() {
        binding.setShowEnding(true);
    }
}
