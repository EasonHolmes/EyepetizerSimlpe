package com.cui.video.view.iml;

import com.cui.video.entity.SearchFeaturedListEntity;
import com.cui.video.ui.fragment.FeaturedFrament;
import com.cui.video.view.BaseContract;

public interface SearchFeaturedContract {
    interface SearchFeaturedView extends BaseContract.BaseView {
        void getSearchFeaturedList(SearchFeaturedListEntity entity);
    }

    interface SearchFeaturedPresenterIml extends BaseContract.BasePresenter {

        void getSearchFeaturedList(String queryStr,int start);
    }
}
