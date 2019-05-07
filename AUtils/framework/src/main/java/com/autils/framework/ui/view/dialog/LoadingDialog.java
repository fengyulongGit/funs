package com.autils.framework.ui.view.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.autils.api.utils.StringUtils;
import com.autils.framework.R;

/**
 * Created by fengyulong on 2018/5/12.
 */
public class LoadingDialog extends DialogFragment {
    private static final String TAG_CONTENT = "content";
    private int mNum = 2;

    public static LoadingDialog newInstance(String content) {
        LoadingDialog dialog = new LoadingDialog();
        Bundle bundle = new Bundle();
        bundle.putString(TAG_CONTENT, content);
        dialog.setArguments(bundle);
        return dialog;
    }

    private String content = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            content = bundle.getString(TAG_CONTENT);
        }

        int style = DialogFragment.STYLE_NORMAL;
        int theme = 0;
        switch ((mNum - 1) % 6) {
            case 1: {
                style = DialogFragment.STYLE_NO_TITLE;
                break;
            }
            case 2: {
                style = DialogFragment.STYLE_NO_FRAME;
                break;
            }
            case 3: {
                style = DialogFragment.STYLE_NO_INPUT;
                break;
            }
            case 4: {
                style = DialogFragment.STYLE_NORMAL;
                break;
            }
            case 5: {
                style = DialogFragment.STYLE_NORMAL;
                break;
            }
            case 6: {
                style = DialogFragment.STYLE_NO_TITLE;
                break;
            }
            case 7: {
                style = DialogFragment.STYLE_NO_FRAME;
                break;
            }
            case 8: {
                style = DialogFragment.STYLE_NORMAL;
                break;
            }
        }

        switch ((mNum - 1) % 6) {
            case 4: {
                theme = android.R.style.Theme_Holo;
                break;
            }
            case 5: {
                theme = android.R.style.Theme_Holo_Light_Dialog;
                break;
            }
            case 6: {
                theme = android.R.style.Theme_Holo_Light;
                break;
            }
            case 7: {
                theme = android.R.style.Theme_Holo_Light_Panel;
                break;
            }
            case 8: {
                theme = android.R.style.Theme_Holo_Light;
                break;
            }
        }
        setStyle(style, theme);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (KeyEvent.KEYCODE_BACK == keyCode)
                    return true;
                else
                    return false;

            }
        });
        return inflater.inflate(R.layout.fragment_loading_dialog, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (StringUtils.isNotNullOrEmpty(content)) {
            ((TextView) getView().findViewById(R.id.tv_loading)).setText(content);
        }

        ((ProgressBar) getView().findViewById(R.id.progressbar)).getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.red), android.graphics.PorterDuff.Mode.SRC_IN);
    }

    public void updateText(String content) {
        if (getView() != null && StringUtils.isNotNullOrEmpty(content)) {
            ((TextView) getView().findViewById(R.id.tv_loading)).setText(content);
        }
    }
}
