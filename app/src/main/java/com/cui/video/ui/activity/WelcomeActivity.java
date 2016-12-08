package com.cui.video.ui.activity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.text.SpannableString;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;

import com.cui.video.AbstractBaseActivity;
import com.cui.video.R;
import com.cui.video.databinding.WelcomeActBinding;
import com.cui.video.helper.TransitionHelper;
import com.cui.video.presenter.iml.WelcomePresenter;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

/**
 * Created by cuiyang on 2016/12/8.
 */

public class WelcomeActivity extends AbstractBaseActivity<WelcomeActBinding, WelcomePresenter> implements View.OnClickListener {
    @Override
    protected void onCreated(Bundle savedInstanceState) {
        super.setFLAG_TRANSLUCENT_STATUS();
        super.setFLAG_TRANSLUCENT_NAVIGATION();
        ViewPropertyAnimator viewPropertyAnimator = binding.imgWel.animate();
        viewPropertyAnimator.setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                transitionTo(new Intent(WelcomeActivity.this, MainActivity.class));
                WelcomeActivity.super.onBackPressed();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        viewPropertyAnimator.scaleX(1.1f).scaleY(1.1f).setDuration(2000).setStartDelay(1000)
                .setInterpolator(new AccelerateDecelerateInterpolator()).start();

    }

    @Override
    protected WelcomePresenter initPresenter() {
        return new WelcomePresenter(this);
    }

    @Override
    protected int setDataBindingContentViewId() {
        return R.layout.welcome_act;
    }
}
