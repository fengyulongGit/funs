package com.autils.framework.ui.view.password;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.autils.framework.R;

/**
 * describe:  密码输入框的控件布局
 * author: Went_Gone
 * create on: 2016/10/24
 */
public class VerifyCodeLayout extends RelativeLayout {
    private EditText editText;
    private TextView[] textViews;//使用一个数组存储密码框

    public VerifyCodeLayout(Context context) {
        this(context, null);
    }

    public VerifyCodeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerifyCodeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        textViews = new TextView[6];

        View.inflate(context, R.layout.layout_verify_code, this);

        editText = (EditText) findViewById(R.id.item_edittext);
        textViews[0] = (TextView) findViewById(R.id.verify_code_1);
        textViews[1] = (TextView) findViewById(R.id.verify_code_2);
        textViews[2] = (TextView) findViewById(R.id.verify_code_3);
        textViews[3] = (TextView) findViewById(R.id.verify_code_4);
        textViews[4] = (TextView) findViewById(R.id.verify_code_5);
        textViews[5] = (TextView) findViewById(R.id.verify_code_6);

        editText.setCursorVisible(false);//将光标隐藏
        editText.requestFocus();

        setListener();
    }

    private void setListener() {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editText.length() == 6) {
                    //文字长度位6   则调用完成输入的监听
                    if (inputCompleteListener != null) {
                        inputCompleteListener.inputComplete(getStrPassword());
                    }
                }

                for (int i = 0; i < textViews.length; i++) {
                    if (i < editText.getText().length()) {
                        textViews[i].setText(editText.getText().charAt(i) + "");
                    } else {
                        textViews[i].setText("");
                    }
                }
            }
        });
    }


    private InputCompleteListener inputCompleteListener;

    public void setInputCompleteListener(InputCompleteListener inputCompleteListener) {
        this.inputCompleteListener = inputCompleteListener;
    }

    public interface InputCompleteListener {
        void inputComplete(String password);
    }

    public EditText getEditText() {
        return editText;
    }

    /**
     * 获取密码
     *
     * @return
     */
    public String getStrPassword() {
        StringBuilder sb = new StringBuilder(getEditText().getText().toString().trim());
        if (sb.length() > 6) {
            sb.setLength(6);
        }
        return sb.toString();
    }

    public void setContent(String content) {
        editText.setText(content);
    }
}
