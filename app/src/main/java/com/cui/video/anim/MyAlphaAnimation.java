package com.cui.video.anim;

import android.content.Context;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;

/**
 * An animation used to slide {@link com.devbrackets.android.exomedia.ui.widget.VideoControls}
 * in and out from the bottom of the screen when changing visibilities.
 */
public class MyAlphaAnimation extends AnimationSet {
    private View animationView;
    private boolean toVisible;

    public MyAlphaAnimation(View view, boolean toVisible, long duration) {
        super(false);
        this.toVisible = toVisible;
        this.animationView = view;

        float startAlpha = toVisible ? 0 : 1;
        float endAlpha = toVisible ? 1 : 0;

        android.view.animation.AlphaAnimation alphaAnimation = new android.view.animation.AlphaAnimation(startAlpha, endAlpha);
        alphaAnimation.setDuration(duration);
        addAnimation(alphaAnimation);
        setAnimationListener(new Listener());
    }

    private int getHideShowDelta(View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        Display display = ((WindowManager) view.getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        display.getMetrics(displayMetrics);

        int screenHeight = displayMetrics.heightPixels;
        return screenHeight - view.getTop();
    }

    private class Listener implements AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {
            animationView.setVisibility(View.VISIBLE);
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            animationView.setVisibility(toVisible ? View.VISIBLE : View.GONE);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
            //Purposefully left blank
        }
    }
}
