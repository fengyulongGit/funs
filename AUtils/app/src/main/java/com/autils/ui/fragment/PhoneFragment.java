package com.autils.ui.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import com.autils.R;
import com.autils.common.utils.Phone;
import com.autils.framework.common.utils.UIUtils;
import com.autils.framework.ui.base.BaseFragment;
import com.google.gson.Gson;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

/**
 * Created by fengyulong on 2019/4/12.
 */
public class PhoneFragment extends BaseFragment {
    private TextView tv_result;

    @Override
    protected int layoutContentResID() {
        return R.layout.fragment_phone;
    }

    @Override
    protected void initViewBindClick() {
        tv_result = $(R.id.tv_result);
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initViewData(Bundle savedInstanceState) {
        new RxPermissions(getActivity()).request(
                Manifest.permission.READ_PHONE_STATE
        ).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean granted) {
                if (!granted) {
                    UIUtils.showOpenPermissionDialog(context, "");
                    return;
                }
                tv_result.setText(new Gson().toJson(new Phone()));
            }
        });
    }

    @Override
    public String getTitleText() {
        return "设备信息";
    }

    @Override
    public boolean isRoot() {
        return false;
    }

    public static BaseFragment newInstance() {
        Bundle bundle = new Bundle();

        PhoneFragment fragment = new PhoneFragment();
        fragment.setArguments(bundle);

        return fragment;
    }
}
