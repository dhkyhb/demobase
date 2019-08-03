package com.wangdh.utilslibrary.netlibrary.clien;


import com.wangdh.utilslibrary.netlibrary.server.xiaohua.API_Xiaohua;

/**
 * @author wangdh
 * @time 2019/5/15 14:56
 * @describe
 */
public class OnlineConfig {
    //是否显示框架内部日志
    public boolean isLog = true;
    public String url = API_Xiaohua.url;
    public int time_out = 10000;

    public static OnlineConfig getDefConfig() {
        return InternalConfig.config;
    }

    static class InternalConfig{
        private static final OnlineConfig config = new OnlineConfig();
    }
}
