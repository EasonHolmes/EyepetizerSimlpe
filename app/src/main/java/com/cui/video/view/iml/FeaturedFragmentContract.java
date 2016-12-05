package com.cui.video.view.iml;

import com.cui.video.entity.FeturedListEntity;
import com.cui.video.entity.ItemList;
import com.cui.video.view.BaseContract;

import java.util.List;

public interface FeaturedFragmentContract {
    interface FeaturedFragmentView extends BaseContract.BaseView {

        void getFeaturedListMoreData(List<ItemList> entity);

    }

    interface FeaturedPresenterIml extends BaseContract.BasePresenter {

        /**
         * 获取精选列表
         * ／／精选
         * http://baobab.wandoujia.com/api/v3/tabs/selected?udid=80259deac61a4e9585d78a6439771216058e88f9&vc=144&vn=2.9.3&deviceModel=Nexus%206&first_channel=dream2_hl&last_channel=eyepetizer_wandoujia_market&system_version_code=24
         * 同样是精选 pagination是页数
         * http://baobab.kaiyanapp.com/api/v3/tabs/selected?pagination=0&needFilter=true
         */
        /**
         * 第一页和后面的结构不一样
         * @param page
         */
        void getFeaturedListMoreData(int page ,long detaTime);

    }
}
