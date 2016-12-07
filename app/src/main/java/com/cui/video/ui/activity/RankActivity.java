package com.cui.video.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.transition.Transition;
import android.view.Gravity;
import android.view.View;

import com.cui.video.AbstractBaseActivity;
import com.cui.video.R;
import com.cui.video.adapter.MyPagerAdapter;
import com.cui.video.databinding.RankActBinding;
import com.cui.video.helper.TransitionHelper;
import com.cui.video.presenter.iml.RankPresenter;
import com.cui.video.ui.fragment.RankFragment;
import com.cui.video.view.iml.RankActivityContract;

/**
 * (1)setExitTransition() - 当A start B时，使A中的View退出场景的transition
 * <p>
 * (2)setEnterTransition() - 当A start B时，使B中的View进入场景的transition
 * <p>
 * (3)setReturnTransition() - 当B 返回 A时，使B中的View退出场景的transition
 * <p>
 * (4)setReenterTransition() - 当B 返回 A时，使A中的View进入场景的transition
 * <p>
 * (5)setAllowEnterTransitionOverlap() 当A start B时，是否覆盖A的界面不等待A的动画结束
 * <p>
 * (6)setAllowReturnTransitionOverlap  当B 返回 A时,是否覆盖B的界面不等待B的动画结束
 */
public class RankActivity extends AbstractBaseActivity<RankActBinding, RankPresenter>
        implements RankActivityContract.RankActivityView {

    private MyPagerAdapter pagerAdapter;

    @Override
    protected void onCreated(Bundle savedInstanceState) {
        getWindow().setEnterTransition(TransitionHelper.buildSlideTransition(Gravity.BOTTOM));
        getWindow().setReturnTransition(TransitionHelper.buildSlideTransition(Gravity.BOTTOM));
        getWindow().getEnterTransition().addListener(transitionListener);
        super.initToolbar(R.string.app_name, true);
        pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(RankFragment.createInstance("weekly"), "周排行");
        pagerAdapter.addFragment(RankFragment.createInstance("monthly"), "月排行");
        pagerAdapter.addFragment(RankFragment.createInstance("historical"), "总排行");
        binding.viewPager.setAdapter(pagerAdapter);
        binding.viewPager.setOffscreenPageLimit(3);
        binding.tabLayout.setTabMode(TabLayout.MODE_FIXED);//设置tab模式，当前为系统默认模
        //用来设置tab的，同时也要覆写  PagerAdapter 的 CharSequence getPageTitle(int position) 方法，要不然 Tab 没有 title
        binding.tabLayout.setupWithViewPager(binding.viewPager);


    }

    private Transition.TransitionListener transitionListener = new Transition.TransitionListener() {
        @Override
        public void onTransitionStart(Transition transition) {
        }

        @Override
        public void onTransitionEnd(Transition transition) {
            getWindow().getEnterTransition().removeListener(this);
            binding.viewPager.animate().alpha(1).setDuration(300).start();
        }

        @Override
        public void onTransitionCancel(Transition transition) {

        }

        @Override
        public void onTransitionPause(Transition transition) {

        }

        @Override
        public void onTransitionResume(Transition transition) {

        }
    };

    /**
     * 初始化P层类
     */
    @Override
    protected RankPresenter initPresenter() {
        return new RankPresenter(this);
    }

    /**
     * 载入layout布局
     */
    @Override
    protected int setDataBindingContentViewId() {
        return R.layout.rank_act;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void onDestroy() {
        if (pagerAdapter != null) {
            pagerAdapter.fragmentList.clear();
            pagerAdapter.fragmentList = null;
            pagerAdapter.fragmentTitleList.clear();
            pagerAdapter.fragmentTitleList = null;
        }
        pagerAdapter = null;
        super.onDestroy();
    }
}
