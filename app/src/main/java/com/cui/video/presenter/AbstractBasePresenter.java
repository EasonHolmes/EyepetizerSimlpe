package com.cui.video.presenter;

/**
 * Created by cuiyang on 16/6/6.
 */
public class AbstractBasePresenter<V> {
//    /**
//     * 保存subscription用来在界面销毁的时候释放用
//     */
//    public Subscription subscription;


    /*这个基类的Presenter 主要的作用就是将当前Presenter持有的view 在合适的时候给清除掉*/
    //如果有多个P2使用同一个P1的情况在P1不要确定泛型在P2中的构造方法中强转
    public V mView;

    public void dettach() {
            mView = null;
    }

    public AbstractBasePresenter(V mView) {
        this.mView = mView;
    }
}
