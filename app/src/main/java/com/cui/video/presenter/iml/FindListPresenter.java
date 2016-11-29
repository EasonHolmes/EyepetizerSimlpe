package com.cui.video.presenter.iml;

import com.cui.video.entity.FindListEntity;
import com.cui.video.http.ApiClient;
import com.cui.video.http.RxSchedulersHelper;
import com.cui.video.presenter.AbstractBasePresenter;
import com.cui.video.ui.fragment.FindListFragment;
import com.cui.video.utils.StringUtils;
import com.cui.video.view.iml.FindListContract;
import com.trello.rxlifecycle.android.FragmentEvent;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class FindListPresenter extends AbstractBasePresenter<FindListFragment>
        implements FindListContract.FindListPresenterIml {

    private String findUrl = "http://baobab.kaiyanapp.com/api/v3/discovery?udid=80259deac61a4e9585d78a6439771216058e88f9&vc=144&vn=2.9.3&deviceModel=Nexus%206&first_channel=eyepetizer_wandoujia_market&last_channel=eyepetizer_wandoujia_market&system_version_code=24";

    public FindListPresenter(FindListFragment mView) {
        super(mView);
    }


    @Override
    public void getFindListData() {
        ApiClient.getApiService()
                .getFindList(findUrl)
                .map(findListEntity -> {
                    for (int i = 0; i < findListEntity.getItemList().size(); i++) {
                        if (!findListEntity.getItemList().get(i).getData().isShade())
                            findListEntity.getItemList().remove(i);
                    }
                    return findListEntity;
                })
                .compose(RxSchedulersHelper.io_main())
                .compose(mView.bindToLifecycle())
                .subscribe(findListEntity -> mView.getFindListData(findListEntity)
                        , throwable -> mView.refreshError(throwable.toString()));

    }
}
