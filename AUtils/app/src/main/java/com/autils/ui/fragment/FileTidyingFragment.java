package com.autils.ui.fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.autils.R;
import com.autils.common.utils.FileTidyingUtils;
import com.autils.framework.common.log.L;
import com.autils.framework.common.utils.PathUtils;
import com.autils.framework.common.utils.UIUtils;
import com.autils.framework.ui.base.BaseFragment;
import com.autils.framework.ui.view.dialog.ConfirmDialog;
import com.autils.framework.ui.view.recyclerview.itemdecoration.HorizontalSpacingItemDecoration;
import com.autils.ui.adapter.FileTidyingAdapter;
import com.autils.ui.adapter.FileTidyingTitleAdapter;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by fengyulong on 2019/4/12.
 */
public class FileTidyingFragment extends BaseFragment {
    private RecyclerView listview_title;
    private FileTidyingTitleAdapter titleAdapter;

    private RecyclerView listview;
    private FileTidyingAdapter adapter;

    @Override
    protected int layoutContentResID() {
        return R.layout.fragment_file_tidying;
    }

    @Override
    protected void initViewBindClick() {
        listview_title = $(R.id.listview_title);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        listview_title.setLayoutManager(layoutManager);//设置瀑布流管理器
        listview_title.addItemDecoration(new HorizontalSpacingItemDecoration(UIUtils.dip2px(1)));//边距和分割线，需要自己定义
        listview_title.setAdapter(titleAdapter = new FileTidyingTitleAdapter());
        titleAdapter.setOnClickListener(new FileTidyingTitleAdapter.OnClickListener() {
            @Override
            public void select(File file) {
                for (int i = titleAdapter.getPosition(file) + 1; i < titleAdapter.getItemCount(); ) {
                    titleAdapter.remove(i);
                }

                adapter.setList(getFiles(file));
            }
        });

        listview = $(R.id.listview);
        layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listview.setLayoutManager(layoutManager);//设置瀑布流管理器
        listview.setAdapter(adapter = new FileTidyingAdapter());
        adapter.setOnClickListener(new FileTidyingAdapter.OnClickListener() {
            @Override
            public void select(File file) {
                if (file.isDirectory()) {
                    titleAdapter.add(file);
                    listview_title.scrollToPosition(titleAdapter.getItemCount() - 1);
                    adapter.setList(getFiles(file));
                } else if (file.isFile()) {
//                    Intent intent = new Intent(Intent.ACTION_VIEW);
//                    intent.setAction("android.intent.action.VIEW");
//                    intent.setDataAndType(Uri.parse(file.getAbsolutePath()),"video/*");
//                    startActivity(intent);
                } else {
                    L.e("unknow " + file.getAbsolutePath());
                }
            }

            @Override
            public void longSelect(final File file) {
                new ConfirmDialog.Builder(context)
                        .setMessage("是否整理该文件夹")
                        .setNegativeButton(context.getResources().getString(com.autils.framework.R.string.cancel), null, context.getResources().getColor(com.autils.framework.R.color.text_333))
                        .setPositiveButton(context.getResources().getString(com.autils.framework.R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Observable.just(true)
                                        .throttleFirst(300, TimeUnit.MILLISECONDS)
                                        .compose(FileTidyingFragment.this.<Boolean>bindUntilEvent(FragmentEvent.DESTROY))
                                        .subscribeOn(Schedulers.io())
                                        .map(new Function<Boolean, Object>() {
                                            @Override
                                            public Boolean apply(Boolean flag) {
                                                FileTidyingUtils.tidying(file);
                                                return flag;
                                            }
                                        })
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .doOnSubscribe(new Consumer<Disposable>() {
                                            @Override
                                            public void accept(Disposable disposable) throws Exception {
                                                showLoading();
                                            }
                                        })
                                        .doOnTerminate(new Action() {
                                            @Override
                                            public void run() throws Exception {
                                                dismissLoading();
                                            }
                                        })
                                        .subscribe(new Consumer<Object>() {
                                            @Override
                                            public void accept(Object flag) {
                                                toast("完成");
                                            }
                                        });

                            }
                        })
                        .create().show();
            }
        });
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initViewData(Bundle savedInstanceState) {
        titleAdapter.add(new File(PathUtils.SdCard()));

        Observable.just(titleAdapter.getItem(0))
                .throttleFirst(300, TimeUnit.MILLISECONDS)
                .compose(FileTidyingFragment.this.<File>bindUntilEvent(FragmentEvent.DESTROY))
                .subscribeOn(Schedulers.io())
                .map(new Function<File, List<FileTidyingAdapter.ItemData>>() {
                    @Override
                    public List<FileTidyingAdapter.ItemData> apply(File file) {
                        return getFiles(file);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showLoading();
                    }
                })
                .doOnTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        dismissLoading();
                    }
                })
                .subscribe(new Consumer<List<FileTidyingAdapter.ItemData>>() {
                    @Override
                    public void accept(List<FileTidyingAdapter.ItemData> files) {
                        adapter.setList(files);
                    }
                });
    }

    private List<FileTidyingAdapter.ItemData> getFiles(File file) {
        List<FileTidyingAdapter.ItemData> list = new ArrayList<>();

        File[] files = file.listFiles();
        if (files == null) {
            return list;
        }
        for (File f : files) {
            if (f.getName().startsWith(".")) {
                continue;
            }
            FileTidyingAdapter.ItemData itemData = new FileTidyingAdapter.ItemData();
            itemData.setFile(f);
            if (f.isFile()) {
                itemData.setSize(f.length());
            }
            itemData.setLastModified(f.lastModified());

            list.add(itemData);
        }

        Collections.sort(list, new Comparator<FileTidyingAdapter.ItemData>() {
            @Override
            public int compare(FileTidyingAdapter.ItemData o1, FileTidyingAdapter.ItemData o2) {
                if (o1.getFile().isDirectory() && o2.getFile().isFile()) {
                    return -1;
                }
                if (o1.getFile().isFile() && o2.getFile().isDirectory()) {
                    return 1;
                }
                return o1.getFile().getName().compareTo(o2.getFile().getName());
            }
        });

        //TODO 根据大小排序

        return list;
    }

    public boolean canGoBack() {
        if (titleAdapter.getItemCount() > 1) {
            titleAdapter.remove(titleAdapter.getItemCount() - 1);
            adapter.setList(getFiles(titleAdapter.getItem(titleAdapter.getItemCount() - 1)));
            return true;
        }
        return false;
    }

    @Override
    public String getTitleText() {
        return "文件规整";
    }

    @Override
    public void left() {
        if (canGoBack()) {
            return;
        }

        super.left();
    }

    @Override
    public boolean onBackPressedSupport() {
        if (canGoBack()) {
            return true;
        }
        return super.onBackPressedSupport();
    }

    @Override
    public boolean isRoot() {
        return false;
    }

    public static BaseFragment newInstance() {
        Bundle bundle = new Bundle();

        FileTidyingFragment fragment = new FileTidyingFragment();
        fragment.setArguments(bundle);

        return fragment;
    }
}
