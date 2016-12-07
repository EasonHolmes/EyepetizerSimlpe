package com.cui.video.ui.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.cui.video.AbstractBaseFragment;
import com.cui.video.R;
import com.cui.video.adapter.FeaturedAdapter;
import com.cui.video.databinding.FrankFragmentBinding;
import com.cui.video.entity.PlayerVideoEntity;
import com.cui.video.entity.RankListEntity;
import com.cui.video.helper.TransitionHelper;
import com.cui.video.presenter.iml.RankFragmentPresenter;
import com.cui.video.ui.activity.VideoDeatilActivity;
import com.cui.video.ui.activity.SearchFeaturedActivity;
import com.cui.video.view.iml.RankFragmnetContract;

import cn.iwgang.familiarrecyclerview.FamiliarRecyclerView;

import static com.cui.video.ui.fragment.FeaturedFrament.FEATURED_DETAIL_ENTITY;

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
        i.setClass(activity, VideoDeatilActivity.class);
        PlayerVideoEntity playerVideoEntity = new PlayerVideoEntity();
        playerVideoEntity.setList(adapter.getData().subList(position, adapter.getData().size() - 1));
        i.putExtra(FEATURED_DETAIL_ENTITY, playerVideoEntity);
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity, TransitionHelper.createSafeTransitionParticipants(activity, true, Pair.create(view, getResources().getString(R.string.featured_item_share_txt))));
        activity.startActivity(i, activityOptionsCompat.toBundle());
    }

    @Override
    public void getRankListData(RankListEntity entity) {
        if (entity.getItemList().size() > 0) {
            adapter.setNewData(entity.getItemList());
        }
    }
}