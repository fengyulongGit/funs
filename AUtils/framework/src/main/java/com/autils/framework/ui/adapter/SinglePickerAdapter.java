package com.autils.framework.ui.adapter;

import android.content.Context;

import com.autils.framework.R;
import com.autils.framework.ui.view.wheelview.adapter.AbstractWheelTextAdapter1;

import java.util.List;

/**
 * Created by fengyulong on 2018/5/18.
 */
public class SinglePickerAdapter extends AbstractWheelTextAdapter1 {
    private List<String> list;

    public SinglePickerAdapter(Context context, List<String> list, int currentIndex, int maxsize, int minsize) {
        super(context, R.layout.item_birth_year, AbstractWheelTextAdapter1.NO_RESOURCE, currentIndex, maxsize, minsize);
        setItemTextResource(R.id.tempValue);
        this.list = list;
    }

    @Override
    public CharSequence getItemText(int index) {
        if (list != null) {
            return list.get(index);
        }
        return "";
    }

    @Override
    public int getItemsCount() {
        return list != null ? list.size() : 0;
    }
}
