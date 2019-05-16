package com.wangdh.netlibrary.clien;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.parkingwang.okhttp3.LogInterceptor.LogInterceptor;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.wangdh.utilslibrary.utils.logger.TLog;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author wangdh
 * @time 2019/5/15 14:47
 * @describe 1.通过 onlineConfig 去 初始化 retrofit
 * 2.外部调用getAPI 获取api 得到被观察者
 * 3.调用 connect 进行绑定
 */
public class StandardRXOnline {

    private OnlineConfig onlineConfig;

    protected Retrofit retrofit;

    protected String url;

    protected OnlineContext onlineContext;

    protected LifecycleProvider lifecycleProvider;

    protected LifecycleTransformer lifecycleTransformer;

    protected Context context;

    //获得联机配置属性
    public OnlineConfig getOnlineConfig() {
        if (onlineConfig == null) {
            onlineConfig = OnlineConfig.getDefConfig();
        }
        return onlineConfig;
    }

    public StandardRXOnline() {
        init();
    }

    private void init() {
        getOnlineConfig();
        onlineContext = new OnlineContext();
        initDefRetrofit();
    }

    public void connect(Observable obs, Observer observer) {
        onlineContext.setContext(context);
        onlineContext.setOnlineConfig(onlineConfig);
        onlineContext.setUrl(url);
        Observable observable;
        if (getLifecycleProvider() != null && getEvent()!=null) {
            TLog.e("执行生命周期监听");
            LifecycleTransformer lifecycleTransformer = getLifecycleProvider().bindUntilEvent(getEvent());
            observable = obs.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(lifecycleTransformer);
        } else {
            observable = obs.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
        observable.subscribe(observer);

    }

    //按照默认配置初始化 Retrofit
    public void initDefRetrofit() {
        if (retrofit != null) {
            return;
        }
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.connectTimeout(onlineConfig.time_out, TimeUnit.SECONDS);
        if (getOnlineConfig().isLog) {
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


        if (TextUtils.isEmpty(url)) {
            url = onlineConfig.url;
        }
        retrofit = new Retrofit.Builder()
                .client(okHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(url)
                .build();
    }

    //获取api
    public <H> H getAPI(Class<H> service) {
        return retrofit.create(service);
    }

    public LifecycleProvider getLifecycleProvider() {
        return lifecycleProvider;
    }

    public void setLifecycleProvider(LifecycleProvider lifecycleProvider) {
        this.lifecycleProvider = lifecycleProvider;
    }

    // 设置msg 关闭事件
    //ActivityEvent.STOP
    private Enum event = ActivityEvent.STOP;

    public void setCloseEvent(Enum msg) {
        this.event = msg;
    }

    public Enum getEvent() {
        return this.event;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        if (context instanceof LifecycleProvider) {
            this.setLifecycleProvider((LifecycleProvider) context);
        }
        this.context = context;
    }

    // 设置联机 场景  。 activity、fragment、dialog、service 等 目前就写2个
//    private Activity currentAty;
//    private Fragment currentFra;
//
//    public void setCurrentAty(Activity aty) {
//        if (aty instanceof LifecycleProvider) {
//            this.setLifecycleProvider((LifecycleProvider) context);
//        }
//        this.setContext(aty);
//        this.currentAty = aty;
//    }
//
//    public void setCurrentFra(Fragment fra) {
//        if (fra instanceof LifecycleProvider) {
//            this.setLifecycleProvider((LifecycleProvider) context);
//        }
//        this.setContext(fra.getActivity());
//        this.currentFra = fra;
//    }
}
