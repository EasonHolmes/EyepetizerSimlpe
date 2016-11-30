package com.cui.video.presenter.iml;

import com.cui.video.presenter.AbstractBasePresenter;
import com.cui.video.ui.activity.RankActivity;
import com.cui.video.view.iml.RankActivityContract;

public class RankPresenter extends AbstractBasePresenter<RankActivity>
        implements RankActivityContract.RankActivityPresenterIml {


    public RankPresenter(RankActivity mView) {
        super(mView);
    }


}
