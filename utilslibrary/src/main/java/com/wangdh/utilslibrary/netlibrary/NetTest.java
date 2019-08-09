package com.wangdh.utilslibrary.netlibrary;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;


import com.wangdh.utilslibrary.exception.AppException;
import com.wangdh.utilslibrary.netlibrary.clien.OnlineConfig;
import com.wangdh.utilslibrary.netlibrary.clien.OnlineContext;
import com.wangdh.utilslibrary.netlibrary.clien.OnlineListener;
import com.wangdh.utilslibrary.netlibrary.server.HttpResponse;
import com.wangdh.utilslibrary.netlibrary.server.XH_RXOnline;
import com.wangdh.utilslibrary.netlibrary.server.weather.API_Weather;
import com.wangdh.utilslibrary.netlibrary.server.weather.ResultBean;
import com.wangdh.utilslibrary.netlibrary.server.xiaohua.API_Xiaohua;
import com.wangdh.utilslibrary.netlibrary.server.xiaohua.XiaoHuaContent;
import com.wangdh.utilslibrary.netlibrary.server.xiaohua.XiaohuaBody;
import com.wangdh.utilslibrary.ui.dialog.DialogTip;
import com.wangdh.utilslibrary.utils.logger.TLog;


/**
 * @author wangdh
 * @time 2019/5/16 14:00
 * @describe
 */
public class NetTest {

    public static OnlineConfig getConfig() {
        OnlineConfig onlineConfig = OnlineConfig.getDefConfig();
        onlineConfig.url = API_Xiaohua.url;
        onlineConfig.isShowWait = true;
        return onlineConfig;
    }

    public synchronized static void conn_xiao_hua(Context context) {
        XH_RXOnline online = new XH_RXOnline();
        OnlineConfig config = getConfig();
        online.setOnlineConfig(config);

        //可选项
        config.waitDialog = DialogTip.getLoading(context);

        online.setContext(context);
        API_Xiaohua api = online.getAPI(API_Xiaohua.class);

        online.connectFuc(api.xhList(), new OnlineListener<HttpResponse<XiaohuaBody>>() {
            @Override
            public void succeed(HttpResponse<XiaohuaBody> xiaohuaBodyHttpResponse, OnlineContext context) {
                int size = xiaohuaBodyHttpResponse.getResult().getList().size();
                TLog.e("收到的笑话集合大小为：" + size);
                for (XiaoHuaContent body : xiaohuaBodyHttpResponse.getResult().getList()) {
                }
//                    TLog.e("收到的笑话集合为：" + body.getContent());
            }

            @Override
            public void fail(String errorCode, AppException exception) {

            }
        });
    }

    public static void conn_weather(Context context) {
        XH_RXOnline online = new XH_RXOnline();
        online.setContext(context);
        online.setCloseEvent(Lifecycle.Event.ON_DESTROY);
        API_Weather api = online.getAPI(API_Weather.class);
        online.connectFuc(api.requestWeather("上海", "0fbb2ebffb60b3952bdf49fe72ce5838"), new OnlineListener<HttpResponse<ResultBean>>() {
            @Override
            public void succeed(HttpResponse<ResultBean> resultBeanHttpResponse, OnlineContext context) {
                String weatherInfo = resultBeanHttpResponse.getResult().toString();
                TLog.e("当前天气为：" + weatherInfo);
            }

            @Override
            public void fail(String errorCode, AppException exception) {

            }
        });
    }

}
