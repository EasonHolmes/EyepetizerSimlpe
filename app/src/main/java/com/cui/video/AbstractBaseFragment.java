package com.cui.video;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.OnRebindCallback;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.cui.video.presenter.AbstractBasePresenter;
import com.cui.video.view.BaseContract;
import com.trello.rxlifecycle.components.support.RxFragment;

import rx.Subscription;

/**
 * Created by cuiyang on 2016/11/20.
 */

public abstract class AbstractBaseFragment<B extends ViewDataBinding, T extends AbstractBasePresenter> extends RxFragment
        implements View.OnClickListener,BaseContract.BaseView{

    protected Toolbar mToolbar;
    protected Subscription subscription;
    protected TextView toolbarTitle;
    public T presenter;//在oncreate中初始化P在Ondestory中释放V
    public B binding;//在onCreate中初始化
    protected Context mContext;
    protected AbstractBaseActivity activity;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, setDataBindingContentViewId(), null, false);
        mContext = container.getContext();
        activity = (AbstractBaseActivity) getActivity();
        return binding.getRoot();

    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = initPresenter();

        onFragmentViewCreated(view, savedInstanceState);
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
    protected abstract void onFragmentViewCreated(View view, Bundle savedInstanceState);

    /* 这里是适配为不同的View 装载不同Presenter*/
    protected abstract T initPresenter();

    //设置setContentViewId
    protected abstract int setDataBindingContentViewId();


    protected TextView initToolbar(View v, int titleResourceId) {
        return initToolbar(v, getResources().getString(titleResourceId));

    }

    protected TextView initToolbar(View v, String title) {
        mToolbar = (Toolbar) v.findViewById(R.id.mToolBar);
        toolbarTitle = (TextView) v.findViewById(R.id.mToolBar);
        toolbarTitle.setText(title);
        assert mToolbar != null;
        mToolbar.setTitle("");
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        return toolbarTitle;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void refreshError(String error) {

    }
}
