package com.cui.video.presenter.iml;

import com.cui.video.http.ApiClient;
import com.cui.video.http.RxSchedulersHelper;
import com.cui.video.presenter.AbstractBasePresenter;
import com.cui.video.ui.fragment.RankFragment;
import com.cui.video.view.iml.RankFragmnetContract;
import com.trello.rxlifecycle.android.FragmentEvent;

public class RankFragmentPresenter extends AbstractBasePresenter<RankFragment>
        implements RankFragmnetContract.RankFragmentPresenterIml {


    public RankFragmentPresenter(RankFragment mView) {
        super(mView);
    }


    @Override
    public void getRankListData(String strategy) {
        ApiClient.getApiService().getRankList(strategy)
                .compose(RxSchedulersHelper.io_main())
                .compose(mView.bindToLifecycle())
                .subscribe(listentity->mView.getRankListData(listentity)
                ,throwable -> mView.refreshError(throwable.toString()));

    }
}
