package com.cui.video.presenter.iml;

import com.cui.video.presenter.AbstractBasePresenter;
import com.cui.video.ui.activity.video.PlayerHelperActivity;
import com.cui.video.view.iml.PlayerContract;

public class PlayerPresenter extends AbstractBasePresenter<PlayerHelperActivity>
        implements PlayerContract.PlayerPresenterIml {


    public PlayerPresenter(PlayerHelperActivity mView) {
        super(mView);
    }


}
