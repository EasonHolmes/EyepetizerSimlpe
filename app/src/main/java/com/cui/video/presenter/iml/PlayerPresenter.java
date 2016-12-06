package com.cui.video.presenter.iml;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import com.cui.video.R;
import com.cui.video.presenter.AbstractBasePresenter;
import com.cui.video.ui.activity.video.PlayerHelperActivity;
import com.cui.video.ui.activity.video.PlayerVideoActivity;
import com.cui.video.view.iml.PlayerContract;

public class PlayerPresenter extends AbstractBasePresenter<PlayerHelperActivity>
        implements PlayerContract.PlayerPresenterIml {



    public PlayerPresenter(PlayerHelperActivity mView) {
        super(mView);
    }



}
