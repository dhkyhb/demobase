package com.wangdh.utilslibrary.ui.dialog;

import android.content.Context;

import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

/**
 * @author wangdh
 * @time 2019/8/6 14:39
 * @describe
 */
public class DialogTip {
    public static QMUITipDialog getLoading(Context context) {
        QMUITipDialog dialog = new QMUITipDialog.Builder(context)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("正在加载")
                .create();
        return dialog;
    }

    public static QMUITipDialog getSuccess(Context context) {
        QMUITipDialog dialog = new QMUITipDialog.Builder(context)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS)
                .setTipWord("发送成功")
                .create();
        return dialog;
    }

    public static QMUITipDialog getFail(Context context) {
        QMUITipDialog dialog = new QMUITipDialog.Builder(context)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_FAIL)
                .setTipWord("发送失败")
                .create();
        return dialog;
    }

    public static QMUITipDialog getHint(Context context, String msg) {
        QMUITipDialog dialog = new QMUITipDialog.Builder(context)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_INFO)
                .setTipWord(msg)
                .create();
        return dialog;
    }

    public static QMUITipDialog getText(Context context, String msg) {
        QMUITipDialog dialog = new QMUITipDialog.Builder(context)
                .setTipWord(msg)
                .create();
        return dialog;
    }
//    public static QMUITipDialog getLayout(Context context){
//        QMUITipDialog dialog = new QMUITipDialog.CustomBuilder(context)
//                .setContent(R.layout.qmui_tip_dialog_layout)
//                .create();
//        return dialog;
//    }
}
