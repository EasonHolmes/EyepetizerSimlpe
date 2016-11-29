package com.cui.video.view.iml;

import com.cui.video.entity.ClasssDetailEntity;
import com.cui.video.view.BaseContract;

import retrofit2.http.Query;

public interface ClasssDetailContract {
    interface ClasssDetailView extends BaseContract.BaseView {

        void getClasssDeatilList(ClasssDetailEntity entity);
    }

    interface ClasssDetailPresenterIml extends BaseContract.BasePresenter {

        void getClasssDeatilList(int start, int categoryId,String strategy);

    }
}
