package com.cui.video.presenter.iml;

import com.cui.video.presenter.AbstractBasePresenter;
import com.cui.video.ui.activity.FeaturedDeatilActivity;
import com.cui.video.view.iml.FeaturedDetailContract;

public class FeaturedDetailPresenter extends AbstractBasePresenter<FeaturedDeatilActivity>
        implements FeaturedDetailContract.FeaturedDetailPresenterIml {


    public FeaturedDetailPresenter(FeaturedDeatilActivity mView) {
        super(mView);
    }


}
