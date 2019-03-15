/*
 * Copyright (c) 2016.
 * 南京码动通信科技版权所有
 * 开发人员：马腾飞
 *
 */

package com.wangdh.demolist.base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class CommonAdapter<T> extends BaseAdapter {
    public int postion;

    protected Context mContext;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    private int layoutId;

    public CommonAdapter(Context context, List<T> datas) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        if (datas == null) {
            datas = new ArrayList<>();
        }
        this.mDatas = datas;
    }

    protected abstract int getLayoutId();

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        this.postion = position;
        this.layoutId = getLayoutId();
        ViewHolder holder = ViewHolder.get(mContext, convertView, parent,
                layoutId, position);
        convert(holder, getItem(position));
        return holder.getConvertView();
    }

    public void setDatas(List<T> datas) {
        if (mDatas == null) {
            mDatas = new ArrayList<T>();
        }
        mDatas.clear();
        mDatas.addAll(datas);
        this.notifyDataSetChanged();
    }

    public List<T> getDatas() {
        return this.mDatas;
    }

    public abstract void convert(ViewHolder holder, T t);

}
