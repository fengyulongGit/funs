package com.autils.framework.ui.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fengyulong on 2018/12/7.
 */
public abstract class BaseAdapter4RecyclerView<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    private List<T> list = new ArrayList<>();

    public T getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
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

    public void add(T t) {
        if (list == null) {
            list = new ArrayList<>();
        }

        list.add(t);

        notifyDataSetChanged();
    }

    public void add(int position, T t) {
        if (list == null) {
            list = new ArrayList<>();
        }

        list.add(position, t);

        notifyDataSetChanged();
    }

    public void remove(T t) {
        if (list != null) {
            list.remove(t);
        }
        notifyDataSetChanged();
    }

    public void remove(int position) {
        if (list != null && list.size() > position) {
            list.remove(position);
        }
        notifyDataSetChanged();
    }


    public int getPosition(T t) {
        return list.indexOf(t);
    }
}
