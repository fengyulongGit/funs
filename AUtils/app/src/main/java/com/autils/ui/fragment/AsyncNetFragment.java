package com.autils.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.autils.R;
import com.autils.common.utils.AsyncNetUtils;
import com.autils.framework.ui.base.BaseFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fengyulong on 2019/4/12.
 */
public class AsyncNetFragment extends BaseFragment {
    private TextView tv_result;

    @Override
    protected int layoutContentResID() {
        return R.layout.fragment_async_net;
    }

    @Override
    protected void initViewBindClick() {
        final Map<String, String> params = new HashMap<>();
        params.put("js_code", "1213测试你好");

        $clicks(R.id.btn_get, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncNetUtils.get("http://172.16.10.139:8080/code2Session", params, new AsyncNetUtils.CallBack() {
                    @Override
                    public void callback(String response) {
                        tv_result.setText(response);
                    }
                });
            }
        });

        $clicks(R.id.btn_post, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncNetUtils.post("http://172.16.10.139:8080/code2Session", params, new AsyncNetUtils.CallBack() {
                    @Override
                    public void callback(String response) {
                        tv_result.setText(response);
                    }
                });
            }
        });
        tv_result = $(R.id.tv_result);
    }

    @Override
    protected void initViewData(Bundle savedInstanceState) {

    }

    @Override
    public String getTitleText() {
        return "async net";
    }

    @Override
    public boolean isRoot() {
        return false;
    }

    public static BaseFragment newInstance() {
        Bundle bundle = new Bundle();

        AsyncNetFragment fragment = new AsyncNetFragment();
        fragment.setArguments(bundle);

        return fragment;
    }
}
