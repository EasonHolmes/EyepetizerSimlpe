package com.cui.video.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;

import com.cui.video.R;
import com.cui.video.anim.MyAlphaAnimation;
import com.devbrackets.android.exomedia.ui.animation.TopViewHideShowAnimation;
import com.devbrackets.android.exomedia.ui.widget.FitsSystemWindowRelativeLayout;
import com.devbrackets.android.exomedia.ui.widget.VideoControls;
import com.devbrackets.android.exomedia.util.TimeFormatUtil;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by cuiyang on 2016/11/20.
 */

public class CVideoControlsView extends VideoControls {
    protected SeekBar seekBar;
    //    protected LinearLayout extraViewsContainer;
    protected FitsSystemWindowRelativeLayout container_root_layout;
    public View RootLayout;
    protected boolean userInteracting = false;

    public CVideoControlsView(Context context) {
        super(context);
    }

    public CVideoControlsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CVideoControlsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CVideoControlsView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected int getLayoutResource() {
        //这个布局中的id不能改只能添 因为父类都是根据这个里面的设置的一些监听
//        exomedia_controls_text_container title有container布局
        RootLayout = LayoutInflater.from(super.getContext()).inflate(R.layout.my_exomedia_default_controls_mobile, null);
        return R.layout.my_exomedia_default_controls_mobile;
    }

    @Override
    public void setPosition(@IntRange(from = 0) long position) {
        currentTime.setText(TimeFormatUtil.formatMs(position));
        seekBar.setProgress((int) position);
    }

    @Override
    public void setDuration(@IntRange(from = 0) long duration) {
        if (duration != seekBar.getMax()) {
            endTime.setText(TimeFormatUtil.formatMs(duration));
            seekBar.setMax((int) duration);
        }
    }

    @Override
    public void updateProgress(@IntRange(from = 0) long position, @IntRange(from = 0) long duration, @IntRange(from = 0, to = 100) int bufferPercent) {
        if (!userInteracting) {
            seekBar.setSecondaryProgress((int) (seekBar.getMax() * ((float) bufferPercent / 100)));
            seekBar.setProgress((int) position);
            currentTime.setText(TimeFormatUtil.formatMs(position));
        }
    }

    @Override
    protected void retrieveViews() {
        super.retrieveViews();
        seekBar = (SeekBar) findViewById(R.id.exomedia_controls_video_seek);
//        extraViewsContainer = (LinearLayout) findViewById(R.id.exomedia_controls_extra_container);
        container_root_layout = (FitsSystemWindowRelativeLayout) findViewById(R.id.container_root_layout);
    }

    @Override
    protected void registerListeners() {
        super.registerListeners();
        seekBar.setOnSeekBarChangeListener(new CVideoControlsView.SeekBarChanged());
    }

    @Override
    public void addExtraView(@NonNull View view) {
        container_root_layout.addView(view);
    }

    @Override
    public void removeExtraView(@NonNull View view) {
        container_root_layout.removeView(view);
    }

    @NonNull
    @Override
    public List<View> getExtraViews() {
        int childCount = container_root_layout.getChildCount();
        if (childCount <= 0) {
            return super.getExtraViews();
        }

        //Retrieves the layouts children
        List<View> children = new LinkedList<>();
        for (int i = 0; i < childCount; i++) {
            children.add(container_root_layout.getChildAt(i));
        }

        return children;
    }

    @Override
    public void hideDelayed(long delay) {
        hideDelay = delay;

        if (delay < 0 || !canViewHide || isLoading) {
            return;
        }

        //If the user is interacting with controls we don't want to start the delayed hide yet
        if (!userInteracting) {
            visibilityHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    animateVisibility(false);
                }
            }, delay);
        }
    }

    @Override
    protected void animateVisibility(boolean toVisible) {
        if (isVisible == toVisible) {
            return;
        }

        if (!hideEmptyTextContainer || !isTextContainerEmpty()) {
            textContainer.startAnimation(new TopViewHideShowAnimation(textContainer, toVisible, CONTROL_VISIBILITY_ANIMATION_LENGTH));
        }

        if (!isLoading) {
            //从下向上的动画
//            controlsContainer.startAnimation(new BottomViewHideShowAnimation(controlsContainer, toVisible, CONTROL_VISIBILITY_ANIMATION_LENGTH));//从下到上的动画
            container_root_layout.startAnimation(new MyAlphaAnimation(container_root_layout, toVisible, CONTROL_VISIBILITY_ANIMATION_LENGTH));
        }

        isVisible = toVisible;
        onVisibilityChanged();
    }

    @Override
    protected void updateTextContainerVisibility() {
        if (!isVisible) {
            return;
        }

        boolean emptyText = isTextContainerEmpty();
        if (hideEmptyTextContainer && emptyText && textContainer.getVisibility() == VISIBLE) {
            textContainer.clearAnimation();
            textContainer.startAnimation(new TopViewHideShowAnimation(textContainer, false, CONTROL_VISIBILITY_ANIMATION_LENGTH));
        } else if ((!hideEmptyTextContainer || !emptyText) && textContainer.getVisibility() != VISIBLE) {
            textContainer.clearAnimation();
            textContainer.startAnimation(new TopViewHideShowAnimation(textContainer, true, CONTROL_VISIBILITY_ANIMATION_LENGTH));
        }
    }

    @Override
    public void showLoading(boolean initialLoad) {
        if (isLoading) {
            return;
        }

        isLoading = true;
        controlsContainer.setVisibility(View.GONE);
        loadingProgress.setVisibility(View.VISIBLE);

        show();
    }

    @Override
    public void finishLoading() {
        if (!isLoading) {
            return;
        }

        isLoading = false;
        controlsContainer.setVisibility(View.VISIBLE);
        loadingProgress.setVisibility(View.GONE);

        updatePlaybackState(videoView != null && videoView.isPlaying());
    }

    /**
     * Listens to the seek bar change events and correctly handles the changes
     */
    protected class SeekBarChanged implements SeekBar.OnSeekBarChangeListener {
        private int seekToTime;

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (!fromUser) {
                return;
            }

            seekToTime = progress;
            if (currentTime != null) {
                currentTime.setText(TimeFormatUtil.formatMs(seekToTime));
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            userInteracting = true;
            if (seekListener == null || !seekListener.onSeekStarted()) {
                internalListener.onSeekStarted();
            }
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            userInteracting = false;
            if (seekListener == null || !seekListener.onSeekEnded(seekToTime)) {
                internalListener.onSeekEnded(seekToTime);
            }
        }
    }
}
