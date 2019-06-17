package com.wangdh.netlibrary.clien;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.parkingwang.okhttp3.LogInterceptor.LogInterceptor;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;

import com.uber.autodispose.CompletableSubscribeProxy;
import com.uber.autodispose.FlowableSubscribeProxy;
import com.uber.autodispose.MaybeSubscribeProxy;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.ParallelFlowableSubscribeProxy;
import com.uber.autodispose.SingleSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.wangdh.utilslibrary.utils.logger.TLog;

import java.util.concurrent.TimeUnit;

import io.reactivex.ObservableConverter;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Function;
import io.reactivex.parallel.ParallelFlowable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.uber.autodispose.AutoDispose.autoDisposable;
import static com.wangdh.netlibrary.server.weather.API_Weather.WEATHER_URL;

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

    protected LifecycleOwner lifecycleOwner;

    //装个逼？
    @Deprecated
    protected LifecycleTransformer lifecycleTransformer;

    protected Context context;

    //获得联机配置属性
    public OnlineConfig getOnlineConfig() {
        if (onlineConfig == null) {
            onlineConfig = OnlineConfig.getDefConfig();
        }
        return onlineConfig;
    }

    public void setUrl(String Url) {
        this.onlineConfig.url = Url;
        this.url = Url;
    }

    public StandardRXOnline() {
        init();
    }

    private void init() {
        getOnlineConfig();
        onlineContext = new OnlineContext();
        initDefRetrofit();
    }

    @SuppressLint("CheckResult")
    public <T> void connect(Observable<T> obs, Observer observer) {
        onlineContext.setContext(context);
        onlineContext.setOnlineConfig(onlineConfig);
        onlineContext.setUrl(url);
        TLog.e("执行生命周期监听");
        obs
                //测试取消订阅
//                .repeat()
                .doOnDispose(new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.e(StandardRXOnline.class.getSimpleName(), "The Observable onDispose()");
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //添加泛型原因，.as返回Object导致无法继续走下去.
                .as(this.<T>transformer())
                .subscribe(observer);

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

    public LifecycleOwner getLifecycleOwner() {
        return lifecycleOwner;
    }

    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner;
    }

    // 设置msg 关闭事件
    //ActivityEvent.STOP
    private Lifecycle.Event event = Lifecycle.Event.ON_STOP;

    public void setCloseEvent(Lifecycle.Event msg) {
        this.event = msg;
    }

    public Lifecycle.Event getEvent() {
        return this.event;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.setLifecycleOwner((LifecycleOwner) context);
        this.context = context;
    }

    private <T> AutoDisposeConverter<T>  transformer() {
        return autoDisposable(AndroidLifecycleScopeProvider
                .from(getLifecycleOwner(), (getEvent() != null ? getEvent() : Lifecycle.Event.ON_STOP)));
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
