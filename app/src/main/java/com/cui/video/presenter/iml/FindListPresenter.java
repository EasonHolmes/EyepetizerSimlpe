package com.cui.video.presenter.iml;

import com.cui.video.entity.ItemList;
import com.cui.video.http.ApiClient;
import com.cui.video.http.RxSchedulersHelper;
import com.cui.video.presenter.AbstractBasePresenter;
import com.cui.video.ui.fragment.FindListFragment;
import com.cui.video.view.iml.FindListContract;

import java.util.ArrayList;
import java.util.List;

public class FindListPresenter extends AbstractBasePresenter<FindListFragment>
        implements FindListContract.FindListPresenterIml {

    private String findUrl = "http://baobab.kaiyanapp.com/api/v3/discovery";

    public FindListPresenter(FindListFragment mView) {
        super(mView);
    }


    @Override
    public void getFindListData() {
        ApiClient.getApiService()
                .getFindList(findUrl)
                .map(findListEntity -> {
                    List<ItemList> list = new ArrayList<ItemList>();
                    for (int i = 0; i < findListEntity.getItemList().size(); i++) {
                        if (findListEntity.getItemList().get(i).data.shade)
                            list.add(findListEntity.getItemList().get(i));
                    }
                    return list;
                })
                .compose(RxSchedulersHelper.io_main())
                .compose(mView.bindToLifecycle())
                .subscribe(list_data -> mView.getFindListData(list_data)
                        , throwable -> mView.refreshError(throwable.toString()));

    }
}
