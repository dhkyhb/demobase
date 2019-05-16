package com.wangdh.netlibrary.server.xiaohua;

import java.util.List;

/**
 * @author wangdh
 * @time 2019/5/13 17:30
 * @describe
 */
public class XiaohuaBody {
    private List<XiaoHuaContent> data;

    public List<XiaoHuaContent> getList() {
        return data;
    }

    public void setList(List<XiaoHuaContent> list) {
        this.data = list;
    }

    @Override
    public String toString() {
        return "XiaohuaBody{" +
                "list=" + data +
                '}';
    }
}
