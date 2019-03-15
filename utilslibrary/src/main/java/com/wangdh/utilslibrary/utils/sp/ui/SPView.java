package com.wangdh.utilslibrary.utils.sp.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.wangdh.utilslibrary.utils.logger.TLog;
import com.wangdh.utilslibrary.utils.sp.SP;
import com.wangdh.utilslibrary.utils.sp.SPFactory;

/**
 * Created by wangdh on 2017/10/21.
 * name：
 * 描述：
 */

public class SPView extends LinearLayout implements SPViewOnListener {
    private Context context;

    public SPView(Context context) {
        super(context);
        this.context = context;
    }

    public SPView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public SPView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    private String[] selectStr = null;
    private boolean isShow = true;
    private boolean change = true;
    private String hint = "";
    private Object value = null;
    private String pattern = "";
    private String error = "";
    private String key = "";
    private int lenght = 0;//edit 长度
    private int inputType = -1;//输入类型

    private boolean isBoolean = false;

    private int typeUI = 0;//0: kv  1:boolean 2:boolean

    public Object getValue() {
        try {
            switch (typeUI) {
                case 0:
                    return editText.getText().toString().trim();
                case 1:
                    return toggleButton.isChecked();
                case 2:
                    return selectText.getText().toString().trim();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean save() {
        if (isShow && change) {
            Object value = getValue();
            if (value != null) {
                if (SPFactory.match(pattern, value)) {
                    boolean set = SPFactory.set(key, value);
                    if (!set) {
                        TLog.p("保存失败：" + key);
                    }
                    return set;
                } else {
                    if (editText != null && typeUI == 0) {
                        editText.setText("");
                        editText.setHint(error);
                    }
//                    Toast.makeText(context,error,Toast.LENGTH_SHORT).show();
                }

            }
            TLog.p("保存失败：" + key);
            return false;
        } else {
            return true;
        }
    }

    public SPView createBySP(String key) {
        this.key = key;
        SP anno = null;
        try {
            anno = SPFactory.getAnno(key);
        } catch (Exception e) {
            e.printStackTrace();
            return this;
        }
        isShow = anno.isShow();
        if (!isShow) {
            return null;
        }
        selectStr = anno.selectKey();
        pattern = anno.pattern();
        hint = anno.hint();

        change = anno.change();
        error = anno.error();
        lenght = anno.lenght();
        inputType = anno.inputType();

        if (selectStr != null && selectStr.length > 0) {
            String[] strings = anno.selectValue();
            if (strings != null && strings.length > 0) {
                selectStr = strings;
            }
            value = SPFactory.get(key, String.class);
            this.addView(initArray());
            return this;
        }
        Class<?> type = anno.type();
        if (type.equals(boolean.class) || type.equals(Boolean.class)) {
            value = SPFactory.get(key, boolean.class);
            this.addView(initBoolean());
            return this;
        }
        value = SPFactory.get(key, String.class);
        this.addView(initString());
        return this;
    }


    private int testsize = 16;
    private static int anInt = 0;

    private int getBackColor() {
        if (anInt == 0) {
            anInt = 1;
//            return Color.GRAY;
            return Color.parseColor("#C0C0C0");
        } else {
            anInt = 0;
            return Color.TRANSPARENT;
        }

    }

    private TextView getText(String msg) {
        TextView textView = new TextView(context);
        textView.setTextSize(testsize);
        textView.setTextColor(Color.BLACK);
        textView.setText(msg);
        return textView;
    }

    private TextView selectText = null;

    private TextView getRightText(String msg) {
        selectText = new TextView(context);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
//        layoutParams.setMargins(0, 20, 40, 20);
        selectText.setLayoutParams(layoutParams);
        selectText.setTextSize(testsize);
        selectText.setPadding(0, 15, 70, 15);
        selectText.setBackgroundColor(Color.TRANSPARENT);
        selectText.setTextColor(Color.BLACK);
        selectText.setGravity(Gravity.RIGHT);
        selectText.setText(msg);
        return selectText;
    }

    private EditText editText = null;

    private EditText getEdit(String msg) {
        editText = new EditText(context);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 30, 0);
        editText.setLayoutParams(layoutParams);
        editText.setTextSize(testsize);
        editText.setBackgroundColor(Color.TRANSPARENT);
        editText.setTextColor(Color.BLACK);
        editText.setGravity(Gravity.RIGHT);
        if (lenght > 0) {
            editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(lenght)});
        }
        if (inputType >= 0) {
            editText.setInputType(inputType);
        }
        editText.setText(msg);
        if (!change) {
            editText.setEnabled(false);
        }
        return editText;
    }

    private ToggleButton toggleButton = null;

    private View getTogglebtn(boolean b) {
        LinearLayout linearLayout = new LinearLayout(context);
        LayoutParams layoutParams0 = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(layoutParams0);
        linearLayout.setGravity(Gravity.RIGHT);

        toggleButton = new ToggleButton(context);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        toggleButton.setLayoutParams(layoutParams);
//        toggleButton.setButtonDrawable(R.drawable.checkbox_iphone);
        toggleButton.setChecked(b);
        toggleButton.setText("");
        toggleButton.setTextOff("");
        toggleButton.setTextOn("");
        toggleButton.setGravity(Gravity.CENTER);
        toggleButton.setBackgroundColor(Color.TRANSPARENT);
        if (!change) {
            toggleButton.setEnabled(false);
        }
        linearLayout.addView(toggleButton);
        return linearLayout;
    }

    private int left = 0;
    private int top = 0;
    private int right = 0;
    private int bottom = 0;

    public void setMargins(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    private int color = -1;

    public void setColor(int color) {
        this.color = color;
    }

    private LinearLayout getLayout() {
        LinearLayout linearLayout = new LinearLayout(context);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(left, top, right, bottom);
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setGravity(Gravity.CENTER_VERTICAL);
        linearLayout.setPadding(20, 5, 30, 5);
        if (color == -1) {
            linearLayout.setBackgroundColor(getBackColor());
        } else {
            linearLayout.setBackgroundColor(color);
        }
        return linearLayout;
    }

    public View initString() {
        typeUI = 0;
        Log.e("----", "typeUI:" + typeUI);
        LinearLayout linearLayout = getLayout();
        linearLayout.addView(getText(hint));
        linearLayout.addView(getEdit((String) value));
        return linearLayout;
    }

    public View initBoolean() {
        typeUI = 1;
        Log.e("----", "typeUI:" + typeUI);
        isBoolean = true;
        LinearLayout linearLayout = getLayout();
        linearLayout.addView(getText(hint));
        linearLayout.addView(getTogglebtn((boolean) value));
        return linearLayout;
    }

    public View initArray() {
        typeUI = 2;
        Log.e("----", "typeUI:" + typeUI);
        LinearLayout linearLayout = getLayout();
        linearLayout.addView(getText(hint));
        final TextView text = getRightText((String) value);
        linearLayout.addView(text);
        if (change) {
            text.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog dialog = new AlertDialog.Builder(context).setTitle("")
                            .setItems(selectStr, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    text.setText(selectStr[which]);
                                    if (onClickListener != null) {
                                        onClickListener.onClick(which, selectStr[which]);
                                    }
                                }
                            }).create();
                    dialog.show();
                }
            });
        }
        return linearLayout;
    }

    public void setAlertDialogonClickListener(AlertDialogonClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public AlertDialogonClickListener onClickListener = null;

    public interface AlertDialogonClickListener {
        void onClick(int which, String msg);
    }

}
