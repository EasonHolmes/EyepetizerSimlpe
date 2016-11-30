package com.cui.video.ui.fragment;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.cui.video.AbstractBaseFragment;
import com.cui.video.R;
import com.cui.video.adapter.FeaturedAdapter;
import com.cui.video.databinding.FrankFragmentBinding;
import com.cui.video.entity.FeturedListEntity;
import com.cui.video.entity.ItemList;
import com.cui.video.entity.RankListEntity;
import com.cui.video.presenter.iml.FeaturedFragmentPresenter;
import com.cui.video.presenter.iml.RankFragmentPresenter;
import com.cui.video.ui.activity.FeaturedDeatilActivity;
import com.cui.video.ui.activity.SearchFeaturedActivity;
import com.cui.video.view.iml.FeaturedFragmentContract;
import com.cui.video.view.iml.RankFragmnetContract;

import java.util.ArrayList;
import java.util.List;

import cn.iwgang.familiarrecyclerview.FamiliarRecyclerView;

import static com.cui.video.ui.fragment.FeaturedFrament.FEATURED_DETAIL_ENTITY;
import static com.cui.video.ui.fragment.FeaturedFrament.PX;
import static com.cui.video.ui.fragment.FeaturedFrament.PY;

/**
 * Created by cuiyang on 2016/11/30.
 */

public class RankFragment extends AbstractBaseFragment<FrankFragmentBinding, RankFragmentPresenter>
        implements RankFragmnetContract.RankFragmentView, FamiliarRecyclerView.OnItemClickListener {
    private FeaturedAdapter adapter;
    private static final String ITEM_STRATEGY = "strategy";
    private String strategy;

    public static RankFragment createInstance(String strategy) {
        RankFragment rankFragment = new RankFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ITEM_STRATEGY, strategy);
        rankFragment.setArguments(bundle);
        return rankFragment;
    }

    @Override
    protected void onFragmentViewCreated(View view, Bundle savedInstanceState) {
        this.strategy = getArguments().getString(ITEM_STRATEGY);
        adapter = new FeaturedAdapter(mContext, null);
        binding.swipeTarget.setLayoutManager(new LinearLayoutManager(mContext));
        binding.swipeTarget.setOnItemClickListener(this);
        binding.swipeTarget.setAdapter(adapter);
        presenter.getRankListData(strategy);
    }

    /**
     * 初始化P层类
     */
    @Override
    protected RankFragmentPresenter initPresenter() {
        return new RankFragmentPresenter(this);
    }

    /**
     * 载入layout布局
     */
    @Override
    protected int setDataBindingContentViewId() {
        return R.layout.frank_fragment;
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
    public void onItemClick(FamiliarRecyclerView familiarRecyclerView, View view, int position) {
        Intent i = new Intent();
        i.setClass(activity, FeaturedDeatilActivity.class);
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
    public void getRankListData(RankListEntity entity) {
        if (entity.getItemList().size() > 0) {
            adapter.setNewData(entity.getItemList());
        }
    }
}