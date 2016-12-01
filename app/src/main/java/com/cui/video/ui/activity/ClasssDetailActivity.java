package com.cui.video.ui.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.cui.video.AbstractBaseActivity;
import com.cui.video.R;
import com.cui.video.adapter.FeaturedAdapter;
import com.cui.video.databinding.ClasssDetailActBinding;
import com.cui.video.entity.ClasssDetailEntity;
import com.cui.video.presenter.iml.ClasssDetailPresenter;
import com.cui.video.ui.fragment.FindListFragment;
import com.cui.video.utils.img.ImageLoaderDisplay;
import com.cui.video.view.iml.ClasssDetailContract;

import cn.iwgang.familiarrecyclerview.FamiliarRecyclerView;

import static com.cui.video.ui.fragment.FeaturedFrament.FEATURED_DETAIL_ENTITY;

public class ClasssDetailActivity extends AbstractBaseActivity<ClasssDetailActBinding, ClasssDetailPresenter>
        implements ClasssDetailContract.ClasssDetailView, FamiliarRecyclerView.OnItemClickListener, OnLoadMoreListener, MenuItem.OnMenuItemClickListener {
    private FeaturedAdapter adapter;
    private final int num = 10;//每页的条数
    private int start = 0;
    private int categoryId;
    private String toolbarTitle;
    private String strategyStr = "date";

    @Override
    protected void onCreated(Bundle savedInstanceState) {
        categoryId = getIntent().getIntExtra(FindListFragment.CATEGORYID, -1);
        toolbarTitle = getIntent().getStringExtra(FindListFragment.CATEGORYNAME);
        super.initToolbar(toolbarTitle, true);

        //设置toolbar透明这样就能充满顶部看起来更好些
        mToolbar.setBackgroundColor(getResources().getColor(R.color.transtions_color));

        binding.mCollapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.black));
        binding.mCollapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.white));

        adapter = new FeaturedAdapter(this, null);
        binding.include.swipeTarget.initRefreshSwipe(binding.include.swipeToLoadLayout, null, this);
        binding.include.swipeTarget.setLayoutManager(new LinearLayoutManager(this));
        binding.include.swipeTarget.setAdapter(adapter);

        binding.include.swipeTarget.setOnItemClickListener(this);
        binding.include.swipeToLoadLayout.setRefreshEnabled(false);

        presenter.getClasssDeatilList(start, categoryId, strategyStr);
    }

    /**
     * 初始化P层类
     */
    @Override
    protected ClasssDetailPresenter initPresenter() {
        return new ClasssDetailPresenter(this);
    }

    /**
     * 载入layout布局
     */
    @Override
    protected int setDataBindingContentViewId() {
        return R.layout.classs_detail_act;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.classs_detail_menu, menu);
        menu.getItem(0).setOnMenuItemClickListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(FamiliarRecyclerView familiarRecyclerView, View view, int position) {
        Intent i = new Intent();
        i.setClass(this, VideoDeatilActivity.class);
        i.putExtra(FEATURED_DETAIL_ENTITY, adapter.getItem(position));
            ActivityOptionsCompat activityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(this, view, getResources().getString(R.string.featured_item_share_txt));
            startActivity(i, activityOptionsCompat.toBundle());
    }

    @Override
    public void getClasssDeatilList(ClasssDetailEntity entity) {
        if (entity.getItemList().size() > 0) {
            if (start == 0) {
                ImageLoaderDisplay.imageLoader(this, binding.imtToolbar, entity.getItemList().get(5).data.cover.feed);
                adapter.setNewData(entity.getItemList());
                binding.include.swipeTarget.smoothScrollToPosition(0);
                start += num;
            } else {
                adapter.addData(entity.getItemList());
                start += num;
            }
        }
        getActivityHelper().dismissSimpleLoadDialog();
        binding.include.swipeTarget.refresComplete();
    }

    @Override
    public void onLoadMore() {
        presenter.getClasssDeatilList(start, categoryId, strategyStr);
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        getActivityHelper().showSimpleLoadDialog();
        start = 0;
        if (strategyStr.equals("date")) {
            strategyStr = "shareCount";
            menuItem.setTitle("按时间排序");
            presenter.getClasssDeatilList(start, categoryId, strategyStr);
        } else {
            strategyStr = "date";
            presenter.getClasssDeatilList(start, categoryId, strategyStr);
            menuItem.setTitle("按分享排序");
        }
        return false;
    }
}
