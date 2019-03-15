package com.wangdh.demolist.utils.recycler;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntDef;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * RecyclerView 分割线
 * 下拉刷新库 AVLoadingIndicatorView
 * <p>
 * -----------纵向调用
 * <p>
 * mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
 * mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
 * -----------横向调用
 * mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONAL, false);
 * mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONAL))
 * 自定义 分割线颜色 代码最下面查看
 */
public class DividerItemDecoration extends RecyclerView.ItemDecoration {
    @OrientationType
    private int mOrientation = LinearLayoutManager.VERTICAL;
    private Drawable mDivider;

    private int[] attrs = new int[]{
            android.R.attr.listDivider
    };

    public DividerItemDecoration(Context context, @OrientationType int orientation) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs);
        mDivider = typedArray.getDrawable(0);
        typedArray.recycle();
        setOrientation(orientation);
    }

    private void setOrientation(@OrientationType int orientation) {
        if (orientation != LinearLayoutManager.VERTICAL && orientation != LinearLayoutManager.HORIZONTAL) {
            throw new IllegalArgumentException("传入的布局类型不合法");
        }
        this.mOrientation = orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        //调用这个绘制方法，RecyclerView会回调该绘制方法，需要我们自己去绘制条目的间隔线
        if (mOrientation == LinearLayoutManager.VERTICAL) {
            //垂直
            drawVertical(c, parent);
        } else {
            //水平
            drawHorizontal(c, parent);
        }
    }

    private void drawVertical(Canvas c, RecyclerView parent) {
        // 画水平线
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getBottom() + params.bottomMargin + Math.round(ViewCompat.getTranslationY(child));
            int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    private void drawHorizontal(Canvas c, RecyclerView parent) {
        int top = parent.getPaddingTop();
        int bottom = parent.getHeight() - parent.getPaddingBottom();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int left = child.getRight() + params.rightMargin + Math.round(ViewCompat.getTranslationX(child));
            int right = left + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //获得条目的偏移量（所有的条目都会回调一次该方法）
        if (mOrientation == LinearLayoutManager.VERTICAL) {
            //垂直
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
        } else {
            //水平
            outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
        }
    }

    @IntDef({LinearLayoutManager.VERTICAL, LinearLayoutManager.HORIZONTAL})
    public @interface OrientationType {
    }
}

//<style name="AppTheme" parent="Theme.AppCompat.Light.DarkActionBar">
//<!-- Customize your theme here. -->
//<item name="colorPrimary">@color/colorPrimary</item>
//<item name="colorPrimaryDark">@color/colorPrimaryDark</item>
//<item name="colorAccent">@color/colorAccent</item>
//<item name="android:listDivider">@drawable/bg_recyclerview_divider</item>
//</style>

//bg_recyclerview_divider.xml
//<?xml version="1.0" encoding="utf-8"?>
//<shape xmlns:android="http://schemas.android.com/apk/res/android"
//        android:shape="rectangle">
//
//<gradient
//        android:centerColor="#ff00ff00"
//                android:endColor="#ff0000ff"
//                android:startColor="#ffff0000"
//                android:type="linear"/>
//
//<size
//        android:width="10dp"
//                android:height="10dp"/>
//
//</shape>