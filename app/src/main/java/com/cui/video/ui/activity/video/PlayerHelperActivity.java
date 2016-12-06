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
import com.cui.video.databinding.PlayerVideoActBinding;
import com.cui.video.entity.PlayerVideoEntity;
import com.cui.video.manager.VideoApi;
import com.cui.video.presenter.iml.PlayerPresenter;
import com.cui.video.widget.CVideoControlsView;
import com.devbrackets.android.exomedia.listener.VideoControlsVisibilityListener;
import com.devbrackets.android.exomedia.ui.widget.EMVideoView;
import com.devbrackets.android.exomedia.ui.widget.VideoControls;

import static com.cui.video.R.id.txt_title;

/**
 * 该类只负责初始化播放类的工作
 */

public class PlayerHelperActivity extends AbstractBaseActivity<PlayerVideoActBinding, PlayerPresenter> implements View.OnClickListener {
    protected EMVideoView emVideoView;
    protected PlayerVideoEntity playerEntity;
    public static final String PLAYER_ENTITY = "player_entity";
    protected boolean pausedInOnStop = false;
    protected VideoApi mVideoApi;
    private FullScreenListener fullScreenListener;

    protected CVideoControlsView cVideoControlsView;

    @Override
    protected void onCreated(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        //默认先隐藏最后的列表封面
        binding.setShowEnding(false);
        //固定播对象
        playerEntity = getIntent().getParcelableExtra(PLAYER_ENTITY);
        //播放ui
        emVideoView = (EMVideoView) findViewById(R.id.video_play_activity_video_view);
        //播放各种回调和接口
        mVideoApi = new VideoApi(emVideoView);
        //自定义播放控制界面
        cVideoControlsView = new CVideoControlsView(this);
        cVideoControlsView.getTitleView().setOnClickListener(this);
        //设置播放控制界面
        emVideoView.setControls(cVideoControlsView);
        //设置播放控制界面显示隐藏监听
        if (emVideoView.getVideoControls() != null) {
            emVideoView.getVideoControls().setVisibilityListener(new ControlsVisibilityListener());
        }
        fullScreenListener = new FullScreenListener();
        goFullscreen();
        //是否移除上一首按钮
//        videoControls.setPreviousButtonRemoved(false);
        //是否移除下一首按钮
//        videoControls.setNextButtonRemoved(false);
//        videoControls.setButtonListener();

    }

    @Override
    protected PlayerPresenter initPresenter() {
        return new PlayerPresenter(this);
    }

    @Override
    protected int setDataBindingContentViewId() {
        return R.layout.player_video_act;
    }

    protected void goFullscreen() {
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
        cVideoControlsView = null;
    }


    /**
     * Applies the correct flags to the windows decor view to enter
     * or exit fullscreen mode
     *
     * @param fullscreen True if entering fullscreen mode
     */
    private void setUiFlags(boolean fullscreen) {
        View decorView = getWindow().getDecorView();
        if (decorView != null) {
            decorView.setSystemUiVisibility(fullscreen ? getFullscreenUiFlags() : View.SYSTEM_UI_FLAG_VISIBLE);
            decorView.setOnSystemUiVisibilityChangeListener(fullScreenListener);
        }

    }

    /**
     * Determines the appropriate fullscreen flags based on the
     * systems API version.
     *
     * @return The appropriate decor view flags to enter fullscreen mode when supported
     */
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.exomedia_controls_title:
            case R.id.img_close:
                finish();
                break;
        }
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
    protected class ControlsVisibilityListener implements VideoControlsVisibilityListener {
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
