package com.autils.framework.ui.base;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fengyulong on 2018/5/11.
 */
public abstract class BaseAdapter4ListView<T> extends android.widget.BaseAdapter {
    private List<T> list = new ArrayList<>();

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public T getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setList(List<T> list) {
        if (list == null) {
            list = new ArrayList<>();
        }
        this.list = list;
        notifyDataSetChanged();
    }

    public void addList(@NonNull List<T> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

}
