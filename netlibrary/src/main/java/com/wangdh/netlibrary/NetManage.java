package com.wangdh.netlibrary;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * @author wangdh
 * @time 2019/2/22 13:52
 * @describe
 */
public class NetManage {
    public <T> void conn(Observable<T> observable, DisposableObserver disposableObserver, LifecycleProvider lifecycleProvider) {

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(lifecycleProvider.bindUntilEvent(ActivityEvent.PAUSE))
                .subscribe(disposableObserver);
    }
}
