package com.cui.video.presenter.iml;

import com.cui.video.http.ApiClient;
import com.cui.video.http.RxSchedulersHelper;
import com.cui.video.presenter.AbstractBasePresenter;
import com.cui.video.ui.activity.ClasssDetailActivity;
import com.cui.video.view.iml.ClasssDetailContract;
import com.trello.rxlifecycle.android.ActivityEvent;

public class ClasssDetailPresenter extends AbstractBasePresenter<ClasssDetailActivity>
        implements ClasssDetailContract.ClasssDetailPresenterIml {


    public ClasssDetailPresenter(ClasssDetailActivity mView) {
        super(mView);
    }


    @Override
    public void getClasssDeatilList(int start, int categoryId, String strategy) {
        ApiClient.getApiService().getClasssDeatilList(start, categoryId, strategy)
                .compose(RxSchedulersHelper.io_main())
                .compose(mView.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(entity -> mView.getClasssDeatilList(entity)
                        , throwable -> mView.refreshError(throwable.toString()));
    }
}
