package com.wangdh.utilslibrary.netlibrary.clien;

import android.widget.Toast;

import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.wangdh.utilslibrary.exception.AppException;
import com.wangdh.utilslibrary.ui.dialog.DialogTip;

/**
 * 带有效果的 连接
 */
public class EffectRXOnline extends StandardRXOnline {
    @Override
    public void onOnlineStart() {
        OnlineConfig onlineConfig = getConfig();
        if (onlineConfig.isShowWait && context != null) {
            QMUITipDialog loading = DialogTip.getLoading(context);
            onlineContext.dialog1 = loading;
            loading.show();
        }
    }
    @Override
    public void onOnlineComplete() {
        OnlineConfig onlineConfig = getConfig();
        if (onlineConfig.isShowWait && onlineContext.dialog1 != null) {
            onlineContext.dialog1.dismiss();
        }
    }
    @Override
    public void onOnlineError(Throwable e) {
        onOnlineComplete();
        if (context != null) {
            OnlineConfig onlineConfig = getConfig();
            if (onlineConfig.isShowError) {
                if (e instanceof AppException) {
                    if (onlineConfig.isErrorToast) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }
}
