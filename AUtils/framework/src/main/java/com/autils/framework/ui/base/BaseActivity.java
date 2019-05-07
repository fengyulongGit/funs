package com.autils.framework.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.autils.framework.R;
import com.autils.framework.common.thirdparty.UmengTraceHelper;
import com.autils.framework.common.utils.AppManager;
import com.autils.framework.ui.utils.StatusBarUtil;
import com.autils.framework.ui.view.fragmentation.SupportActivity;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by fengyulong on 2018/5/11.
 */
public abstract class BaseActivity extends SupportActivity {
    protected Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        StatusBarUtil.statusBar(this);

        //当log_debug状态为关闭时，开启监听是否被动态调试
        /**
         * 部分MIUI8系统的机器问题，暂时关闭该验证，否则闪退无法启动
         */
//        if (!CommonUtils.DEBUG()) {
//            new EncryptUtil();
//        }

        context = this;
        AppManager.getAppManager().addActivity(this);

        if (layoutContentResID() != 0) {
            setContentView(layoutContentResID());
        }

        initViewBindClick();
        initViewData();

        if (savedInstanceState == null) {
            loadFragment(baseFragment());
        }
    }

    protected void initViewBindClick() {
    }

    protected void initViewData() {
    }

    public void loadFragment(BaseFragment baseFragment) {
        if (baseFragment == null) {
            return;
        }
        loadRootFragment(R.id.fl_container, baseFragment);
    }

    protected abstract BaseFragment baseFragment();

    @Override
    protected void onResume() {
        super.onResume();

        UmengTraceHelper.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        UmengTraceHelper.onPause(this);
    }

    protected int layoutContentResID() {
        return R.layout.activity_base;
    }

    @Override
    protected void onDestroy() {
        AppManager.getAppManager().finishActivity(this);
        super.onDestroy();
    }

    public <T extends View> T $(int viewID) {
        return (T) findViewById(viewID);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
