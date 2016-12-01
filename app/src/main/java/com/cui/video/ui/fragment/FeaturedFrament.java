package com.cui.video.ui.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
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
    public static final String PX = "px";
    public static final String PY = "py";

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
        presenter.getFeaturedListMoreData(binding.include.swipeTarget.getPage(false), TextUtils.isEmpty(dateTime)?0:Long.decode(dateTime));
    }

    @Override
    public void onItemClick(FamiliarRecyclerView familiarRecyclerView, View view, int position) {
        Intent i = new Intent();
        i.setClass(activity, VideoDeatilActivity.class);
        i.putExtra(FEATURED_DETAIL_ENTITY, adapter.getItem(position));
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions activityOptionsCompat =
                    ActivityOptions.makeSceneTransitionAnimation(activity, view, getResources().getString(R.string.featured_item_share_txt));
            activity.startActivity(i, activityOptionsCompat.toBundle());
        } else {
            float x = view.getX();
            float y = view.getY();
            i.putExtra(PX, x);
            i.putExtra(PY, y);
            activity.startActivity(i);
            activity.overridePendingTransition(0, 0);
        }
    }

    @Override
    public void getFeaturedListMoreData(FeturedListEntity entity) {
        if (entity.issueList.size() > 0) {
            if (binding.include.swipeTarget.page == 1) {
                entity.issueList.get(1).itemList.remove(0);
                items.addAll(entity.issueList.get(0).itemList);
                items.addAll(entity.issueList.get(1).itemList);
                adapter.setNewData(items);
            } else {
                entity.issueList.get(0).itemList.remove(0);
                entity.issueList.get(1).itemList.remove(0);
                items.addAll(entity.issueList.get(0).itemList);
                items.addAll(entity.issueList.get(1).itemList);
                adapter.notifyDataSetChanged();
            }
        }
        setDateTime(entity);
        binding.include.swipeTarget.refresComplete();
    }

    private void setDateTime(FeturedListEntity daily) {
        String nextPageUrl = daily.nextPageUrl;
        dateTime = nextPageUrl.substring(nextPageUrl.indexOf("=") + 1,
                nextPageUrl.indexOf("&"));
    }

    @Override
    public void onRefresh() {
        presenter.getFeaturedListMoreData(binding.include.swipeTarget.getPage(true), 0);
    }
}
