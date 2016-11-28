package com.cui.video.presenter.iml;

import com.cui.video.presenter.AbstractBasePresenter;
import com.cui.video.ui.activity.video.PlayerHelperActivity;
import com.cui.video.view.iml.PlayerHelperContract;

public class PlayerHelperPresenter extends AbstractBasePresenter<PlayerHelperActivity>
        implements PlayerHelperContract.PlayerHelperPresenterIml {


    public PlayerHelperPresenter(PlayerHelperActivity mView) {
        super(mView);
    }


}
