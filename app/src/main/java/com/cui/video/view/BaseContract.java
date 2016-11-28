package com.cui.video.view;


/**
 * Created by cuiyang on 16/5/11.
 */
public interface BaseContract {

    interface BaseView {

        /**
         * 程序错误返回
         *
         * @param error
         */
        void refreshError(String error);


    }

    interface BasePresenter {
    }
}
