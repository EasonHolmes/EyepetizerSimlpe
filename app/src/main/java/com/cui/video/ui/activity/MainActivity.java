package com.cui.video.ui.activity;

import android.animation.Animator;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import com.cui.video.AbstractBaseActivity;
import com.cui.video.R;
import com.cui.video.adapter.MyViewPagerAdapter;
import com.cui.video.databinding.MainActBinding;
import com.cui.video.helper.TransitionHelper;
import com.cui.video.presenter.iml.MainActivityPresenter;
import com.cui.video.ui.fragment.FeaturedFrament;
import com.cui.video.ui.fragment.FindListFragment;
import com.cui.video.utils.listener.MainViewPagerChangeListener;
import com.cui.video.view.iml.MainContract;

import java.util.ArrayList;
import java.util.List;

import static com.cui.video.R.id.root_layout;
import static com.cui.video.R.layout.featured_fragment;
import static com.cui.video.R.layout.main_act;

public class MainActivity extends AbstractBaseActivity<MainActBinding, MainActivityPresenter>
        implements MainContract.MainActivityView, MainViewPagerChangeListener.PageSelectCallback {

    private Drawable[] default_drawable;
    private Drawable[] select_drawable;
    private TextView[] textViews;
    private ViewGroup rootView;

    @Override
    protected void onCreated(Bundle savedInstanceState) {
        MyViewPagerAdapter adapter = new MyViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FeaturedFrament());
        adapter.addFragment(new FindListFragment());
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.setOnPageChangeListener(new MainViewPagerChangeListener(binding.viewPager, this, getDefault_Bottom_drawable(), getSelect_Bottom_drawable(),
                getChange_Bottom_TextViews(), this));
        binding.viewPager.setOffscreenPageLimit(3);

    }

    /**
     * 默认tabbar图标
     *
     * @return
     */
    private Drawable[] getDefault_Bottom_drawable() {
        return default_drawable = new Drawable[]{
                getResources().getDrawable(R.drawable.ic_tab_strip_icon_feed),
                getResources().getDrawable(R.drawable.ic_tab_strip_icon_category)};
    }

    /**
     * 选中tabbar图标
     *
     * @return
     */
    private Drawable[] getSelect_Bottom_drawable() {
        return select_drawable = new Drawable[]{
                getResources().getDrawable(R.drawable.ic_tab_strip_icon_feed_selected),
                getResources().getDrawable(R.drawable.ic_tab_strip_icon_category_selected)};
    }

    /**
     * tabbarTextView
     *
     * @return
     */
    private TextView[] getChange_Bottom_TextViews() {
        return textViews = new TextView[]{binding.include.tabbar1Text,
                binding.include.tabbar2Text};
    }

    /**
     * 初始化P层类
     */
    @Override
    protected MainActivityPresenter initPresenter() {
        return new MainActivityPresenter(this);
    }

    /**
     * 载入layout布局
     */
    @Override
    protected int setDataBindingContentViewId() {
        return R.layout.main_act;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
