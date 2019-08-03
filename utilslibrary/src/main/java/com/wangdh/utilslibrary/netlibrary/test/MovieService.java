package com.wangdh.utilslibrary.netlibrary.test;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author wangdh
 * @time 2019/2/20 16:04
 * @describe
 */
public interface MovieService {

    //获取豆瓣Top250 榜单
    @GET("top250")
    Call<MovieSubject> getTop250(@Query("start") int start, @Query("count") int count);

    @GET("top250")
    Observable<MovieSubject> getTop2502(@Query("start") int start, @Query("count") int count);

    @GET("test/2")
    Observable<MovieSubject> test();
}
