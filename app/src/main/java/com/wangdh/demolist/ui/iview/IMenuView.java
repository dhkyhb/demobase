package com.wangdh.demolist.ui.iview;

import android.app.Activity;
import android.app.Fragment;
import android.support.v7.widget.RecyclerView;

/**
 * Created by wangdh on 2016/11/18.
 * name：
 * 描述：
 */

public interface IMenuView {
    <T extends Fragment> void openFragment(T fragment);

    void setAdapter(RecyclerView.Adapter adapter);

    RecyclerView getRecyclerView();

    Activity getContext();
}
