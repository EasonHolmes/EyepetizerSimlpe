package com.cui.video.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.View;

import com.cui.video.AbstractBaseActivity;
import com.cui.video.R;
import com.cui.video.adapter.MyPagerAdapter;
import com.cui.video.databinding.RankActBinding;
import com.cui.video.presenter.iml.RankPresenter;
import com.cui.video.ui.fragment.RankFragment;
import com.cui.video.view.iml.RankActivityContract;

public class RankActivity extends AbstractBaseActivity<RankActBinding, RankPresenter>
        implements RankActivityContract.RankActivityView {

    private MyPagerAdapter pagerAdapter;

    @Override
    protected void onCreated(Bundle savedInstanceState) {
        super.initToolbar(R.string.app_name,true);
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
