package com.wangdh.utilslibrary.dblibrary.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author wangdh
 * @time 2019/8/5 15:46
 * @describe 日志记录
 */
@Entity
public class LogText {
    @Id
    Long id;
    public String time;

    public String name;

    public String text;

    @Generated(hash = 739250935)
    public LogText(Long id, String time, String name, String text) {
        this.id = id;
        this.time = time;
        this.name = name;
        this.text = text;
    }

    @Generated(hash = 493219882)
    public LogText() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "LogText{" +
                "id=" + id +
                ", time='" + time + '\'' +
                ", name='" + name + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
