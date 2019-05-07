package com.autils.ui.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.autils.R;
import com.autils.framework.ui.base.BaseAdapter4RecyclerView;
import com.autils.framework.ui.base.BaseViewHolder4RecyclerView;

/**
 * Created by fengyulong on 2019/4/11.
 */
public class MainAdapter extends BaseAdapter4RecyclerView<MainAdapter.Item, MainAdapter.ViewHolder> {

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_main_item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.initViewData(getItem(position));
    }

    public class ViewHolder extends BaseViewHolder4RecyclerView<Item> {
        private TextView tv_title;
        private TextView tv_desc;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void initView() {
            tv_title = $(R.id.tv_title);
            tv_desc = $(R.id.tv_desc);

        }

        @Override
        public void initViewData(Item item) {
            tv_title.setText(item.title);
            tv_desc.setText(item.desc);
            $clicks(itemView, item.onItemClickListener);
        }
    }

    public static class Item {
        private String title;
        private String desc;
        private View.OnClickListener onItemClickListener;

        public Item(String title, String desc, View.OnClickListener onItemClickListener) {
            this.title = title;
            this.desc = desc;
            this.onItemClickListener = onItemClickListener;
        }
    }
}
