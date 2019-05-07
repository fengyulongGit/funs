package com.autils.framework.ui.view.dialog;

import android.content.Context;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.autils.framework.R;
import com.autils.framework.ui.adapter.SinglePickerAdapter;
import com.autils.framework.ui.view.wheelview.OnWheelChangedListener;
import com.autils.framework.ui.view.wheelview.WheelView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fengyulong on 2018/5/18.
 */
public class SingleTextPickerBottomDialog {
    private Context context;
    private List<String> items;

    private BottomSheetDialog bottomSheetDialog;
    private OnItemSelectListener mListener;

    private SinglePickerAdapter mAdapter;

    private View view;

    private int maxsize = 14;
    private int minsize = 12;


    public SingleTextPickerBottomDialog(Context context, List<String> itemsList) {
        this.context = context;
        items = itemsList;
        bottomSheetDialog = new BottomSheetDialog(context);
        setContentView();
    }

    private void setContentView() {
        view = LayoutInflater.from(context).inflate(R.layout.view_single_selector_pop_layout, null);
        initPicker();
        bottomSheetDialog.setContentView(view);
        view.findViewById(R.id.btn_myinfo_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        view.findViewById(R.id.btn_myinfo_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    WheelView wv_single = (WheelView) view.findViewById(R.id.wv_single);
                    int index = wv_single.getCurrentItem();
                    mListener.onClick(items.get(index), index);
                }
                dismiss();
            }
        });
    }

    public void show() {
        if (!bottomSheetDialog.isShowing()) {
            bottomSheetDialog.show();
        }
    }

    public void dismiss() {
        if (bottomSheetDialog.isShowing()) {
            bottomSheetDialog.dismiss();
        }
    }

    private void initPicker() {
        mAdapter = new SinglePickerAdapter(context, items, 0, maxsize, minsize);

        WheelView wv_single = (WheelView) view.findViewById(R.id.wv_single);
        wv_single.setVisibleItems(5);
        wv_single.setViewAdapter(mAdapter);
        wv_single.setCurrentItem(0);
        wv_single.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                String currentText = mAdapter.getItemText(wheel.getCurrentItem()).toString();
                setTextViewSize(currentText, mAdapter);
            }
        });
    }

    /**
     * 设置字体大小
     */
    private void setTextViewSize(String currentItemText, SinglePickerAdapter adapter) {
        if (null != adapter) {
            ArrayList<View> arrayList = adapter.getTestViews();
            String currentText;
            for (View view : arrayList) {
                TextView textView = (TextView) view;
                currentText = textView.getText().toString();
                if (currentItemText.equalsIgnoreCase(currentText)) {
                    textView.setTextSize(14f);
                } else {
                    textView.setTextSize(12f);
                }
            }
        }
    }

    public SingleTextPickerBottomDialog setOnItemSelectListener(OnItemSelectListener onItemSelectListener) {
        this.mListener = onItemSelectListener;
        return this;
    }

    public interface OnItemSelectListener {
        void onClick(String item, int index);
    }
}
