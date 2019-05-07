package com.autils.framework.ui.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.CheckResult;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.autils.api.ServerClient;
import com.autils.api.exception.ApiException;
import com.autils.api.response.BaseResponse;
import com.autils.api.utils.StringUtils;
import com.autils.framework.Launcher;
import com.autils.framework.R;
import com.autils.framework.common.thirdparty.UmengTraceHelper;
import com.autils.framework.common.utils.UIUtils;
import com.autils.framework.data.db.DbHelper;
import com.autils.framework.ui.base.mvp.ILoadView;
import com.autils.framework.ui.utils.Toast;
import com.autils.framework.ui.view.dialog.LoadingDialog;
import com.autils.framework.ui.view.fragmentation.SupportFragment;
import com.autils.framework.ui.view.fragmentation.anim.DefaultHorizontalAnimator;
import com.autils.framework.ui.view.fragmentation.anim.FragmentAnimator;
import com.jakewharton.rxbinding2.view.RxView;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.android.RxLifecycleAndroid;

import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by fengyulong on 2018/5/12.
 */
public abstract class BaseFragment extends SupportFragment implements ILoadView, LifecycleProvider<FragmentEvent> {
    protected Context context;
    private LoadingDialog loadingDialogFragment;

    private LinearLayout base_title;
    private LinearLayout base_content;
    private LinearLayout base_error;

    private View layout_title;
    private View layout_title_bar;
    private TextView tv_left;
    private TextView tv_close;
    private TextView tv_title;
    private TextView tv_right;
    private Toolbar toolbar;
    private View v_bottom_line;

    protected LinearLayout error_view_network_error;
    protected LinearLayout error_view_service_error;
    protected TextView bt_error_view_retry;

    private BehaviorSubject<FragmentEvent> lifecycleSubject = BehaviorSubject.create();

    public DbHelper dbHelper;

    public void toast(int message) {
        Toast.toast(context, message);
    }

    public void toast(String message) {
        Toast.toast(context, message);
    }

    @CheckResult
    @Override
    public Observable<FragmentEvent> lifecycle() {
        return lifecycleSubject.hide();
    }

    @CheckResult
    @Override
    public <T> LifecycleTransformer<T> bindUntilEvent(FragmentEvent event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
    }

    @CheckResult
    @Override
    public <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifecycleAndroid.bindFragment(lifecycleSubject);
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }

    @CallSuper
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        lifecycleSubject.onNext(FragmentEvent.ATTACH);
    }

    @CallSuper
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getContext();
        dbHelper = DbHelper.getInstance();
        lifecycleSubject.onNext(FragmentEvent.CREATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        lifecycleSubject.onNext(FragmentEvent.CREATE_VIEW);

        View view = inflater.inflate(R.layout.fragment_base, container, false);
        base_title = (LinearLayout) view.findViewById(R.id.base_title);
        base_content = (LinearLayout) view.findViewById(R.id.base_content);
        base_error = (LinearLayout) view.findViewById(R.id.base_error);
        if (layoutTitleResID() != 0) {
            LayoutInflater.from(context).inflate(layoutTitleResID(), base_title);
        }
        if (layoutContentResID() != 0) {
            LayoutInflater.from(context).inflate(layoutContentResID(), base_content);
        }
        if (layoutErrorResID() != 0) {
            LayoutInflater.from(context).inflate(layoutErrorResID(), base_error);
        }
        return view;
    }

    protected int layoutTitleResID() {
        return R.layout.layout_title;
    }

    protected abstract int layoutContentResID();

    protected int layoutErrorResID() {
        return R.layout.layout_view_error;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initCommonView();

        initTitleBar();
        initViewBindClick();
        initViewData(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initCommonView() {
        layout_title = $(R.id.layout_title);

        layout_title_bar = $(R.id.layout_title_bar);
        tv_left = $(R.id.tv_left);
        if (tv_left != null) {
            $clicks(tv_left, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    left();
                }
            });
        }

        tv_close = $(R.id.tv_close);
        if (tv_close != null) {
            $clicks(tv_close, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    close();
                }
            });
        }

        tv_title = $(R.id.tv_title);
        tv_right = $(R.id.tv_right);
        if (tv_right != null) {
            $clicks(tv_right, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    right();
                }
            });
        }
        toolbar = $(R.id.toolbar);
        v_bottom_line = $(R.id.v_bottom_line);
        error_view_network_error = $(R.id.error_view_network_error);
        error_view_service_error = $(R.id.error_view_service_error);
        bt_error_view_retry = $(R.id.bt_error_view_retry);
        if (bt_error_view_retry != null) {
            $clicks(bt_error_view_retry, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    retry();
                }
            });
        }
    }

    private void initTitleBar() {
        if (toolbar != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        }

        if (layout_title != null) {
            int height = UIUtils.statusBarHeight();
            if (layout_title_bar != null) {
                height += UIUtils.dip2px(50);
            }

            ViewGroup.LayoutParams layoutParams = layout_title.getLayoutParams();
            layoutParams.height = height;
            layout_title.setLayoutParams(layoutParams);
        }

        setLeftBtn(getLeftBtnDrawable(), getLeftBtnString(), getLeftBtnColor(), isLeftBtnVisibility());
        setCloseBtn(getCloseBtnDrawable(), getCloseBtnString(), getCloseBtnColor(), isCloseBtnVisibility());
        setTitle(getTitleBackground(), getTitleText(), getTitleTextColor());
        setRightBtn(getRightBtnDrawable(), getRightBtnString(), getRightBtnColor(), isRightBtnVisibility());
        setBottomLineViewVisibility(isBottomLineViewVisibility());
    }

    protected abstract void initViewBindClick();

    protected abstract void initViewData(Bundle savedInstanceState);

    public Drawable getLeftBtnDrawable() {
        return getResources().getDrawable(R.drawable.back_arrow_selector);
    }

    public String getLeftBtnString() {
        return "";
    }

    public int getLeftBtnColor() {
        return getResources().getColor(R.color.text_333);
    }

    public boolean isLeftBtnVisibility() {
        return true;
    }

    public void setLeftBtnIcon(Drawable icon) {
        if (tv_left != null) {
            tv_left.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
        }
    }

    public void setLeftBtnText(String text) {
        if (tv_left != null) {
            tv_left.setText(text);
        }
    }

    public void setLeftBtnTextColor(int textColor) {
        if (tv_left != null) {
            tv_left.setTextColor(textColor);
        }
    }

    public void setLeftBtnVisibility(boolean isVisibility) {
        if (tv_left != null) {
            tv_left.setVisibility(isVisibility ? View.VISIBLE : View.GONE);
        }
    }

    public void setLeftBtn(Drawable icon, String text, int textColor, boolean isVisibility) {
        setLeftBtnIcon(icon);
        setLeftBtnText(text);
        setLeftBtnTextColor(textColor);
        setLeftBtnVisibility(isVisibility);
    }

    public Drawable getCloseBtnDrawable() {
        return null;
    }

    public String getCloseBtnString() {
        return "关闭";
    }

    public int getCloseBtnColor() {
        return getResources().getColor(R.color.text_333);
    }

    public boolean isCloseBtnVisibility() {
        return false;
    }

    public void setCloseBtnIcon(Drawable icon) {
        if (tv_close != null) {
            tv_close.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
        }
    }

    public void setCloseBtnText(String text) {
        if (tv_close != null) {
            tv_close.setText(text);
        }
    }

    public void setCloseBtnTextColor(int textColor) {
        if (tv_close != null) {
            tv_close.setTextColor(textColor);
        }
    }

    public void setCloseBtnVisibility(boolean isVisibility) {
        if (tv_close != null) {
            tv_close.setVisibility(isVisibility ? View.VISIBLE : View.GONE);
        }
    }

    public void setCloseBtn(Drawable icon, String text, int textColor, boolean isVisibility) {
        setCloseBtnIcon(icon);
        setCloseBtnText(text);
        setCloseBtnTextColor(textColor);
        setCloseBtnVisibility(isVisibility);
    }

    public int getTitleBackground() {
        return Launcher.getInstance().getTitleBgColor();
    }

    public abstract String getTitleText();

    public int getTitleTextColor() {
        return getResources().getColor(R.color.text_333);
    }

    public void setTitleBackground(int backgroundColor) {
        if (layout_title != null && backgroundColor != 0) {
            layout_title.setBackgroundColor(backgroundColor);
        }
    }

    public void setTitleText(String text) {
        if (tv_title != null) {
            tv_title.setText(text);
        }
    }

    public void setTitleTextColor(int textColor) {
        if (tv_title != null) {
            tv_title.setTextColor(textColor);
        }
    }

    public void setTitle(int backgroundColor, String text, int textColor) {
        setTitleBackground(backgroundColor);
        setTitleText(text);
        setTitleTextColor(textColor);
    }

    public Drawable getRightBtnDrawable() {
        return null;
    }

    public String getRightBtnString() {
        return "";
    }

    public int getRightBtnColor() {
        return getResources().getColor(R.color.text_333);
    }

    public boolean isRightBtnVisibility() {
        return false;
    }

    public void setRightBtnIcon(Drawable icon) {
        if (tv_right != null) {
            tv_right.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
        }
    }

    public void setRightBtnText(String text) {
        if (tv_right != null) {
            tv_right.setText(text);
        }
    }

    public void setRightBtnTextColor(int textColor) {
        if (tv_right != null) {
            tv_right.setTextColor(textColor);
        }
    }

    public void setRightBtnVisibility(boolean isVisibility) {
        if (tv_right != null) {
            tv_right.setVisibility(isVisibility ? View.VISIBLE : View.GONE);
        }
    }

    public void setRightBtn(Drawable icon, String text, int textColor, boolean isVisibility) {
        setRightBtnIcon(icon);
        setRightBtnText(text);
        setRightBtnTextColor(textColor);
        setRightBtnVisibility(isVisibility);
    }

    public boolean isBottomLineViewVisibility() {
        return true;
    }

    public void setBottomLineViewVisibility(boolean isVisibility) {
        if (v_bottom_line != null) {
            v_bottom_line.setVisibility(isVisibility ? View.VISIBLE : View.GONE);
        }
    }

    @CallSuper
    @Override
    public void onStart() {
        lifecycleSubject.onNext(FragmentEvent.START);
        super.onStart();
    }

    @CallSuper
    @Override
    public void onResume() {
        super.onResume();
        lifecycleSubject.onNext(FragmentEvent.RESUME);

        UmengTraceHelper.onPageStart(getClass().getName());
    }

    @SuppressLint("CheckResult")
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            Observable.timer(200, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(Long number) {
                            userVisibleHint();
                        }
                    });
        }

        super.setUserVisibleHint(isVisibleToUser);
    }

    protected void userVisibleHint() {
    }

    @CallSuper
    @Override
    public void onPause() {
        lifecycleSubject.onNext(FragmentEvent.PAUSE);
        super.onPause();
        UmengTraceHelper.onPageEnd(getClass().getName());
    }

    @CallSuper
    @Override
    public void onStop() {
        lifecycleSubject.onNext(FragmentEvent.STOP);
        super.onStop();
    }


    @Override
    public void onDestroyView() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY_VIEW);
        dismissLoading();
        super.onDestroyView();
    }

    @CallSuper
    @Override
    public void onDestroy() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY);
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        lifecycleSubject.onNext(FragmentEvent.DETACH);
        super.onDetach();
    }

    public boolean isRoot() {
        return true;
    }

    @Override
    public boolean onBackPressedSupport() {
        if (!isRoot()) {
            pop();
            return true;
        } else {
            return false;
        }
    }

    public void left() {
        if (!isRoot()) {
            pop();
        } else {
            getActivity().finish();
        }
    }

    public void close() {
        if (!isRoot()) {
            pop();
        } else {
            getActivity().finish();
        }
    }

    public void right() {
    }

    public void retry() {
    }

    public <T extends View> T $(int viewID) {
        return (T) getView().findViewById(viewID);
    }

    public void $clicks(int viewID, View.OnClickListener onClickListener) {
        $clicks($(viewID), onClickListener);
    }

    @SuppressLint("CheckResult")
    public <T extends View> void $clicks(final T t, final View.OnClickListener onClickListener) {
        RxView.clicks(t)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {

                    @Override
                    public void accept(Object o) {
                        onClickListener.onClick(t);
                    }
                });
    }

    public <T extends View> void $longClicks(int viewID, final View.OnLongClickListener onLongClickListener) {
        $longClicks($(viewID), onLongClickListener);
    }

    @SuppressLint("CheckResult")
    public <T extends View> void $longClicks(final T t, final View.OnLongClickListener onLongClickListener) {
        RxView.longClicks(t)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {

                    @Override
                    public void accept(Object o) {
                        onLongClickListener.onLongClick(t);
                    }
                });
    }

    public void login2() {
        dbHelper.clearToken();
        ServerClient.getInstance().setServerToken(null);
        Intent loginIntent = Launcher.getInstance().getLoginIntent();

        if (null != loginIntent) {
            startActivity(loginIntent);
        }
//        getActivity().finish();
    }

    public boolean isNotLoginAndJump2() {
        if (!ServerClient.getInstance().getServerToken().isLogined()) {
            login2();
            return true;
        }
        return false;
    }

    @Override
    public void error(Throwable e) {
        if (e instanceof ApiException) {
            int code = ((ApiException) e).getResultCode();
            if (BaseResponse.isLogin(code)) {
                if (!TextUtils.isEmpty(ServerClient.getInstance().getServerToken().getAccess_token())) {
                    toast(R.string.log_in_other_or_expire);
                }
                login2();
            } else {
                toast(e.getMessage());
            }
        } else if (e instanceof SocketTimeoutException) {
            toast(R.string.network_overtime);
        } else {
            toast("出错了.");
        }
    }

    @Override
    public void showLoading() {
        showLoading(R.string.loading);
    }


    @Override
    public void showLoading(int resourceId) {
        showLoading(getResources().getString(resourceId));
    }

    @Override
    public void showLoading(String content) {
        if (!isHidden()) {
            if (null == loadingDialogFragment) {
                loadingDialogFragment = LoadingDialog.newInstance(content);
                if (!loadingDialogFragment.isVisible() && !loadingDialogFragment.isAdded()) {
                    loadingDialogFragment.show(getFragmentManager(), "dialog");
                }
            } else {
                loadingDialogFragment.updateText(content);
            }
        }
    }

    @Override
    public void dismissLoading() {
        if (loadingDialogFragment != null) {
            try {
                loadingDialogFragment.dismissAllowingStateLoss();
                loadingDialogFragment = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void next() {
    }

    @Override
    public void showNetWorkError(int type, String message) {
        if (StringUtils.isNotNullOrEmpty(message)) {
            toast(message);
        }

        if (base_content != null) {
            base_content.setVisibility(View.GONE);
        }
        if (base_error != null) {
            base_error.setVisibility(View.VISIBLE);
        }

        if (error_view_network_error != null) {
            error_view_network_error.setVisibility(ILoadView.TYPE_NETWORK == type ? View.VISIBLE : View.GONE);
        }
        if (error_view_service_error != null) {
            error_view_service_error.setVisibility(ILoadView.TYPE_SERVICE == type ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void showContentView() {
        if (base_content != null) {
            base_content.setVisibility(View.VISIBLE);
        }

        if (base_error != null) {
            base_error.setVisibility(View.GONE);
        }
    }

    public void hideContentView() {
        if (base_content != null) {
            base_content.setVisibility(View.GONE);
        }
    }
}
