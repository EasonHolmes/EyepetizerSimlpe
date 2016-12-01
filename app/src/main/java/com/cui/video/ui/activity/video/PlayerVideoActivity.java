package com.cui.video.ui.activity.video;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cui.video.R;
import com.cui.video.manager.VideoApi;
import com.cui.video.widget.CVideoControlsView;
import com.devbrackets.android.exomedia.listener.OnPreparedListener;

import static com.cui.video.R.id.txt_title;

public class PlayerVideoActivity extends PlayerHelperActivity implements OnPreparedListener {

    private TextView txt_title;
    private CVideoControlsView cVideoControlsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        cVideoControlsView = new CVideoControlsView(this);
        emVideoView.setControls(cVideoControlsView);
        cVideoControlsView.setTitle(playerEntity.getTitle());

        emVideoView.setOnPreparedListener(this);
        emVideoView.setVideoURI(Uri.parse(playerEntity.getUrl()));
    }

    @Override
    public void onPrepared() {
        mVideoApi.play();
    }
}
