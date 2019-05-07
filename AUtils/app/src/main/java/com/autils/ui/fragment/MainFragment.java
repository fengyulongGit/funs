package com.autils.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.autils.R;
import com.autils.framework.common.utils.AppManager;
import com.autils.framework.ui.base.BaseFragment;
import com.autils.ui.adapter.MainAdapter;

/**
 * Created by fengyulong on 2019/4/11.
 */
public class MainFragment extends BaseFragment {
    private RecyclerView listview;
    private MainAdapter adapter;

    @Override
    protected int layoutTitleResID() {
        return R.layout.layout_title_fix_status_bar;
    }

    @Override
    protected int layoutContentResID() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initViewBindClick() {
        listview = $(R.id.listview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listview.setLayoutManager(layoutManager);//设置瀑布流管理器

        listview.setAdapter(adapter = new MainAdapter());
    }

    @Override
    protected void initViewData(Bundle savedInstanceState) {
        adapter.add(new MainAdapter.Item("文件规整( ", "多层文件夹内容整理至单个文件夹", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(FileTidyingFragment.newInstance());
            }
        }));
        adapter.add(new MainAdapter.Item("async net( ", "http接口", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(AsyncNetFragment.newInstance());
            }
        }));

        adapter.add(new MainAdapter.Item("设备信息( ", "Phone", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(PhoneFragment.newInstance());
            }
        }));
    }

    @Override
    public String getTitleText() {
        return null;
    }

    private long TOUCH_TIME = 0;

    @Override
    public boolean onBackPressedSupport() {
        if (System.currentTimeMillis() - TOUCH_TIME > 2000) {
            TOUCH_TIME = System.currentTimeMillis();
            toast(R.string.exit_app_tips);
        } else {
            AppManager.getAppManager().finishAllActivity(true);
            System.gc();
        }

        return true;
    }

    public static BaseFragment newInstance() {
        Bundle bundle = new Bundle();

        MainFragment fragment = new MainFragment();
        fragment.setArguments(bundle);

        return fragment;
    }
}
