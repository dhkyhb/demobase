package com.wangdh.netlibrary.test;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.parkingwang.okhttp3.LogInterceptor.LogInterceptor;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.wangdh.utilslibrary.utils.logger.TLog;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author wangdh
 * @time 2019/2/20 16:05
 * @describe
 */
public class NetTest {
    public static final String BASE_URL = "https://api.douban.com/v2/movie/";

    public static void _1() {
        TLog.e("NetTest:");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //获取接口实例
        MovieService movieService = retrofit.create(MovieService.class);
        //调用方法得到一个Call
        Call<MovieSubject> call = movieService.getTop250(0, 20);
        //进行网络请求
        call.enqueue(new Callback<MovieSubject>() {
            @Override
            public void onResponse(Call<MovieSubject> call, Response<MovieSubject> response) {
                List<MovieSubject.SubjectsBean> subjects = response.body().getSubjects();
                for (MovieSubject.SubjectsBean subject : subjects) {
                    TLog.e(subject.toString());
                }
            }

            @Override
            public void onFailure(Call<MovieSubject> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public static void _2(LifecycleProvider lifecycleProvider) {
        TLog.e("NetTest2:");
        OkHttpClient okHttpClient = new OkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        //获取接口实例
        MovieService movieService = retrofit.create(MovieService.class);

        DisposableObserver<MovieSubject> disposableObserver = new DisposableObserver<MovieSubject>() {
            @Override
            public void onNext(MovieSubject movieSubject) {
                List<MovieSubject.SubjectsBean> subjects = movieSubject.getSubjects();
                for (MovieSubject.SubjectsBean subject : subjects) {
                    TLog.e(subject.toString());
                }
            }

            @Override
            public void onError(Throwable e) {
                TLog.e("错误" + e.getMessage());
            }

            @Override
            public void onComplete() {
                TLog.e("停止");
            }
        };
        //调用方法得到一个Call
        Observable<MovieSubject> top250 = movieService.getTop2502(0, 20);
        top250.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(lifecycleProvider.bindUntilEvent(ActivityEvent.PAUSE))
                .subscribe(disposableObserver);
    }

    public static void _3(LifecycleProvider lifecycleProvider) {
        TLog.e("NetTest2:");
        OkHttpClient okHttpClient = new OkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("http://192.168.191.1:8080/")
                .build();
        //获取接口实例
        MovieService movieService = retrofit.create(MovieService.class);

        DisposableObserver<MovieSubject> disposableObserver = new DisposableObserver<MovieSubject>() {
            @Override
            public void onNext(MovieSubject movieSubject) {
                List<MovieSubject.SubjectsBean> subjects = movieSubject.getSubjects();
                for (MovieSubject.SubjectsBean subject : subjects) {
                    TLog.e(subject.toString());
                }
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                TLog.e("错误" + e.getMessage());
            }

            @Override
            public void onComplete() {
                TLog.e("停止");
            }
        };
        //调用方法得到一个Call
        Observable<MovieSubject> top250 = movieService.test();
        Observable<MovieSubject> movieSubjectObservable = top250.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        if (lifecycleProvider != null) {
            movieSubjectObservable = movieSubjectObservable.compose(lifecycleProvider.bindUntilEvent(ActivityEvent.PAUSE));
        }
        movieSubjectObservable.subscribe(disposableObserver);
    }
//    http://localhost:8888/test/1
//    http://192.168.56.1:8888/test/1
//    http://192.168.222.2:8888/test/1
//    http://192.168.2.115:8888/test/1
//    http://127.0.0.1:8888/test/1

    public static void _4() {
        String url = "http://192.168.191.1:8080/test/1";
//        String url = "http://www.baidu.com";
        TLog.e(url);
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(new LogInterceptor()).build();
        final Request request = new Request.Builder()
                .url(url)
                .get()//默认就是GET请求，可以不写
                .build();
        okhttp3.Call call = okHttpClient.newCall(request);
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                TLog.e("错误：" + e.getLocalizedMessage());
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                TLog.e("onResponse：" + response.body().string());
            }
        });
    }
}
