package com.wangdh.utilslibrary.rx;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class RXTest {
    private static final String TAG = "RXTest";
    private static Disposable mDisposable;

//                .doOnNext(new Consumer<Long>() {
//        @Override
//        public void accept(@NonNull Long aLong) throws Exception {
//            Log.e(TAG, "accept: doOnNext : "+aLong );
//        }
//    })
    public static void tt(){
        mDisposable = Flowable.interval(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        Log.e(TAG, "accept: 设置文本 ："+aLong );
                    }
                });
    }
}
