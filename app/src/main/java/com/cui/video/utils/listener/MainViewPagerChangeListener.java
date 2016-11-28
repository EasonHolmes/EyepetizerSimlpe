package com.cui.video.utils.listener;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.cui.video.R;


/**
 * Created by cuiyang on 16/6/7.
 */
public class MainViewPagerChangeListener implements ViewPager.OnPageChangeListener {
    private Drawable default_icon[];//默认icon
    private Drawable select_icon[];//选中的当前icon
    private TextView changeView[];//需要变化的textview
    private Context mContext;
    private PageSelectCallback pageSelectCallback;//当前page回调方便做一些其他处理
    private ViewPager viewPager;

    public MainViewPagerChangeListener(@NonNull ViewPager viewPager, @NonNull Context mContext, @NonNull Drawable[] default_icon,
                                       @NonNull Drawable[] select_icon, @NonNull TextView[] changeView,
                                       @NonNull PageSelectCallback pageSelectCallback) {
        this.default_icon = default_icon;
        this.select_icon = select_icon;
        this.changeView = changeView;
        this.mContext = mContext;
        this.viewPager = viewPager;
        this.pageSelectCallback = pageSelectCallback;

        //API提示，setCompoundDrawables() 调用的时候，Drawable对象必须调用setBounds(int left, int top, int right, int bottom)方法
        for (Drawable s : default_icon) {
            s.setBounds(0, 0, s.getMinimumWidth(), s.getMinimumHeight());
        }
        for (Drawable s : select_icon) {
            s.setBounds(0, 0, s.getMinimumWidth(), s.getMinimumHeight());
        }
        //给底部tab设置点击事件
        for (int i = 0; i < changeView.length; i++) {
            changeView[i].setOnClickListener(new SelectListener(i));
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        pageSelectCallback.onPageSelected(position);
        if (position < default_icon.length) {
            //先都设置成默认icon再设置选择icon
            setDefault_icon();
            switch (position) {
                case 0:
                    changeView[0].setCompoundDrawables(null, select_icon[0], null, null);
                    changeView[0].setTextColor(mContext.getResources().getColor(R.color.black_trans));
                    break;
                case 1:
                    changeView[1].setCompoundDrawables(null, select_icon[1], null, null);
                    changeView[1].setTextColor(mContext.getResources().getColor(R.color.black_trans));
                    break;
                case 2:
                    changeView[2].setCompoundDrawables(null, select_icon[2], null, null);
                    changeView[2].setTextColor(mContext.getResources().getColor(R.color.black_trans));
                    break;
                case 3:
                    changeView[3].setCompoundDrawables(null, select_icon[3], null, null);
                    changeView[3].setTextColor(mContext.getResources().getColor(R.color.black_trans));
                    break;
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 设置每个tab选择的当前fragment位置
     */
    private class SelectListener implements View.OnClickListener {
        int position;

        public SelectListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            switch (position) {
                case 0:
                    viewPager.setCurrentItem(0);
                    break;
                case 1:
                    viewPager.setCurrentItem(1);
                    break;
                case 2:
                    viewPager.setCurrentItem(2);
                    break;
                case 3:
                    viewPager.setCurrentItem(3);
                    break;
            }
        }
    }

    private void setDefault_icon() {
        for (int i = 0; i < changeView.length; i++) {
            changeView[i].setTextColor(mContext.getResources().getColor(R.color.cui_text_color));
            changeView[i].setCompoundDrawables(null, default_icon[i], null, null);
        }
    }

    public interface PageSelectCallback {
        void onPageSelected(int position);
    }
}
