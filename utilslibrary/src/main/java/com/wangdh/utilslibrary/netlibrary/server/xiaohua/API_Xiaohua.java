package com.wangdh.utilslibrary.netlibrary.server.xiaohua;

import com.wangdh.utilslibrary.netlibrary.server.BaseResponse;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author wangdh
 * @time 2019/5/13 17:28
 * @describe https://www.jianshu.com/p/15edb4de0f62
 */
public interface API_Xiaohua {
    String url="http://v.juhe.cn/";
    //请求地址：http://v.juhe.cn/joke/content/list.php
    //请求参数：sort=desc&page=1&pagesize=3&time=1418816972&key=4bccc3f1ee021fd12621dfffb8ddcfcf
    //请求方式：GET
    @GET("joke/content/list.php/")
    Call<BaseResponse> xhList(@Query("sort") String sort, @Query("page") int page, @Query("pagesize") int pagesize, @Query("key") String key, @Query("time") String time);

    //@Headers("apikey:b86c2269fe6588bbe3b41924bb2f2da2")
    @GET("joke/content/list.php?sort=asc&page=1&pagesize=5&time=1418816972&key=4bccc3f1ee021fd12621dfffb8ddcfcf")
    Observable<BaseResponse<XiaohuaBody>> xhList();


    @GET("joke/content/list.php?sort=asc&page=1&pagesize=5&time=1418816972&key=4bccc3f1ee021fd12621dfffb8ddcfcf")
    Call<BaseResponse>  xhList2();

    //http://v.juhe.cn/joke/content/list.php?sort=asc&page=1&pagesize=5&time=1418816972&key=4bccc3f1ee021fd12621dfffb8ddcfcf

    //http://apis.juhe.cn/joke/content/list.php?sort=asc&page=1&pagesize=5&time=1418816972&key=4bccc3f1ee021fd12621dfffb8ddcfcf
}
