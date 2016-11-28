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

//    @Override
//    public List<FeturedDataEntity> updateDataByServiceData(Object entity) {
//        if (entity instanceof FeturedListOnePageEntity) {
//            return setDatas((FeturedListOnePageEntity) entity);
//        } else {
//            return setDatas((FeturedListMorePageEntity) entity);
//        }
//    }
//
//    private List<FeturedDataEntity> setDatas(FeturedListOnePageEntity entity) {
//        List<FeturedDataEntity> list_data = new ArrayList<>();
//        for (FeturedListOnePageEntity.ItemListEntity itemListEntity : entity.getItemList()) {
//            FeturedDataEntity feturedDataEntity = new FeturedDataEntity();
//            feturedDataEntity.setCategory(itemListEntity.getData().getCategory());
//            feturedDataEntity.setAuthor(itemListEntity.getData().getAuthor());
//            feturedDataEntity.setConver(itemListEntity.getData().getCover().getFeed());
//            feturedDataEntity.setDataType(itemListEntity.getData().getDataType());
//            feturedDataEntity.setDescription(itemListEntity.getData().getDescription());
//            feturedDataEntity.setTitle(itemListEntity.getData().getTitle());
//            feturedDataEntity.setId(itemListEntity.getData().getId());
//            feturedDataEntity.setDuration(itemListEntity.getData().getDuration());
//            feturedDataEntity.setPlayerHigh(itemListEntity.getData().getPlayInfo().size() >= 2 ? itemListEntity.getData().getPlayInfo().get(1).getUrl() : "");
//            feturedDataEntity.setPlayerNormal(itemListEntity.getData().getPlayInfo().size() >= 1 ? itemListEntity.getData().getPlayInfo().get(0).getUrl() : "");
//            feturedDataEntity.setPlayerUrlDefault(itemListEntity.getData().getPlayUrl());
//            feturedDataEntity.setReleaseTime(itemListEntity.getData().getReleaseTime());
//            feturedDataEntity.setBulerImgUrl(itemListEntity.getData().getCover().getBlurred());
//            list_data.add(feturedDataEntity);
//        }
//        return list_data;
//    }
//
//    private List<FeturedDataEntity> setDatas(FeturedListMorePageEntity entity) {
//        List<FeturedDataEntity> list_data = new ArrayList<>();
//        for (FeturedListMorePageEntity.ItemListEntityX.DataEntityXX.ItemListEntity itemListEntity : entity.getItemList().get(0).getData().getItemList()) {
//            FeturedDataEntity feturedDataEntity = new FeturedDataEntity();
//            feturedDataEntity.setCategory(itemListEntity.getData().getCategory());
//            feturedDataEntity.setAuthor(itemListEntity.getData().getAuthor());
//            feturedDataEntity.setConver(itemListEntity.getData().getCover().getFeed());
//            feturedDataEntity.setDataType(itemListEntity.getData().getDataType());
//            feturedDataEntity.setDescription(itemListEntity.getData().getDescription());
//            feturedDataEntity.setId(itemListEntity.getData().getId());
//            feturedDataEntity.setTitle(itemListEntity.getData().getTitle());
//            feturedDataEntity.setDuration(itemListEntity.getData().getDuration());
//            feturedDataEntity.setPlayerHigh(itemListEntity.getData().getPlayInfo().size() >= 2 ? itemListEntity.getData().getPlayInfo().get(1).getUrl() : "");
//            feturedDataEntity.setPlayerNormal(itemListEntity.getData().getPlayInfo().size() >= 1 ? itemListEntity.getData().getPlayInfo().get(0).getUrl() : "");
//            feturedDataEntity.setPlayerUrlDefault(itemListEntity.getData().getPlayUrl());
//            feturedDataEntity.setReleaseTime(itemListEntity.getData().getReleaseTime());
//            feturedDataEntity.setBulerImgUrl(itemListEntity.getData().getCover().getBlurred());
//            list_data.add(feturedDataEntity);
//        }
//        return list_data;
//    }
}
