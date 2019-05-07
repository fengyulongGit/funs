package com.autils.ui.activity;

import com.autils.framework.ui.base.BaseActivity;
import com.autils.framework.ui.base.BaseFragment;
import com.autils.ui.fragment.MainFragment;

/**
 * Created by fengyulong on 2019/4/11.
 */
public class MainActivity extends BaseActivity {
    @Override
    protected BaseFragment baseFragment() {
        return MainFragment.newInstance();
    }
}
