package com.cui.video.http;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by cuiyang on 16/6/29.
 * http://www.jianshu.com/p/f3f0eccbcd6f
 */
public class RxSchedulersHelper {

    //http://www.jianshu.com/p/e9e03194199e
    private static final Observable.Transformer schedulersTransformer = new Observable.Transformer() {
        @Override
        public Object call(Object observable) {
            return ((Observable) observable).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    };

    /**
     * 网络请求统一的线程调度
     *
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<T, T> io_main() {
        return (Observable.Transformer<T, T>) schedulersTransformer;
    }

    //这个方法和上面的一样  http://www.jianshu.com/p/f3f0eccbcd6f
//    public static <T> Observable.Transformer<T, T> io_main() {
//        return tObservable -> tObservable
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());
//    }

//    public static <T> Observable.Transformer<BaseEntity, T> handleResult(AbstractBaseActivity activity) {
//        return new Observable.Transformer<BaseEntity, T>() {
//            @Override
//            public Observable<T> call(Observable<BaseEntity> tObservable) {
//                return tObservable.flatMap(
//                        new Func1<BaseEntity, Observable<T>>() {
//                            @Override
//                            public Observable<T> call(BaseEntity result) {
//                                if (result.isSuccess()) {
//                                    return (Observable<T>) createData(result);
//                                } else if (result.getCode() == -1) {
//                                    activity.getActivityHelper().dialogMessageErrorByService(result);
//                                }
//                                return Observable.empty();
//                            }
//                        }
//                );
//            }
//        };
//    }
//
//    private static <T> Observable<BaseEntity> createData(BaseEntity entity) {
//        return Observable.create(new Observable.OnSubscribe<BaseEntity>() {
//            @Override
//            public void call(Subscriber<? super BaseEntity> subscriber) {
//                try {
//                    subscriber.onNext(entity);
//                    subscriber.onCompleted();
//                } catch (Exception e) {
//                    subscriber.onError(e);
//                }
//            }
//        });
//    }
}
