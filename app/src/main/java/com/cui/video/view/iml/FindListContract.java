package com.cui.video.view.iml;

import com.cui.video.entity.ItemList;
import com.cui.video.view.BaseContract;

import java.util.List;

public interface FindListContract {
    interface FindListView extends BaseContract.BaseView {
        void getFindListData(List<ItemList> entity);

    }

    interface FindListPresenterIml extends BaseContract.BasePresenter {
        void getFindListData();

    }
}
