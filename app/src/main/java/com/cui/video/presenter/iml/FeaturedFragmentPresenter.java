package com.cui.video.presenter.iml;

import com.cui.video.http.ApiClient;
import com.cui.video.http.RxSchedulersHelper;
import com.cui.video.presenter.AbstractBasePresenter;
import com.cui.video.ui.fragment.FeaturedFrament;
import com.cui.video.utils.LogUtils;
import com.cui.video.view.iml.FeaturedFragmentContract;


public class FeaturedFragmentPresenter extends AbstractBasePresenter<FeaturedFrament>
        implements FeaturedFragmentContract.FeaturedPresenterIml {
    String url = "http://baobab.kaiyanapp.com/api/v3/tabs/selected?";

    public FeaturedFragmentPresenter(FeaturedFrament mView) {
        super(mView);
    }


    @Override
    public void getFeaturedListMoreData(int page,long deteTime) {
        if(page ==1){
            ApiClient.getApiService()
                    .getDaily()
                    .compose(RxSchedulersHelper.io_main())
                    .subscribe(FeturedListMorePageEntity -> mView.getFeaturedListMoreData(FeturedListMorePageEntity),
                            Throwables -> LogUtils.e(FeaturedFragmentPresenter.class, "ttt==" + Throwables.toString()));
        }else{
            ApiClient.getApiService()
                    .getDaily(deteTime)
                    .compose(RxSchedulersHelper.io_main())
                    .subscribe(FeturedListMorePageEntity -> mView.getFeaturedListMoreData(FeturedListMorePageEntity),
                            Throwables -> LogUtils.e(FeaturedFragmentPresenter.class, "ttt==" + Throwables.toString()));
        }
    }
}
