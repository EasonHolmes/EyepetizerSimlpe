package com.cui.video.ui.activity.video;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.devbrackets.android.exomedia.listener.OnPreparedListener;

public class PlayerVideoActivity extends PlayerHelperActivity implements OnPreparedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        emVideoView.setOnPreparedListener(this);
        emVideoView.setVideoURI(Uri.parse(playerEntity.getUrl()));
        super.txt_title.setText(super.playerEntity.getTitle());
        txt_title.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPrepared() {
        mVideoApi.play();
    }
}
