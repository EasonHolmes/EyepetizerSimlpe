package com.cui.video.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.cui.video.AbstractBaseActivity;
import com.cui.video.R;
import com.cui.video.adapter.FeaturedAdapter;
import com.cui.video.databinding.SearchFeaturedActBinding;
import com.cui.video.entity.SearchFeaturedListEntity;
import com.cui.video.entity.fetured.FeturedListEntity;
import com.cui.video.presenter.iml.SearchFeaturedPresenter;
import com.cui.video.ui.fragment.FeaturedFrament;
import com.cui.video.view.iml.SearchFeaturedContract;

import cn.iwgang.familiarrecyclerview.FamiliarRecyclerView;

import static com.cui.video.ui.fragment.FeaturedFrament.FEATURED_DETAIL_ENTITY;
import static com.cui.video.ui.fragment.FeaturedFrament.PY;

public class SearchFeaturedActivity extends AbstractBaseActivity<SearchFeaturedActBinding, SearchFeaturedPresenter>
        implements SearchFeaturedContract.SearchFeaturedView, FamiliarRecyclerView.OnItemClickListener, SearchView.OnQueryTextListener, OnLoadMoreListener {

    private final int scaleX_duration = 400;
    private FeaturedAdapter adapter;
    private int start;
    private int num = 10;
    private String queryStr;

    @Override
    protected void onResume() {
        super.onResume();
        binding.searchView.clearFocus();
    }

    @Override
    protected void onCreated(Bundle savedInstanceState) {
        binding.include.swipeToLoadLayout.setVisibility(View.GONE);
        binding.backgroundView.animate().scaleY(getDeviceHeight(this))
                .setInterpolator(new AccelerateInterpolator())
                .setDuration(scaleX_duration)
                .start();

        adapter = new FeaturedAdapter(this, null);
        binding.include.swipeTarget.initRefreshSwipe(binding.include.swipeToLoadLayout, null, this);
        binding.include.swipeTarget.setLayoutManager(new LinearLayoutManager(this));
        binding.include.swipeTarget.setAdapter(adapter);
        binding.include.swipeTarget.setOnItemClickListener(this);
        binding.include.swipeToLoadLayout.setRefreshEnabled(false);

        binding.searchView.setOnQueryTextListener(this);
        binding.searchView.onActionViewExpanded();

        setViewsClickListener(binding.txtCancle);
    }

    @SuppressWarnings("deprecation")
    private int getDeviceHeight(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        return manager.getDefaultDisplay().getHeight();
    }

    /**
     * 初始化P层类
     */
    @Override
    protected SearchFeaturedPresenter initPresenter() {
        return new SearchFeaturedPresenter(this);
    }

    /**
     * 载入layout布局
     */
    @Override
    protected int setDataBindingContentViewId() {
        return R.layout.search_featured_act;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_cancle:
                onBackPressed();
                break;
        }
    }

    @Override
    public void getSearchFeaturedList(SearchFeaturedListEntity entity) {
        if (entity.getItemList().size() > 0 && start == num) {
            adapter.setNewData(entity.getItemList());
            start += num;
        } else {
            adapter.addData(entity.getItemList());
            start += num;
        }
        getActivityHelper().dismissSimpleLoadDialog();
        binding.include.swipeTarget.refresComplete();
    }

    @Override
    public void refreshError(String error) {
        super.refreshError(error);
    }

    @Override
    public void onBackPressed() {
        binding.include.swipeToLoadLayout.setVisibility(View.GONE);
        binding.backgroundView.animate().scaleY(0)
                .setInterpolator(new DecelerateInterpolator())
                .setDuration(scaleX_duration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        binding.rootLayout.setVisibility(View.GONE);
                        SearchFeaturedActivity.super.onBackPressed();
                    }
                })
                .start();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (binding.include.swipeToLoadLayout.getVisibility() == View.GONE)
            binding.include.swipeToLoadLayout.setVisibility(View.VISIBLE);
        this.queryStr = query;
        start = 10;
        binding.include.swipeTarget.page = 1;
        getActivityHelper().showSimpleLoadDialog();
        presenter.getSearchFeaturedList(query, start);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onItemClick(FamiliarRecyclerView familiarRecyclerView, View view, int position) {
        Intent i = new Intent();
        i.setClass(this, FeaturedDeatilActivity.class);
        i.putExtra(FEATURED_DETAIL_ENTITY, adapter.getItem(position));
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions activityOptionsCompat =
                    ActivityOptions.makeSceneTransitionAnimation(this, view, getResources().getString(R.string.featured_item_share_txt));
            startActivity(i, activityOptionsCompat.toBundle());
        } else {
            float x = view.getX();
            float y = view.getY();
            i.putExtra(FeaturedFrament.PX, x);
            i.putExtra(FeaturedFrament.PY, y);
            startActivity(i);
            overridePendingTransition(0, 0);
        }
    }

    @Override
    public void onLoadMore() {
        presenter.getSearchFeaturedList(queryStr, start);
    }
}
