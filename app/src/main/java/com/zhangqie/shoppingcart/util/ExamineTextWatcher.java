package com.zhangqie.shoppingcart.util;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

/**
* 用户输入验证器
* <p/>
* Created by zhuwentao on 2016-08-04.
*/
public class ExamineTextWatcher implements TextWatcher {
    private static final String TAG = "ExamineTextWatcher";
 
    /**
     * 帐号
     */
    public static final int TYPE_ACCOUNT = 1;
 
    /**
     * 金额
     */
    public static final int TYPE_MONEY = 2;
 
    /**
     * 输入框
     */
    private EditText mEditText;
 
    /**
     * 验证类型
     */
    private int examineType;
 
    /**
     * 输入前的文本内容
     */
    private String beforeText;
 
    /**
     * 构造器
     *
     * @param type     验证类型
     * @param editText 输入框
     */
    public ExamineTextWatcher(int type, EditText editText) {
        this.examineType = type;
        this.mEditText = editText;
    }
 
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // 输入前的字符
        beforeText = s.toString();
        Log.d(TAG, "beforeText =>>>" + beforeText );
    }
 
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // 输入后的字符
        String afterText = s.toString();
        Log.d(TAG, "afterText =>>>" + afterText);
 
        boolean isValid = true;
        if (!TextUtils.isEmpty(afterText)) {
            switch (examineType) {
                case TYPE_ACCOUNT:
                    isValid = ValidateUtil.isAccount(afterText);
                    break;
                case TYPE_MONEY:
                    isValid = ValidateUtil.isMoney(afterText);
                    break;
            }
            if (!isValid) {
                // 用户现在输入的字符数减去之前输入的字符数，等于新增的字符数
                int differ = afterText.length() - beforeText.length();
                // 如果用户的输入不符合规范，则显示之前输入的文本
                mEditText.setText(beforeText);
                // 光标移动到文本末尾
                mEditText.setSelection(afterText.length() - differ);
            }
        }
    }
 
    @Override
    public void afterTextChanged(Editable s) {
 
    }
}