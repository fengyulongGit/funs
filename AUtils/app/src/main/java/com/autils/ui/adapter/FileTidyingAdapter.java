package com.autils.ui.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.autils.R;
import com.autils.framework.common.utils.FileUtils;
import com.autils.framework.ui.base.BaseAdapter4RecyclerView;
import com.autils.framework.ui.base.BaseViewHolder4RecyclerView;

import java.io.File;
import java.text.SimpleDateFormat;

/**
 * Created by fengyulong on 2019/4/12.
 */
public class FileTidyingAdapter extends BaseAdapter4RecyclerView<FileTidyingAdapter.ItemData, FileTidyingAdapter.ViewHolder> {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_file_tidying_item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.initViewData(getItem(position));
    }

    public class ViewHolder extends BaseViewHolder4RecyclerView<FileTidyingAdapter.ItemData> {

        private FileTidyingAdapter.ItemData itemData;
        private TextView tv_name;
        private TextView tv_size;
        private TextView tv_edit_time;
        private ImageView ic_rght;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void initView() {
            $clicks(itemView, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null) {
                        onClickListener.select(itemData.file);
                    }
                }
            });
            $longClicks(itemView, new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (onClickListener != null) {
                        onClickListener.longSelect(itemData.file);
                    }
                    return false;
                }
            });
            tv_name = $(R.id.tv_name);
            tv_size = $(R.id.tv_size);
            tv_edit_time = $(R.id.tv_edit_time);
            ic_rght = $(R.id.ic_rght);
        }

        @Override
        public void initViewData(FileTidyingAdapter.ItemData itemData) {
            this.itemData = itemData;
            tv_name.setText(itemData.file.getName());
            if (itemData.getFile().isFile()) {
                tv_size.setText(FileUtils.formatFileSize(itemData.size));
            } else {
                tv_size.setText(itemData.file.listFiles().length + " 项");
            }
            tv_edit_time.setText(dateFormat.format(itemData.lastModified));

            ic_rght.setVisibility(itemData.file.isDirectory() ? View.VISIBLE : View.GONE);
        }
    }

    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void select(File file);

        void longSelect(File file);
    }

    public static class ItemData {
        private File file;
        private long size;
        private long lastModified;

        public File getFile() {
            return file;
        }

        public void setFile(File file) {
            this.file = file;
        }

        public long getSize() {
            return size;
        }

        public void setSize(long size) {
            this.size = size;
        }

        public long getLastModified() {
            return lastModified;
        }

        public void setLastModified(long lastModified) {
            this.lastModified = lastModified;
        }
    }
}
