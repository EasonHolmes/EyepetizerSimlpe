package com.cui.video.ui.activity.video;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.cui.video.AbstractBaseActivity;
import com.cui.video.R;
import com.cui.video.entity.PlayerVideoEntity;
import com.cui.video.manager.VideoApi;
import com.cui.video.presenter.AbstractBasePresenter;
import com.cui.video.presenter.iml.PlayerHelperPresenter;
import com.cui.video.widget.MyVideoControlsView;
import com.devbrackets.android.exomedia.listener.VideoControlsVisibilityListener;
import com.devbrackets.android.exomedia.ui.widget.EMVideoView;
import com.devbrackets.android.exomedia.ui.widget.VideoControls;

/**
 * Created by cuiyang on 2016/11/20.
 */

public class PlayerHelperActivity extends AppCompatActivity {
    protected EMVideoView emVideoView;
    protected PlayerVideoEntity playerEntity;
    private MyVideoControlsView videoControls;
    public static final String PLAYER_ENTITY = "player_entity";
    protected boolean pausedInOnStop = false;
    protected VideoApi mVideoApi;
    private FullScreenListener fullScreenListener;
    public MyVideoControlsView myVideoControlsView;
    protected TextView txt_title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        setContentView(R.layout.player_video_act);
        playerEntity = (PlayerVideoEntity) getIntent().getSerializableExtra(PLAYER_ENTITY);

        emVideoView = (EMVideoView) findViewById(R.id.video_play_activity_video_view);
        myVideoControlsView = new MyVideoControlsView(this);
        emVideoView.setControls(myVideoControlsView);
        txt_title = (TextView) myVideoControlsView.RootLayout.findViewById(R.id.exomedia_controls_title);

        mVideoApi = new VideoApi(emVideoView);

        videoControls = (MyVideoControlsView) emVideoView.getVideoControls();

        if (videoControls != null) {
            videoControls.setVisibilityListener(new ControlsVisibilityListener());
        }
        fullScreenListener = new FullScreenListener();


        goFullscreen();

        //是否移除上一首按钮
//        videoControls.setPreviousButtonRemoved(false);
        //是否移除下一首按钮
//        videoControls.setNextButtonRemoved(false);
//        videoControls.setButtonListener();

    }

    private void goFullscreen() {
        setUiFlags(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (emVideoView.isPlaying()) {
            pausedInOnStop = true;
            emVideoView.pause();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (pausedInOnStop) {
            emVideoView.start();
            pausedInOnStop = false;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        emVideoView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        emVideoView.release();
        emVideoView = null;
    }


    /**
     * Applies the correct flags to the windows decor view to enter
     * or exit fullscreen mode
     *
     * @param fullscreen True if entering fullscreen mode
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void setUiFlags(boolean fullscreen) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            View decorView = getWindow().getDecorView();
            if (decorView != null) {
                decorView.setSystemUiVisibility(fullscreen ? getFullscreenUiFlags() : View.SYSTEM_UI_FLAG_VISIBLE);
                decorView.setOnSystemUiVisibilityChangeListener(fullScreenListener);
            }
        }
    }

    /**
     * Determines the appropriate fullscreen flags based on the
     * systems API version.
     *
     * @return The appropriate decor view flags to enter fullscreen mode when supported
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private int getFullscreenUiFlags() {
        int flags = View.SYSTEM_UI_FLAG_LOW_PROFILE | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            flags |= View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }

        return flags;
    }

    /**
     * Listens to the system to determine when to show the default controls
     * for the {@link EMVideoView}
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private class FullScreenListener implements View.OnSystemUiVisibilityChangeListener {
        @Override
        public void onSystemUiVisibilityChange(int visibility) {
            if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                emVideoView.showControls();
            }
        }
    }

    /**
     * A Listener for the {@link VideoControls}
     * so that we can re-enter fullscreen mode when the controls are hidden.
     */
    private class ControlsVisibilityListener implements VideoControlsVisibilityListener {
        @Override
        public void onControlsShown() {
            // No additional functionality performed
        }

        @Override
        public void onControlsHidden() {
            goFullscreen();
        }
    }
}
