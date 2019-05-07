package com.autils.framework.ui.view.recyclerview.itemdecoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by fengyulong on 2018/10/11.
 */
public class HorizontalSpacingItemDecoration extends RecyclerView.ItemDecoration {
    private int spacing; //间隔

    public HorizontalSpacingItemDecoration(int spacing) {
        this.spacing = spacing;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //这里是关键，需要根据你有几列来判断
        int position = parent.getChildAdapterPosition(view); // item position
        outRect.left = position == 0 ? 0 : spacing; // column * ((1f / spanCount) * spacing)
    }
}
