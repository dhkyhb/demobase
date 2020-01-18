package com.wangdh.utilslibrary.netlibrary.clien;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.widget.Toast;

import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.wangdh.utilslibrary.exception.AppException;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * @author wangdh
 * @time 2019/5/15 14:47
 * @describe 1.通过 onlineConfig 去 初始化 retrofit
 * 2.外部调用getAPI 获取api 得到被观察者
 * 3.调用 connect 进行绑定
 */
public class StandardRXOnline {
    protected OnlineConfig onlineConfig;

    protected OnlineContext onlineContext;

    protected LifecycleOwner lifecycleOwner;

    protected Context context;

    /**
     * 如果一个服务器 有独特的配置 子类应该重写配置
     */
    public void initOnlineConfig() {
        onlineConfig = OnlineConfig.getDefConfig();
    }

    public void initOnlineContext() {
        if (onlineContext == null) {
            onlineContext = new OnlineContext();
        }
    }

    public OnlineConfig getConfig() {
        if (onlineConfig == null) {
            initOnlineConfig();
        }
        return onlineConfig;
    }

    /**
     * 当从外部设置 config 的时候 一定要重新设置API 被观察者
     *
     * @param config
     */
    public void setOnlineConfig(OnlineConfig config) {
        onlineConfig = config;
        onlineConfig.build();
    }

    @SuppressLint("CheckResult")
    public <T> void connect(Observable<T> obs, Observer observer) {
        initOnlineContext();
        onlineContext.setContext(context);
        onlineContext.setOnlineConfig(onlineConfig);
        if (getLifecycleOwner() == null) {
            Observable<T> tObservable = obs.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            tObservable.subscribe(observer);
        } else {
            ObservableSubscribeProxy<T> ss = obs
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    //添加泛型原因，.as返回Object导致无法继续走下去.
                    .as(this.<T>transformer());
            ss.subscribe(observer);
        }
    }

    //获取api
    public <H> H getAPI(Class<H> service) {
        if (onlineConfig == null) {
            initOnlineConfig();
            onlineConfig.build();
        }
        return this.onlineConfig.retrofit.create(service);
    }

//    ————————————————————生命周期事件》》》》》》》》》》》》》》》》》》》》》
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
        if (context == null) {
            return;
        }
        if (context instanceof LifecycleOwner) {
            this.setLifecycleOwner((LifecycleOwner) context);
        }
        this.context = context;
    }

    private <T> AutoDisposeConverter<T> transformer() {
        return autoDisposable(AndroidLifecycleScopeProvider
                .from(getLifecycleOwner(), (getEvent() != null ? getEvent() : Lifecycle.Event.ON_STOP)));
    }
//   《《《《《《《《《《《《《《《《《《《生命周期事件————————————————————

    public void onOnlineStart(){
    }

    public void onOnlineComplete(){
    }

    public void onOnlineError(Throwable e){
    }

}
