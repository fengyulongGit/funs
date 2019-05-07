package com.autils.framework.ui.view.dragview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.autils.framework.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fengyulong on 2018/10/26.
 */
public class DragView extends RelativeLayout implements MoveLayout.DeleteMoveLayout {

    private int mSelfViewWidth = 0;
    private int mSelfViewHeight = 0;

    private Context mContext;

    /**
     * the identity of the moveLayout
     */
    private int mLocalIdentity = 0;

    private List<MoveLayout> mMoveLayoutList;

    private boolean isAddDeleteView = false;
    private TextView deleteArea;

    private int DELETE_AREA_WIDTH = 180;
    private int DELETE_AREA_HEIGHT = 90;


    public DragView(Context context) {
        this(context, null);
    }

    public DragView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, this);
    }

    private void init(Context c, DragView thisp) {
        mContext = c;
        mMoveLayoutList = new ArrayList<>();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //  Log.e(TAG, "onDraw: height=" + getHeight());
        mSelfViewWidth = getWidth();
        mSelfViewHeight = getHeight();

        if (mMoveLayoutList != null) {
            int count = mMoveLayoutList.size();
            for (int a = 0; a < count; a++) {
                mMoveLayoutList.get(a).setViewWidthHeight(mSelfViewWidth, mSelfViewHeight);
                mMoveLayoutList.get(a).setDeleteWidthHeight(DELETE_AREA_WIDTH, DELETE_AREA_HEIGHT);
            }
        }

    }

    /**
     * 每个moveLayout都可以拥有自己的最小尺寸
     */
    public void addDragView(int resId, int width, int height, int left, int top) {
        View selfView = LayoutInflater.from(mContext).inflate(resId, null);

        addDragView(selfView, width, height, left, top);
    }

    public void addDragView(View selfView, int width, int height, int left, int top) {
        addDragView(selfView, width, height, left, top, false);
    }

    public void addDragView(int resId, int width, int height, int left, int top, boolean isCanMove) {
        View selfView = LayoutInflater.from(mContext).inflate(resId, null);

        addDragView(selfView, width, height, left, top, isCanMove);
    }

    public void addDragView(View selfView, int width, int height, int left, int top, boolean isCanMove) {
        addDragView(selfView, width, height, left, top, isCanMove, true);
    }

    public void addDragView(int resId, int width, int height, int left, int top, boolean isCanMove, boolean isFixedSize) {
        View selfView = LayoutInflater.from(mContext).inflate(resId, null);

        addDragView(selfView, width, height, left, top, isCanMove, isFixedSize);
    }

    /**
     * selfView
     * width 宽
     * height 高
     * left x
     * top y
     * isFixedSize true 锁定，false 可拖动大小
     * isCanMove true 可拖动位置，false 锁定不可拖动
     */
    public void addDragView(View selfView, int width, int height, int left, int top, boolean isCanMove, boolean isFixedSize) {
        //    invalidate();

        MoveLayout moveLayout = new MoveLayout(mContext);

        moveLayout.setClickable(true);

        moveLayout.setCanMove(isCanMove);
        //set fixed size
        moveLayout.setFixedSize(isFixedSize);

        moveLayout.setMinWidth(width);
        moveLayout.setMinHeight(height);

        //set postion
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(width, height);
        lp.setMargins(left, top, 0, 0);
        moveLayout.setLayoutParams(lp);

        //add sub view (has click indicator)
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View dragSubView = inflater.inflate(R.layout.drag_sub_view, null);
        LinearLayout addYourViewHere = (LinearLayout) dragSubView.findViewById(R.id.add_your_view_here);
        LinearLayout.LayoutParams lv = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addYourViewHere.addView(selfView, lv);

        LinearLayout changeBg = (LinearLayout) dragSubView.findViewById(R.id.change_bg);
        changeBg.setBackgroundDrawable(null);//可以修改背景

        moveLayout.addView(dragSubView);

        if (isAddDeleteView) {
            moveLayout.setOnDeleteMoveLayout(this);
        }
        moveLayout.setIdentity(mLocalIdentity++);

        if (isAddDeleteView) {
            //add delete area
            deleteArea = new TextView(mContext);
            deleteArea.setText("delete");
            deleteArea.setBackgroundColor(Color.argb(99, 0xbb, 0, 0));
            RelativeLayout.LayoutParams dellayout = new RelativeLayout.LayoutParams(DELETE_AREA_WIDTH, DELETE_AREA_HEIGHT);
            dellayout.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            dellayout.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            deleteArea.setLayoutParams(dellayout);
            deleteArea.setGravity(Gravity.CENTER);
            // moveLayout.setDeleteWidthHeight(180, 90);
            deleteArea.setVisibility(View.INVISIBLE);
            addView(deleteArea);
        }

        //set view to get control
        moveLayout.setDeleteView(deleteArea);

        addView(moveLayout);

        mMoveLayoutList.add(moveLayout);
    }

    @Override
    public void onDeleteMoveLayout(int identity) {
        int count = mMoveLayoutList.size();
        for (int a = 0; a < count; a++) {
            if (mMoveLayoutList.get(a).getIdentity() == identity) {
                //delete
                removeView(mMoveLayoutList.get(a));
            }
        }
    }

    public DragView setAddDeleteView(boolean addDeleteView) {
        this.isAddDeleteView = addDeleteView;
        return this;
    }

    //
//    /**
//     * delete interface
//     */
//    private DeleteDragView mListener = null;
//    public interface DeleteDragView {
//        void onDeleteDragView();
//    }
//    public void setOnDeleteDragView(DeleteDragView l) {
//        mListener = l;
//    }


}
