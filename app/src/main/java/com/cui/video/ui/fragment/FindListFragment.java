package com.cui.video.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.GridLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.cui.video.AbstractBaseFragment;
import com.cui.video.R;
import com.cui.video.adapter.FindListAdapter;
import com.cui.video.databinding.FindFragmentBinding;
import com.cui.video.entity.ItemList;
import com.cui.video.helper.TransitionHelper;
import com.cui.video.presenter.iml.FindListPresenter;
import com.cui.video.ui.activity.ClasssDetailActivity;
import com.cui.video.ui.activity.RankActivity;
import com.cui.video.view.iml.FindListContract;
import com.cui.video.widget.SpaceGridItemDecoration;

import java.util.List;

import cn.iwgang.familiarrecyclerview.FamiliarRecyclerView;

public class FindListFragment extends AbstractBaseFragment<FindFragmentBinding, FindListPresenter>
        implements FindListContract.FindListView, FamiliarRecyclerView.OnItemClickListener {
    private FindListAdapter adapter;
    public static final String CATEGORYID = "detail_categoryId";
    public static final String CATEGORYNAME = "detail_title";
    private ImageView imageView_header;

    @Override
    protected void onFragmentViewCreated(View view, Bundle savedInstanceState) {
        adapter = new FindListAdapter(mContext, null);
        binding.swipeTarget.setLayoutManager(new GridLayoutManager(mContext, 2));
        binding.swipeTarget.addItemDecoration(new SpaceGridItemDecoration(10));
        binding.swipeTarget.setOnItemClickListener(this);
        binding.swipeTarget.setAdapter(adapter);
        imageView_header = new ImageView(activity);
        ImageView imageView1 = new ImageView(activity);
        imageView_header.setImageResource(R.drawable.hot_img);
        imageView_header.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView_header.setId(R.id.img_item);
        //因为是2列的所以加头部只加一个的话后面做分割线的计算就会乱
        binding.swipeTarget.addHeaderView(imageView_header);
        binding.swipeTarget.addHeaderView(imageView1);

        setViewsClickListener(imageView_header);

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
        switch (view.getId()) {
            case R.id.img_item:
                activity.transitionTo(new Intent(activity, RankActivity.class));
                break;
        }
    }

    @Override
    public void onItemClick(FamiliarRecyclerView familiarRecyclerView, View view, int position) {
        Intent intent = new Intent(activity, ClasssDetailActivity.class);
        intent.putExtra(CATEGORYID, adapter.getItem(position).data.id);
        intent.putExtra(CATEGORYNAME, adapter.getItem(position).data.title);
        activity.transitionTo(intent);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser && (adapter == null || adapter.getData() == null || adapter.getData().size() == 0)) {
            //do something
        }
    }

    @Override
    public void getFindListData(List<ItemList> list_data) {
        if (list_data != null && list_data.size() > 0) {
            adapter.setNewData(list_data);
        }
    }
}