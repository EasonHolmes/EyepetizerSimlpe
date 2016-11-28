package com.cui.video.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.cui.video.AbstractBaseFragment;
import com.cui.video.R;
import com.cui.video.adapter.FindListAdapter;
import com.cui.video.databinding.FindFragmentBinding;
import com.cui.video.entity.FindListEntity;
import com.cui.video.presenter.iml.FindListPresenter;
import com.cui.video.utils.LogUtils;
import com.cui.video.view.iml.FindListContract;
import com.cui.video.widget.SpaceGridItemDecoration;

import cn.iwgang.familiarrecyclerview.FamiliarRecyclerView;

public class FindListFragment extends AbstractBaseFragment<FindFragmentBinding, FindListPresenter>
        implements FindListContract.FindListView, FamiliarRecyclerView.OnItemClickListener {
    private FindListAdapter adapter;

    @Override
    protected void onFragmentViewCreated(View view, Bundle savedInstanceState) {
        adapter = new FindListAdapter(mContext, null);
        binding.swipeTarget.setLayoutManager(new GridLayoutManager(mContext, 2));
        binding.swipeTarget.addItemDecoration(new SpaceGridItemDecoration(10));
        binding.swipeTarget.setOnItemClickListener(this);
        binding.swipeTarget.setAdapter(adapter);
        presenter.getFindListData();

    }

    /**
     * 初始化P层类
     */
    @Override
    protected FindListPresenter initPresenter() {
        return new FindListPresenter(this);
    }

    /**
     * 载入layout布局
     */
    @Override
    protected int setDataBindingContentViewId() {
        return R.layout.find_fragment;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemClick(FamiliarRecyclerView familiarRecyclerView, View view, int position) {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser && (adapter == null || adapter.getData() == null || adapter.getData().size() == 0)) {
            //do something
        }
    }

    @Override
    public void getFindListData(FindListEntity entity) {
        if (entity != null) {
            adapter.setNewData(entity.getItemList());
        }
    }
}