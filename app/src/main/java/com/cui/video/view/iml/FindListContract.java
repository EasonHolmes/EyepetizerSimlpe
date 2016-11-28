package com.cui.video.view.iml;

import com.cui.video.entity.FindListEntity;
import com.cui.video.view.BaseContract;
import com.google.gson.JsonObject;

public interface FindListContract {
    interface FindListView extends BaseContract.BaseView {
        void getFindListData(FindListEntity entity);

    }

    interface FindListPresenterIml extends BaseContract.BasePresenter {
        void getFindListData();

    }
}
