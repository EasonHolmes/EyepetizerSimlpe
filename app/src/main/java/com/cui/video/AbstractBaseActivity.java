package com.cui.video;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.OnRebindCallback;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.cui.video.helper.ActivityHelper;
import com.cui.video.helper.TransitionHelper;
import com.cui.video.presenter.AbstractBasePresenter;
import com.cui.video.view.BaseContract;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import rx.Subscription;

/**
 * Created by cuiyang on 2016/11/20.
 */

public abstract class AbstractBaseActivity<B extends ViewDataBinding, T extends AbstractBasePresenter> extends RxAppCompatActivity
        implements View.OnClickListener, BaseContract.BaseView {

    protected Toolbar mToolbar;
    protected Subscription subscription;
    protected TextView toolbarTitle;
    protected TextView txt_navi_right;
    protected TextView txt_navi_left;
    public T presenter;//在oncreate中初始化P在Ondestory中释放V
    public B binding;//在onCreate中初始化
    public final Handler mHandler = new Handler(Looper.getMainLooper(), message -> false);
    //一个界面会有一个mActivityHelper
    private ActivityHelper mActivityHelper;

    public final ActivityHelper getActivityHelper() {
        if (mActivityHelper == null) {
            mActivityHelper = new ActivityHelper(this);
        }
        return mActivityHelper;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = initPresenter();
        //如果是过于简单的页面没有P层没有写泛型的话这个binding使用的时候需要强转
        binding = DataBindingUtil.setContentView(this, setDataBindingContentViewId());

        onCreated(savedInstanceState);
    }

    protected void setFLAG_TRANSLUCENT_STATUS() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    protected void setFLAG_TRANSLUCENT_NAVIGATION() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    protected void setViewsClickListener(View... views) {
        for (View view : views) {
            view.setOnClickListener(this);
        }
    }


    protected void setBindingTranstionAnim() {
        binding.addOnRebindCallback(new OnRebindCallback() {
            @Override
            public boolean onPreBind(ViewDataBinding binding) {
                ViewGroup root = (ViewGroup) binding.getRoot();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    TransitionManager.beginDelayedTransition(root);
                }
                return true;
            }
        });
    }

    //初始化成功后
    protected abstract void onCreated(Bundle savedInstanceState);

    /* 这里是适配为不同的View 装载不同Presenter*/
    protected abstract T initPresenter();

    //设置setContentViewId
    protected abstract int setDataBindingContentViewId();

    protected void initToolbar(String titleStr, boolean isNeedBack) {
        mToolbar = (Toolbar) findViewById(R.id.mToolBar);
        assert mToolbar != null;
        mToolbar.setTitle(titleStr);
        setSupportActionBar(mToolbar);
        if (isNeedBack) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    protected void initToolbar(int titleResourceId, boolean isNeedBack) {
        initToolbar(getResources().getString(titleResourceId), isNeedBack);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        supportFinishAfterTransition();
        super.onBackPressed();
    }

    @SuppressWarnings("unchecked")
    public void transitionTo(Intent i) {
        final Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(this, true);
        ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pairs);
        startActivity(i, transitionActivityOptions.toBundle());
    }


    /**
     * 正在加载对话框按返回键取消
     *
     * @return
     */
    public Message getSimpleLoadDialogCancelMessage() {
        return null;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void refreshError(String error) {
        getActivityHelper().errordialogMessageByMine(error);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
        unsubscribe();
        if (presenter != null)//因为有些页面过于简单所以暂时不需要P层而没有
            presenter.dettach();
    }

    protected void unsubscribe() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
