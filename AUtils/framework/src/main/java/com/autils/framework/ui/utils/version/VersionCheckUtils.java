package com.autils.framework.ui.utils.version;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.autils.api.ServerClient;
import com.autils.api.exception.ApiException;
import com.autils.api.response.BaseResponse;
import com.autils.api.response.model.AppVersion;
import com.autils.framework.Launcher;
import com.autils.framework.R;
import com.autils.framework.common.log.L;
import com.autils.framework.common.utils.AppManager;
import com.autils.framework.common.utils.AppUtils;
import com.autils.framework.common.utils.FileUtils;
import com.autils.framework.common.utils.PathUtils;
import com.autils.framework.common.utils.StringUtils;
import com.autils.framework.common.utils.UIUtils;
import com.autils.framework.ui.base.BaseFragment;
import com.autils.framework.ui.utils.Toast;
import com.autils.framework.ui.view.dialog.ConfirmDialog;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.net.SocketTimeoutException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by fengyulong on 2018/5/14.
 */
public class VersionCheckUtils {
    private static VersionCheckUtils instance;

    private Disposable disposable;

    private boolean isToast = false;

    private VersionCheckUtils() {
    }

    public static VersionCheckUtils getInstance() {
        if (instance == null) {
            synchronized (VersionCheckUtils.class) {
                if (instance == null) {
                    instance = new VersionCheckUtils();
                }
            }
        }
        return instance;
    }

    public void checkVersion(BaseFragment fragment) {
        checkVersion(fragment, false);
    }

    @SuppressLint("CheckResult")
    public void checkVersion(final BaseFragment fragment, final boolean isTips) {
        String APKFilePath = PathUtils.APKFilePath(AppUtils.getVersionName(Launcher.getInstance().getApplication()));
        if (FileUtils.isExist(APKFilePath)) {
            new File(APKFilePath).delete();
        }
        unsubscribe();

        if (!Launcher.getInstance().isOfficial()) {
            return;
        }

        if (fragment.getClass().getSimpleName().contains("SplashFragment")) {
            return;
        }

        disposable = ServerClient.getInstance().dataSource()
                .version(AppUtils.getVersionName(Launcher.getInstance().getApplication()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showProgressDialog(fragment, isTips, "");
                    }
                })
                .doOnTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        closeProgressDialog(fragment, isTips);
                    }
                })
                .subscribeWith(new DisposableObserver<AppVersion>() {

                    @Override
                    public void onNext(AppVersion appVersionResponse) {
                        checkUp(fragment, isTips, appVersionResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        showError(fragment, isTips, e);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }


    private void unsubscribe() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }


    private void showError(BaseFragment fragment, boolean isTips, Throwable e) {
        if (e instanceof ApiException) {
            int code = ((ApiException) e).getResultCode();
            if (BaseResponse.isSuccess(code)) {
                checkUp(fragment, isTips, null);
                return;
            } else if (BaseResponse.isLogin(code)) {
                if (isTips) {
                    if (!TextUtils.isEmpty(ServerClient.getInstance().getServerToken().getAccess_token())) {
                        Toast.toast(fragment.getActivity(), R.string.log_in_other_or_expire);
                    }
                }
            } else {
                if (isTips) {
                    Toast.toast(fragment.getActivity(), e.getMessage());
                }
            }
        } else if (e instanceof SocketTimeoutException) {
            if (isTips) {
                Toast.toast(fragment.getActivity(), R.string.network_overtime);
            }
        } else {
            if (isTips) {
                Toast.toast(fragment.getActivity(), "已经是最新版本了.");
            }
        }

        L.e(e);
    }

    @SuppressLint("CheckResult")
    private void checkUp(final BaseFragment fragment, boolean isTips, final AppVersion result) {

        if (result == null || StringUtils.isNullOrEmpty(result.getVersion())) {
            showDialog(fragment, isTips, "当前已经是最新版本");
            return;
        }

        String curVersion = AppUtils.getVersionName(Launcher.getInstance().getApplication());
        String version = result.getVersion();
        String lastVersion = result.getLast_version();

        String content = "";
        final String url = Launcher.getInstance().officialUrl();

        final int updateType;
        if (StringUtils.compareTo4Number(version, curVersion) > 0) {
            if (!"0".equals(result.getForce_update()) && StringUtils.compareTo4Number(lastVersion, curVersion) >= 0) {
                updateType = AppInstallUtils.COMPEL;
                content = fragment.getResources().getString(R.string.alert_dialog_message_force_update);
            } else {
                updateType = AppInstallUtils.SILENCE;
                content = fragment.getResources().getString(R.string.alert_dialog_message_update);
            }

            EventBus.getDefault().post(new VersionEvent(VersionEvent.Event.update));
        } else {
            updateType = AppInstallUtils.UN_UPDATE;
            content = fragment.getResources().getString(R.string.alert_dialog_message_un_update);
        }

        switch (updateType) {
            case AppInstallUtils.UN_UPDATE: {
                showDialog(fragment, isTips, content);
                return;
            }
            case AppInstallUtils.SILENCE: {
                if (isToast && !isTips) {
                    return;
                }
            }
            case AppInstallUtils.COMPEL: {
                ConfirmDialog.Builder builder = new ConfirmDialog.Builder(fragment.getActivity())
                        .setMessage(content);
                if (AppInstallUtils.COMPEL != updateType) {
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            isToast = true;
                        }
                    }, fragment.getResources().getColor(R.color.text_333));
                }
                builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {
                    @SuppressLint("CheckResult")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        new RxPermissions(fragment.getActivity()).request(
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ).subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean granted) {
                                if (granted) {
                                    Intent intent = new Intent();
                                    intent.setAction("android.intent.action.VIEW");
                                    intent.setData(Uri.parse(url));
                                    fragment.getActivity().startActivity(intent);

//                                            String APKFilePath = PathUtils.APKFilePath(result.getVersion());
//                                            if (FileUtils.isExist(APKFilePath)) {
//                                                AppInstallUtils.installApk(fragment.getActivity(), APKFilePath, result.getVersion());
//                                            } else {
//                                                AppInstallUtils.downloadApk(fragment.getActivity(), fragment.getResources().getString(R.string.app_name), url, result.getVersion(), APKFilePath);
//
//                                                Toast.toastCenter(fragment.getActivity(), "开始下载...");
//                                            }
                                } else {
                                    isToast = false;
                                    UIUtils.showOpenPermissionDialog(fragment.getActivity(), fragment.getResources().getString(R.string.permission_storage));
                                }
                            }
                        });
                    }
                })
                        .setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                if (AppInstallUtils.COMPEL == updateType) {
                                    AppManager.getAppManager().finishAllActivity(true);
                                }
                            }
                        })
                        .create().show();
            }
        }
    }

    private void showDialog(BaseFragment fragment, boolean isTips, String message) {
        if (!isTips) {
            return;
        }

        new ConfirmDialog.Builder(fragment.getActivity())
                .setMessage(message)
                .setNegativeButton("确定", null)
                .create().show();
    }

    private void showProgressDialog(BaseFragment fragment, boolean isTips, String content) {
        if (!isTips) {
            return;
        }

        fragment.showLoading(content);
    }

    private void closeProgressDialog(BaseFragment fragment, boolean isTips) {
        if (!isTips) {
            return;
        }

        fragment.dismissLoading();
    }
}
