package com.cui.video.view.iml;

import com.cui.video.view.BaseContract;

import static android.databinding.tool.reflection.TypeUtil.VOID;

public interface VideoDetailContract {
    interface VideoDetailView extends BaseContract.BaseView {
        void showShareDialog();
    }

    interface VideoDetailPresenterIml extends BaseContract.BasePresenter {
    }
}
