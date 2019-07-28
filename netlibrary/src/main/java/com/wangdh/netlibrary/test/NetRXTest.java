package com.wangdh.netlibrary.test;

import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;

import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.parkingwang.okhttp3.LogInterceptor.LogInterceptor;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.wangdh.netlibrary.clien.OnlineContext;
import com.wangdh.netlibrary.clien.OnlineListener;
import com.wangdh.netlibrary.server.BaseResponse;
import com.wangdh.netlibrary.server.XH_RXOnline;
import com.wangdh.netlibrary.server.xiaohua.API_Xiaohua;
import com.wangdh.netlibrary.server.xiaohua.XiaohuaBody;
import com.wangdh.utilslibrary.exception.AppException;
import com.wangdh.utilslibrary.utils.logger.TLog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
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
public class NetRXTest {
    public static final String BASE_URL = "https://api.douban.com/v2/movie/";
//https://api.douban.com/v2/movie/?start=0&count=20

    public static String getJson() {
        BeanBaseHead<BeanTestBody> http = new BeanBaseHead<>();
        http.setCode("1");
        http.setKey("key");
        http.setSmg("smg");
        http.setVerify("jamisekfpawefwe");

        BeanAddition beanAddition = new BeanAddition();
        beanAddition.setBrand("setBrand");
        beanAddition.setImei("setImei");
        beanAddition.setModel("setModel");
        beanAddition.setRelease("setRelease");
        beanAddition.setSdk_int("setSdk_int");

        http.setAddition(beanAddition);

        BeanTestBody beanTestBody = new BeanTestBody();
        beanTestBody.setName("名字");
        ArrayList<BeanBody2> strings = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            BeanBody2 beanBody2 = new BeanBody2();
            beanBody2.setName("i=" + i);
            beanBody2.setS(i);
            beanBody2.setSex("性别");

            strings.add(beanBody2);
        }
        beanTestBody.setBeanBody2List(strings);
        http.setObj(beanTestBody);

        String s = new Gson().toJson(http);
        BeanBaseHead<BeanTestBody> beanBaseHead = new Gson().fromJson(s, BeanBaseHead.class);
        String name = beanBaseHead.getObj().getName();
        TLog.e(name);
        List<BeanBody2> beanBody2List = beanBaseHead.getObj().getBeanBody2List();
        for (BeanBody2 beanBody2 : beanBody2List) {
            TLog.e(beanBody2.toString());
        }
        return s;
    }

    public static void _1() {
        TLog.e("NetRXTest:");
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.addInterceptor(new LogInterceptor());

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient.build())
                .baseUrl(API_Xiaohua.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //获取接口实例
        API_Xiaohua api = retrofit.create(API_Xiaohua.class);
        //调用方法得到一个Call
        //call; //= api.xhList("desc",1,3,"4bccc3f1ee021fd12621dfffb8ddcfcf","1418816972");
        Call<BaseResponse> call = api.xhList2();
        //进行网络请求
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                int code = response.code();
                TLog.e("code:" + code);
                TLog.e("message:" + response.message());

                TLog.e(response.body().toString());

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public static void _2(LifecycleProvider lifecycleProvider) {
        TLog.e("NetTest2:");
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.addInterceptor(new LogInterceptor());


        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(API_Xiaohua.url)
                .build();
        //获取接口实例
        API_Xiaohua api = retrofit.create(API_Xiaohua.class);


        DisposableObserver<BaseResponse> disposableObserver = new DisposableObserver<BaseResponse>() {
            @Override
            public void onNext(BaseResponse movieSubject) {
                TLog.e(movieSubject.toString());
//                List<MovieSubject.SubjectsBean> subjects = movieSubject.getSubjects();
//                for (MovieSubject.SubjectsBean subject : subjects) {
//                    TLog.e(subject.toString());
//                }
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
//        Observable<XiaohuaRespose> call = api.xhList();
//        //调用方法得到一个Call
//        call.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .compose(lifecycleProvider.bindUntilEvent(ActivityEvent.PAUSE))
//                .subscribe(disposableObserver);
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

    public static <T> T getHttp(final Class<T> service) {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.addInterceptor(new LogInterceptor());

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(API_Xiaohua.url)
                .build();
        return retrofit.create(service);
    }

    public static void test(Context context) {
        XH_RXOnline online = new XH_RXOnline();
        if (context instanceof LifecycleOwner) {
            online.setLifecycleOwner((LifecycleOwner) context);
//            online.setCloseEvent(ActivityEvent.RESUME);
        }
        API_Xiaohua api = online.getAPI(API_Xiaohua.class);
        online.connectFuc(api.xhList(), new OnlineListener() {
            @Override
            public void succeed(Object o, OnlineContext context) {
                BaseResponse<XiaohuaBody> xiaohuaBodyXiaohuaRespose = (BaseResponse<XiaohuaBody>) o;
                int size = xiaohuaBodyXiaohuaRespose.getResult().getList().size();
                TLog.e("收到的笑话集合大小为：" + size);
            }

            @Override
            public void fail(String errorCode, AppException exception) {

            }
        });

        /*DisposableObserver<XiaohuaRespose> disposableObserver = new DisposableObserver<XiaohuaRespose>() {
            @Override
            public void onNext(XiaohuaRespose bean) {
                TLog.e(bean.toString());
//                List<MovieSubject.SubjectsBean> subjects = movieSubject.getSubjects();
//                for (MovieSubject.SubjectsBean subject : subjects) {
//                    TLog.e(subject.toString());
//                }
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

        StandardRXOnline standardRXOnline = new StandardRXOnline();
        API_Xiaohua http = (API_Xiaohua)standardRXOnline.getAPI(API_Xiaohua.class);
        standardRXOnline.connect(http.xhList(),disposableObserver);*/
    }

    public static void http() {
        TLog.e("http:");
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.addInterceptor(new LogInterceptor());

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(API_Xiaohua.url)
                .build();

        //获取接口实例
        API_Xiaohua api = retrofit.create(API_Xiaohua.class);

        DisposableObserver<BaseResponse> disposableObserver = new DisposableObserver<BaseResponse>() {
            @Override
            public void onNext(BaseResponse bean) {
                TLog.e(bean.toString());
//                List<MovieSubject.SubjectsBean> subjects = movieSubject.getSubjects();
//                for (MovieSubject.SubjectsBean subject : subjects) {
//                    TLog.e(subject.toString());
//                }
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

        Observable observable = api.xhList();

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);


        /*//调用方法得到一个Call
        Observable<MovieSubject> top250 = movieService.test();
        Observable<MovieSubject> movieSubjectObservable = top250.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
//        if (lifecycleProvider != null) {
//            movieSubjectObservable = movieSubjectObservable.compose(lifecycleProvider.bindUntilEvent(ActivityEvent.PAUSE));
//        }
        movieSubjectObservable.subscribe(disposableObserver);*/
    }

    public void getList(DisposableObserver observer) {

    }
}
