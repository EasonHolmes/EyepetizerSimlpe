package com.cui.video.presenter.iml;

import com.cui.video.entity.FeturedListEntity;
import com.cui.video.entity.ItemList;
import com.cui.video.http.ApiClient;
import com.cui.video.http.RxSchedulersHelper;
import com.cui.video.presenter.AbstractBasePresenter;
import com.cui.video.ui.fragment.FeaturedFrament;
import com.cui.video.utils.LogUtils;
import com.cui.video.view.iml.FeaturedFragmentContract;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Func1;

import static android.R.id.list;


public class FeaturedFragmentPresenter extends AbstractBasePresenter<FeaturedFrament>
        implements FeaturedFragmentContract.FeaturedPresenterIml {
    String url = "http://baobab.kaiyanapp.com/api/v3/tabs/selected?";

    public FeaturedFragmentPresenter(FeaturedFrament mView) {
        super(mView);
    }


    @Override
    public void getFeaturedListMoreData(int page, long deteTime) {
        if (page == 1) {
            ApiClient.getApiService()
                    .getDaily()
                    .doOnNext(feturedListEntity -> mView.setDateTime(feturedListEntity))
                    .map(feturedListEntity -> {
                        List<ItemList> list1 = new ArrayList<ItemList>();
                        for (int i = 0; i < feturedListEntity.issueList.size(); i++) {
                            for (int j = 0; j < feturedListEntity.issueList.get(i).itemList.size(); j++) {
                                if (feturedListEntity.issueList.get(i).itemList.get(j).type.equals("video") ||
                                        feturedListEntity.issueList.get(i).itemList.get(j).equals("video")) {
                                    list1.add(feturedListEntity.issueList.get(i).itemList.get(j));
                                }
                            }
                        }
                        return list1;
                    })
                    .compose(RxSchedulersHelper.io_main())
                    .subscribe(FeturedListMorePageEntity -> mView.getFeaturedListMoreData(FeturedListMorePageEntity),
                            Throwables -> LogUtils.e(FeaturedFragmentPresenter.class, "ttt==" + Throwables.toString()));
        } else {
            ApiClient.getApiService()
                    .getDaily(deteTime)
                    .doOnNext(feturedListEntity -> mView.setDateTime(feturedListEntity))
                    .map(feturedListEntity -> {
                        List<ItemList> list1 = new ArrayList<ItemList>();
                        for (int i = 0; i < feturedListEntity.issueList.size(); i++) {
                            for (int j = 0; j < feturedListEntity.issueList.get(i).itemList.size(); j++) {
                                if (feturedListEntity.issueList.get(i).itemList.get(j).type.equals("video") ||
                                        feturedListEntity.issueList.get(i).itemList.get(j).equals("video")) {
                                    list1.add(feturedListEntity.issueList.get(i).itemList.get(j));
                                }
                            }
                        }
                        return list1;
                    })
                    .compose(RxSchedulersHelper.io_main())
                    .subscribe(FeturedListMorePageEntity -> mView.getFeaturedListMoreData(FeturedListMorePageEntity),
                            Throwables -> LogUtils.e(FeaturedFragmentPresenter.class, "ttt==" + Throwables.toString()));
        }
    }
}
