package com.wangdh.utilslibrary.netlibrary.clien;

import android.content.Context;

import java.util.Map;

/**
 * @author wangdh
 * @time 2019/5/15 16:41
 * @describe 联机模块的上下文
 */
public class OnlineContext {
    protected Context context;
    private OnlineConfig onlineConfig;

    private Map data;

    protected String url;


    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public OnlineConfig getOnlineConfig() {
        return onlineConfig;
    }

    public void setOnlineConfig(OnlineConfig onlineConfig) {
        this.onlineConfig = onlineConfig;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
