package com.wangdh.demolist.base.mvp;

/**
 * Created by wangdh on 2016/11/14.
 * name：
 * 描述：相关rxandroid的一个base presenter
 */

public class BaseRxPresenter<V> {
    public V model;
//    protected ApiStores apiStores;
//    private CompositeSubscription mCompositeSubscription;

    public void attachView(V mvpView) {
        this.model = mvpView;
//        apiStores = AppClient.retrofit().create(ApiStores.class);
    }


    public void detachView() {
        this.model = null;
        onUnsubscribe();
    }


    //RXjava取消注册，以避免内存泄露
    public void onUnsubscribe() {
//        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
//            mCompositeSubscription.unsubscribe();
//        }
    }


//    public void addSubscription(Observable observable, Subscriber subscriber) {
//        if (mCompositeSubscription == null) {
//            mCompositeSubscription = new CompositeSubscription();
//        }
//        mCompositeSubscription.add(observable
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(subscriber));
//    }
}
