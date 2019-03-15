package com.wangdh.demolist.presenter.demolist;

import android.app.Fragment;
import android.os.Bundle;

import com.wangdh.demolist.ui.iview.IMenuView;

/**
 * Created by wangdh on 2016/11/22.
 * name：
 * 描述：
 */

public abstract class BaseMenuPresenter {
    protected IMenuView iMenuView;

    public IMenuView getiMenuView() {
        return iMenuView;
    }

    public void setiMenuView(IMenuView iMenuView) {
        this.iMenuView = iMenuView;
    }

    public void setShowData(Fragment fragment, String msg) {
        Bundle arguments = fragment.getArguments();
        arguments.putString("showData", msg);
    }
}
