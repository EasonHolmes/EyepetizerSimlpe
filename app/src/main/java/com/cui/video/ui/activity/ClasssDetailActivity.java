package com.cui.video.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
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

public class ClasssDetailActivity extends AbstractBaseActivity<ClasssDetailActBinding, ClasssDetailPresenter>
        implements ClasssDetailContract.ClasssDetailView, FamiliarRecyclerView.OnItemClickListener, OnLoadMoreListener {
    private FeaturedAdapter adapter;
    private final int num = 10;//每页的条数
    private int start = 0;
    private int categoryId;
    private String toolbarTitle;
    private final String strategy_date = "date";

    @Override
    protected void onCreated(Bundle savedInstanceState) {
        categoryId = getIntent().getIntExtra(FindListFragment.CATEGORYID, -1);
        toolbarTitle = getIntent().getStringExtra(FindListFragment.CATEGORYNAME);
        super.initToolbar(toolbarTitle, true);

        mToolbar.setBackgroundColor(getResources().getColor(R.color.transtions_color));

        binding.mCollapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.black));
        binding.mCollapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.white));

        adapter = new FeaturedAdapter(this, null);
        binding.include.swipeTarget.initRefreshSwipe(binding.include.swipeToLoadLayout, null, this);
        binding.include.swipeTarget.setLayoutManager(new LinearLayoutManager(this));
        binding.include.swipeTarget.setAdapter(adapter);

        binding.include.swipeTarget.setOnItemClickListener(this);
        binding.include.swipeToLoadLayout.setRefreshEnabled(false);


        getActivityHelper().showSimpleLoadDialog();

        presenter.getClasssDeatilList(start, categoryId, strategy_date);
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
    public void onItemClick(FamiliarRecyclerView familiarRecyclerView, View view, int position) {

    }

    @Override
    public void getClasssDeatilList(ClasssDetailEntity entity) {
        if (entity.getItemList().size() > 0 && start == 0) {
            ImageLoaderDisplay.imageLoader(this, binding.imtToolbar, entity.getItemList().get(5).data.cover.feed);
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
    public void onLoadMore() {
        presenter.getClasssDeatilList(start, categoryId, strategy_date);
    }
}
