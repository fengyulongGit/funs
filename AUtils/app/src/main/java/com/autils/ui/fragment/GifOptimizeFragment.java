package com.autils.ui.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.autils.R;
import com.autils.common.utils.GifEncoder.AnimatedGifEncoder;
import com.autils.common.utils.GifEncoder.GifDecoder;
import com.autils.framework.common.log.L;
import com.autils.framework.common.utils.BitmapUtils;
import com.autils.framework.common.utils.FileUtils;
import com.autils.framework.common.utils.PathUtils;
import com.autils.framework.common.utils.UIUtils;
import com.autils.framework.ui.base.BaseFragment;
import com.autils.framework.ui.view.fragmentation.SupportFragment;
import com.autils.framework.ui.view.imageselector.MultiImageSelector;
import com.bumptech.glide.Glide;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class GifOptimizeFragment extends BaseFragment {
    private TextView text_origin;
    private ImageView image_origin;
    private TextView text_desc;
    private ImageView image_desc;

    @Override
    protected int layoutContentResID() {
        return R.layout.fragment_gif_optimize;
    }

    @Override
    protected void initViewBindClick() {
        text_origin = $(R.id.text_origin);
        image_origin = $(R.id.image_origin);
        text_desc = $(R.id.text_desc);
        image_desc = $(R.id.image_desc);
    }

    @Override
    protected void initViewData(Bundle savedInstanceState) {

    }

    @SuppressLint("CheckResult")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        L.d(requestCode + " resultCode " + resultCode);

        if (REQUEST_CODE_IMAGE == requestCode) {
            if (SupportFragment.RESULT_OK != resultCode) {
                return;
            }

            ArrayList<String> selectPath = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
            if (selectPath == null || selectPath.isEmpty()) {
                return;
            }

            String picPath = selectPath.get(0);
            File imgFile = new File(picPath);
            if (!imgFile.exists()) {
                return;
            }

            text_origin.setText("原件大小： " + FileUtils.formatFileSize(imgFile.length()));
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(picPath, options);
            int width = options.outWidth;
            int height = options.outHeight;

            ViewGroup.LayoutParams layoutParams = image_origin.getLayoutParams();
            layoutParams.width = width;
            layoutParams.height = height;
            image_origin.setLayoutParams(layoutParams);

            Glide.with(this).load(imgFile).into(image_origin);

            Observable.just(picPath)
                    .throttleFirst(300, TimeUnit.MILLISECONDS)
                    .compose(GifOptimizeFragment.this.<String>bindUntilEvent(FragmentEvent.DESTROY))
                    .subscribeOn(Schedulers.io())
                    .map(new Function<String, String>() {
                        @Override
                        public String apply(String flag) {
                            return optimize(flag);
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
                    .subscribe(new Consumer<String>() {
                        @Override
                        public void accept(String flag) {
                            toast("完成");

                            text_desc.setText("结果大小： " + FileUtils.formatFileSize(new File(flag).length()));

                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inJustDecodeBounds = true;
                            BitmapFactory.decodeFile(flag, options);
                            int width = options.outWidth;
                            int height = options.outHeight;

                            ViewGroup.LayoutParams layoutParams = image_desc.getLayoutParams();
                            layoutParams.width = width;
                            layoutParams.height = height;
                            image_desc.setLayoutParams(layoutParams);

                            Glide.with(GifOptimizeFragment.this).load(new File(flag)).into(image_desc);
                        }
                    });
        }
    }

    private String optimize(String picPath) {
        String descPath = PathUtils.CacheFilePath(System.currentTimeMillis() + ".gif");
        try {

            AnimatedGifEncoder encoder = new AnimatedGifEncoder();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            encoder.start(baos);

            GifDecoder decoder = GifDecoder.decode(picPath);

            encoder.setRepeat(0);//设置生成gif的开始播放时间。0为立即开始播放
            encoder.setDelay(decoder.getDelay(0));

            for (int i = 0; i < decoder.getFrameCount(); i++) {
                encoder.addFrame(BitmapUtils.zoom(decoder.getFrame(i), 270, 270), false);
            }
//            encoder.setSize(decoder.getBitmap().getWidth() / 10, decoder.getBitmap().getHeight() / 10);
            encoder.finish();

            FileOutputStream fos = new FileOutputStream(descPath);
            baos.writeTo(fos);
            baos.flush();
            fos.flush();
            baos.close();
            fos.close();

            return descPath;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public String getTitleText() {
        return "GIF optimize";
    }

    @Override
    public String getRightBtnString() {
        return "选择图片";
    }

    @Override
    public boolean isRightBtnVisibility() {
        return true;
    }

    private final int REQUEST_CODE_IMAGE = 1000;

    @SuppressLint("CheckResult")
    @Override
    public void right() {
        new RxPermissions(getActivity()).request(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        ).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean granted) throws Exception {
                if (granted) {
                    MultiImageSelector.create()
                            .showCamera(true)
                            .single()
                            .start(GifOptimizeFragment.this, REQUEST_CODE_IMAGE);
                } else {
                    UIUtils.showOpenPermissionDialog(context, getResources().getString(com.autils.framework.R.string.permission_storage) + "," + getResources().getString(com.autils.framework.R.string.permission_camera));
                }
            }
        });
    }

    public static BaseFragment newInstance() {
        Bundle bundle = new Bundle();

        BaseFragment fragment = new GifOptimizeFragment();
        fragment.setArguments(bundle);

        return fragment;
    }
}
