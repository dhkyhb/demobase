package com.wangdh.demolist.dagger.module;

import dagger.Module;
import dagger.Provides;
//import okhttp3.OkHttpClient;

/**
 * Created by wangdh on 2018/4/18.
 * 对于3方库提供的对象，我们无法去改变他的构造函数的注解
 * 使用Module 可以去解决这种情况
 */

@Module
public class HttpModule {
//    @Provides
//    OkHttpClient provideOkHttpClient() {
//        OkHttpClient okHttpClient = new OkHttpClient();
//        return okHttpClient;
//    }
}
