package com.cui.video.view.iml;

import com.cui.video.entity.RankListEntity;
import com.cui.video.view.BaseContract;

public interface RankFragmnetContract {
    interface RankFragmentView extends BaseContract.BaseView {
        void getRankListData(RankListEntity entity);
    }

    interface RankFragmentPresenterIml extends BaseContract.BasePresenter {

        void getRankListData(String strategy);
    }
}
