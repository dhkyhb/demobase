package com.wangdh.demolist.ui.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;

import java.util.Calendar;

/**
 * Created by wangdh on 2018/1/17.
 * name：
 * 描述：
 */

public class DialogHelper {
    public static AlertDialog.Builder getDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        return builder;
    }

    public static AlertDialog.Builder getSelectDialog(Context context, String title, String[] arrays, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = getDialog(context);
        builder.setItems(arrays, onClickListener);
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        builder.setPositiveButton("取消", null);
        return builder;
    }

    public static ProgressDialog wait(final Activity context, String msg) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(msg);
        progressDialog.show();
        return progressDialog;
    }

    public static void rili(Context context, DatePickerDialog.OnDateSetListener listener) {
        Calendar calendar = Calendar.getInstance();
        int i = 0;
        DatePickerDialog eDialog = new DatePickerDialog(context, i,
                listener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        eDialog.show();
//        eDialog.setTitle("终止时间");
//        ((ViewGroup) ((ViewGroup) (sDialog.getDatePicker().getChildAt(0))).getChildAt(0)).getChildAt(0).setVisibility(View.GONE);
    }
}
