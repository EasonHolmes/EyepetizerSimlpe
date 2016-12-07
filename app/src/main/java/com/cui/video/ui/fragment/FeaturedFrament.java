package com.cui.video.ui.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.cui.video.AbstractBaseFragment;
import com.cui.video.R;
import com.cui.video.adapter.FeaturedAdapter;
import com.cui.video.databinding.FeaturedFragmentBinding;
import com.cui.video.entity.FeturedListEntity;
import com.cui.video.entity.ItemList;
import com.cui.video.entity.PlayerVideoEntity;
import com.cui.video.helper.TransitionHelper;
import com.cui.video.presenter.iml.FeaturedFragmentPresenter;
import com.cui.video.ui.activity.VideoDeatilActivity;
import com.cui.video.ui.activity.SearchFeaturedActivity;
import com.cui.video.view.iml.FeaturedFragmentContract;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import cn.iwgang.familiarrecyclerview.FamiliarRecyclerView;

import static com.bumptech.glide.Glide.with;

public class FeaturedFrament extends AbstractBaseFragment<FeaturedFragmentBinding, FeaturedFragmentPresenter>
        implements FeaturedFragmentContract.FeaturedFragmentView, OnLoadMoreListener, FamiliarRecyclerView.OnItemClickListener, OnRefreshListener {
    private FeaturedAdapter adapter;
    private String dateTime = "";
    private List<ItemList> items = new ArrayList<>();
    public static final String FEATURED_DETAIL_ENTITY = "detail_entity";

    @Override
    protected void onFragmentViewCreated(View view, Bundle savedInstanceState) {
        adapter = new FeaturedAdapter(mContext, binding.include.swipeToLoadLayout);
        binding.include.swipeTarget.initRefreshSwipe(binding.include.swipeToLoadLayout, this, this);
        binding.include.swipeTarget.setLayoutManager(new LinearLayoutManager(mContext));
        binding.include.swipeTarget.setOnItemClickListener(this);
        binding.include.swipeTarget.setAdapter(adapter);
        binding.include.swipeToLoadLayout.setRefreshing(true);
        binding.imgSearchFeatured.setOnClickListener(this);
    }

    /**
     * 初始化P层类
     */
    @Override
    protected FeaturedFragmentPresenter initPresenter() {
        return new FeaturedFragmentPresenter(this);
    }

    /**
     * 载入layout布局
     */
    @Override
    protected int setDataBindingContentViewId() {
        return R.layout.featured_fragment;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_search_featured:
                startActivity(new Intent(activity, SearchFeaturedActivity.class));
                activity.overridePendingTransition(0, 0);
                break;
        }
    }

    @Override
    public void onLoadMore() {
        presenter.getFeaturedListMoreData(binding.include.swipeTarget.getPage(false), TextUtils.isEmpty(dateTime) ? 0 : Long.decode(dateTime));
    }

    @Override
    public void onItemClick(FamiliarRecyclerView familiarRecyclerView, View view, int position) {
        Intent i = new Intent();
        i.setClass(activity, VideoDeatilActivity.class);
        PlayerVideoEntity playerVideoEntity = new PlayerVideoEntity();
        playerVideoEntity.setList(adapter.getData().subList(position, adapter.getData().size() - 1));
        i.putExtra(FEATURED_DETAIL_ENTITY, playerVideoEntity);

        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity, TransitionHelper.createSafeTransitionParticipants(activity, true, Pair.create(view, getResources().getString(R.string.featured_item_share_txt))));
        activity.startActivity(i, activityOptionsCompat.toBundle());
    }

    @Override
    public void getFeaturedListMoreData(List<ItemList> entity) {
        if (entity.size() > 0) {
            if (binding.include.swipeTarget.page == 1) {
                items.clear();
            }
            items.addAll(entity);
            adapter.setNewData(items);
        }
        binding.include.swipeTarget.refresComplete();
    }

    public void setDateTime(FeturedListEntity daily) {
        String nextPageUrl = daily.nextPageUrl;
        dateTime = nextPageUrl.substring(nextPageUrl.indexOf("=") + 1,
                nextPageUrl.indexOf("&"));
    }

    @Override
    public void onRefresh() {
        presenter.getFeaturedListMoreData(binding.include.swipeTarget.getPage(true), 0);
    }
}
