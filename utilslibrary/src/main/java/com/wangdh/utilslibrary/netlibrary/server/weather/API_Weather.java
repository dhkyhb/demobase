package com.wangdh.utilslibrary.netlibrary.server.weather;


import com.wangdh.utilslibrary.netlibrary.server.HttpResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API_Weather {

    String WEATHER_URL = "http://apis.juhe.cn/";

    @GET("simpleWeather/query")
    Observable<HttpResponse<ResultBean>> requestWeather(@Query("city") String city, @Query("key") String key);


}
