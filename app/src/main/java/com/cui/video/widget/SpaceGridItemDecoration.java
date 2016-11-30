package com.cui.video.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class SpaceGridItemDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public SpaceGridItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //不是第一个的格子都设一个左边和底部的间距
        outRect.left = space;
        outRect.bottom = space;
        //因为加了头部所以固定把position0 和1直接不设置分割线
        if(parent.getChildLayoutPosition(view) == 0 || parent.getChildLayoutPosition(view) ==1){
            outRect.left = 0;
        }else if (parent.getChildLayoutPosition(view) % 2 == 0){//由于每行都只有2个，所以第一个都是2的倍数，把左边距设为0
            outRect.left = 0;
        }

        //由于每行都只有2个，所以第一个都是2的倍数，把左边距设为0
//        if (parent.getChildLayoutPosition(view) % 2 == 0){
//            outRect.left = 0;
//        }
    }

}