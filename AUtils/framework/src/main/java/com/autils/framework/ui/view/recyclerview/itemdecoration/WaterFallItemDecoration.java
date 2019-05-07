package com.autils.framework.ui.view.recyclerview.itemdecoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * Created by fengyulong on 2018/10/12.
 */
public class WaterFallItemDecoration extends RecyclerView.ItemDecoration {
    private int spanCount; //列数
    private int spacing; //间隔

    public WaterFallItemDecoration(int spanCount, int spacing) {
        this.spacing = spacing;
        this.spanCount = spanCount;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
//        if (parent.getChildViewHolder(view) instanceof HomeAdapter.FormalThreeVH) {}

        int position = parent.getChildAdapterPosition(view); // item position

//        if (position >= spanCount) {
        outRect.top = spacing; // item top
//        }

        //瀑布流专属分割线
        StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
        /**
         * 根据params.getSpanIndex()来判断左右边确定分割线
         * 第一列设置左边距为spacing，右边距为spacing/2  （第二列反之）
         */
        if (params.getSpanIndex() % 2 == 0) {
            outRect.left = spacing;
            outRect.right = spacing / 2;
        } else {
            outRect.left = spacing / 2;
            outRect.right = spacing;
        }
    }
}
