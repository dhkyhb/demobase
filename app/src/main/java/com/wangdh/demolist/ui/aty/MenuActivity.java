package com.wangdh.demolist.ui.aty;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;

import com.wangdh.demolist.R;
import com.wangdh.demolist.base.mvp.MvpActivity;
import com.wangdh.demolist.base.recycler.MyLinearLayoutManager;
import com.wangdh.demolist.presenter.MenuPresenter;
import com.wangdh.demolist.presenter.demolist.HttpsDemo;
import com.wangdh.demolist.presenter.demolist.ListViewDemo;
import com.wangdh.demolist.ui.iview.IMenuView;

public class MenuActivity extends MvpActivity<MenuPresenter> implements IMenuView {

    private RecyclerView rv_menu;
    private FrameLayout fl_menu;

    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;

    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;
    public int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        initView();
        try {
            mPersenter.run(ListViewDemo.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected MenuPresenter createPresenter() {
        MenuPresenter menuPresenter = new MenuPresenter();
        menuPresenter.setiView(this);
        return menuPresenter;
    }

    private void initView() {
        rv_menu = (RecyclerView) findViewById(R.id.rv_menu);
        fl_menu = (FrameLayout) findViewById(R.id.fl_menu);
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        LinearLayoutManager linearLayoutManager = new MyLinearLayoutManager(this);
        linearLayoutManager.setOrientation(HORIZONTAL_LIST);
        return linearLayoutManager;
        //.setLayoutManager(new GridLayoutManager(this,4));
        //.setLayoutManager(new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL));
    }

    private FragmentTransaction fragmentTransaction;

    @Override
    public <T extends Fragment> void openFragment(T fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_menu, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void setAdapter(RecyclerView.Adapter adapter) {
        rv_menu.setLayoutManager(getLayoutManager());
        rv_menu.setAdapter(adapter);
    }

    @Override
    public RecyclerView getRecyclerView() {
        return rv_menu;
    }

    @Override
    public Activity getContext() {
        return this;
    }

}
