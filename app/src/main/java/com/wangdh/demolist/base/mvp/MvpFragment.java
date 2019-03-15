package com.wangdh.demolist.base.mvp;


import android.os.Bundle;
import android.view.View;

import com.wangdh.demolist.base.BaseFragment;


public abstract class MvpFragment<P extends BasePresenter> extends BaseFragment {
    protected P mPresenter;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = createPresenter();
    }

    protected abstract P createPresenter();


    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        if (mPresenter != null) {
//            mPresenter.detachView();
//        }
    }
}
