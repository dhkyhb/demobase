package com.wangdh.demolist.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wangdh.demolist.R;
import com.wangdh.demolist.ui.iview.IShowView;

/**
 * Created by wangdh on 2016/11/16.
 * name：
 * 描述：
 */

public class TextFragment extends Fragment implements IShowView {
    private TextView fragment_text;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_text, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fragment_text = (TextView) getActivity().findViewById(R.id.fragment_text);
        Bundle arguments = getArguments();
        String text = arguments.getString("showData");
        setFragment_text(text);
    }

    private void setFragment_text(String msg) {
        if (TextUtils.isEmpty(msg)) {
            fragment_text.setText("");
            return;
        }
        fragment_text.setText(msg);
    }

    @Override
    public void excute(Bundle bundle) {
        String showData = bundle.getString("showData");
        setFragment_text(showData);
    }
}
