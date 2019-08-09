package com.wangdh.utilslibrary.netlibrary.clien;


import android.app.Dialog;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.parkingwang.okhttp3.LogInterceptor.LogInterceptor;
import com.wangdh.utilslibrary.netlibrary.server.xiaohua.API_Xiaohua;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author wangdh
 * @time 2019/5/15 14:56
 * @describe 偏向于全局配置的 设置
 */
public class OnlineConfig {
    //是否显示框架内部日志
    public boolean isLog = true;
    public String url = "";
    public int time_out = 10000;

    public boolean isShowWait = false;
    public Dialog waitDialog;

    public boolean isShowError = false;
    public boolean isErrorToast = false;
    public Dialog ErrorDialog;


    public static OnlineConfig getDefConfig() {
        return new OnlineConfig();
    }

    public Retrofit retrofit;

    //按照默认配置初始化 Retrofit
    public void build() {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.connectTimeout(this.time_out, TimeUnit.SECONDS);
        if (this.isLog) {
            okHttpClient.addInterceptor(new LogInterceptor());
        }

        /**
         * 对所有请求添加请求头
         */
//        okHttpClient.addInterceptor(new Interceptor() {
//            @Override
//            public okhttp3.Response intercept(Chain chain) throws IOException {
//                Request request = chain.request();
//                okhttp3.Response originalResponse = chain.proceed(request);
//                return originalResponse.newBuilder().header("key1", "value1").addHeader("key2", "value2").build();
//            }
//        });

        retrofit = new Retrofit.Builder()
                .client(okHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(this.url)
                .build();
    }

}
