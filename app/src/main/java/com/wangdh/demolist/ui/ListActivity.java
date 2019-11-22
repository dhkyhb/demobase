package com.wangdh.demolist.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.wangdh.demolist.R;
import com.wangdh.demolist.base.adapter.CommonAdapter;
import com.wangdh.demolist.base.adapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        setTitle("回款管理");
        list = findViewById(R.id.list);
        List<String> ss = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            ss.add("" + i);
        }
        ListDataAdapter listDataAdapter = new ListDataAdapter(this, ss);
        list.setAdapter(listDataAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListActivity.this.startActivity(new Intent(ListActivity.this, OrderActivity.class));
            }
        });
    }

    public class ListDataAdapter extends CommonAdapter<String> {


        public ListDataAdapter(Context context, List<String> datas) {
            super(context, datas);
        }

        @Override
        protected int getLayoutId() {
            return R.layout.itme_ysj;
        }

        @Override
        public void convert(ViewHolder holder, String s) {

        }
    }
}
