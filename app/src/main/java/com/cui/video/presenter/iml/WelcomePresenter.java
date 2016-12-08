package com.cui.video.presenter.iml;

import com.cui.video.presenter.AbstractBasePresenter;
import com.cui.video.ui.activity.WelcomeActivity;
import com.cui.video.view.iml.WelcomeContract;

public class WelcomePresenter extends AbstractBasePresenter<WelcomeActivity>
        implements WelcomeContract.WelcomePresenterIml {


    public WelcomePresenter(WelcomeActivity mView) {
        super(mView);
    }




}
