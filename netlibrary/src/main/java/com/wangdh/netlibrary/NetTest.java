package com.wangdh.netlibrary;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.wangdh.netlibrary.clien.OnlineConfig;
import com.wangdh.netlibrary.clien.OnlineContext;
import com.wangdh.netlibrary.clien.OnlineListener;
import com.wangdh.netlibrary.server.BaseResponse;
import com.wangdh.netlibrary.server.XH_RXOnline;
import com.wangdh.netlibrary.server.weather.API_Weather;
import com.wangdh.netlibrary.server.weather.ResultBean;
import com.wangdh.netlibrary.server.xiaohua.API_Xiaohua;
import com.wangdh.netlibrary.server.xiaohua.XiaoHuaContent;
import com.wangdh.netlibrary.server.xiaohua.XiaohuaBody;
import com.wangdh.utilslibrary.exception.AppException;
import com.wangdh.utilslibrary.utils.logger.TLog;

import static com.wangdh.netlibrary.server.weather.API_Weather.WEATHER_URL;

/**
 * @author wangdh
 * @time 2019/5/16 14:00
 * @describe
 */
public class NetTest {
    public static void conn_xiao_hua(Context context) {
        XH_RXOnline online = new XH_RXOnline();
        online.setContext(context);
        online.setCloseEvent(Lifecycle.Event.ON_DESTROY);
        API_Xiaohua api = online.getAPI(API_Xiaohua.class);
        online.connectFuc(api.xhList(), new OnlineListener<BaseResponse<XiaohuaBody>>() {
            @Override
            public void succeed(BaseResponse<XiaohuaBody> xiaohuaBodyBaseResponse, OnlineContext context) {
                int size = xiaohuaBodyBaseResponse.getResult().getList().size();
                TLog.e("收到的笑话集合大小为：" + size);
                for (XiaoHuaContent body : xiaohuaBodyBaseResponse.getResult().getList())
                    TLog.e("收到的笑话集合为：" + body.getContent());
            }

            @Override
            public void fail(String errorCode, AppException exception) {

            }
        });
    }

    public static void conn_weather(Context context) {
        XH_RXOnline online = new XH_RXOnline();
        online.setContext(context);
        online.setUrl(WEATHER_URL);
        online.setCloseEvent(Lifecycle.Event.ON_DESTROY);
        API_Weather api = online.getAPI(API_Weather.class);
        online.connectFuc(api.requestWeather("上海", "0fbb2ebffb60b3952bdf49fe72ce5838"), new OnlineListener<BaseResponse<ResultBean>>() {
            @Override
            public void succeed(BaseResponse<ResultBean> resultBeanBaseResponse, OnlineContext context) {
                String weatherInfo = resultBeanBaseResponse.getResult().toString();
                TLog.e("当前天气为：" + weatherInfo);
            }

            @Override
            public void fail(String errorCode, AppException exception) {

            }
        });
    }

}
