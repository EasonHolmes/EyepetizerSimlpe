package com.cui.video.presenter.iml;

import com.cui.video.presenter.AbstractBasePresenter;
import com.cui.video.ui.activity.VideoDeatilActivity;
import com.cui.video.view.iml.VideoDetailContract;

public class VideoDetailPresenter extends AbstractBasePresenter<VideoDeatilActivity>
        implements VideoDetailContract.VideoDetailPresenterIml {


    public VideoDetailPresenter(VideoDeatilActivity mView) {
        super(mView);
    }


}
