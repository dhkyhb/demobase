package com.wangdh.netlibrary;

import android.content.Context;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.wangdh.netlibrary.clien.OnlineContext;
import com.wangdh.netlibrary.clien.OnlineListener;
import com.wangdh.netlibrary.server.XH_RXOnline;
import com.wangdh.netlibrary.server.xiaohua.API_Xiaohua;
import com.wangdh.netlibrary.server.xiaohua.XiaohuaBody;
import com.wangdh.netlibrary.server.xiaohua.XiaohuaRespose;
import com.wangdh.utilslibrary.exception.AppException;
import com.wangdh.utilslibrary.utils.logger.TLog;

/**
 * @author wangdh
 * @time 2019/5/16 14:00
 * @describe
 */
public class NetTest {
    public static  void conn_xiao_hua(Context context){
            XH_RXOnline online = new XH_RXOnline();
//            online.setContext(context);
//            online.setCloseEvent(ActivityEvent.RESUME);
            API_Xiaohua api = online.getAPI(API_Xiaohua.class);
            online.connect(api.xhList(), new OnlineListener<XiaohuaRespose<XiaohuaBody>>() {
                @Override
                public void succeed(XiaohuaRespose<XiaohuaBody> xiaohuaBodyXiaohuaRespose, OnlineContext context) {
                    int size = xiaohuaBodyXiaohuaRespose.getBody().getList().size();
                    TLog.e("收到的笑话集合大小为：" + size);
                }

                @Override
                public void fail(String errorCode, AppException exception) {

                }
            });
    }
}
