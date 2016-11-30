package com.cui.video.presenter.iml;

import com.cui.video.presenter.AbstractBasePresenter;
import com.cui.video.ui.activity.MainActivity;
import com.cui.video.view.iml.MainContract;

public class MainActivityPresenter extends AbstractBasePresenter<MainActivity>
        implements MainContract.MainActivityPresenterIml {


    public MainActivityPresenter(MainActivity mView) {
        super(mView);
    }


}
