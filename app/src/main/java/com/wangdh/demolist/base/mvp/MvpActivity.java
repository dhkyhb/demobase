package com.wangdh.demolist.base.mvp;

import android.os.Bundle;

import com.wangdh.demolist.base.BaseActivity;

/**
 * Created by wangdh on 2016/11/14.
 * <p>
 * 这个的作用应该是实例化 persenter
 */

public abstract class MvpActivity<P extends BasePresenter> extends BaseActivity {
    protected P mPersenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mPersenter = createPresenter();
        super.onCreate(savedInstanceState);
    }

    protected abstract P createPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (mPersenter != null) {
//            mPersenter.detachView();
//        }
    }
}
