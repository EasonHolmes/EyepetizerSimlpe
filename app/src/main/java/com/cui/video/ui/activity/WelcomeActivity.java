package com.cui.video.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.bumptech.glide.Glide;
import com.cui.video.AbstractBaseActivity;
import com.cui.video.AbstractBaseAdapter;
import com.cui.video.BuildConfig;
import com.cui.video.R;
import com.cui.video.adapter.BindingViewHolder;
import com.cui.video.databinding.ItemFeaturedBinding;
import com.cui.video.databinding.WelcomeActBinding;
import com.cui.video.helper.TransitionHelper;
import com.cui.video.presenter.iml.WelcomePresenter;
import com.cui.video.utils.img.ImageLoaderDisplay;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import cn.iwgang.familiarrecyclerview.FamiliarRecyclerView;

/**
 * Created by cuiyang on 2016/12/8.
 */

public class WelcomeActivity extends AbstractBaseActivity<WelcomeActBinding, WelcomePresenter> implements View.OnClickListener {
    @Override
    protected void onCreated(Bundle savedInstanceState) {
        super.setFLAG_TRANSLUCENT_STATUS();
        super.setFLAG_TRANSLUCENT_NAVIGATION();
        Glide.with(this)
                .load(R.drawable.landing_background)
                .into(binding.imgWel);
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
        viewPropertyAnimator.scaleX(1.1f).scaleY(1.1f).setDuration(2000).setStartDelay(800)
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
