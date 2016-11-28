package com.cui.video.presenter.iml;

import android.app.Activity;

import com.cui.video.http.ApiClient;
import com.cui.video.http.RxSchedulersHelper;
import com.cui.video.presenter.AbstractBasePresenter;
import com.cui.video.ui.activity.SearchFeaturedActivity;
import com.cui.video.view.iml.FeaturedFragmentContract;
import com.cui.video.view.iml.SearchFeaturedContract;
import com.trello.rxlifecycle.android.ActivityEvent;

public class SearchFeaturedPresenter extends AbstractBasePresenter<SearchFeaturedActivity>
        implements SearchFeaturedContract.SearchFeaturedPresenterIml {


    public SearchFeaturedPresenter(SearchFeaturedActivity mView) {
        super(mView);
    }


    @Override
    public void getSearchFeaturedList(String queryStr,int start) {
        ApiClient.getApiService().getSearchFeatured(queryStr,start)
                .compose(RxSchedulersHelper.io_main())
                .compose(mView.bindToLifecycle())
                .subscribe(searfelistentity -> mView.getSearchFeaturedList(searfelistentity),
                        throwable -> mView.refreshError(throwable.getMessage()));

    }
}
