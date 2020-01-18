package com.wangdh.utilslibrary.dblibrary.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class AppVersionMsg {
    @Id
    Long mid;
    private String versionName;
    private int versionCode=0;
    private String httpUrl;
    private String fileName;
    private String remake;//备注说明
    
    private String localUrl;
    private long fileSize;//文件大小
    private int dowloadState;//下载状态 0:未下载，1：下载中 2：下载失败，3下载成功

    @Generated(hash = 658116483)
    public AppVersionMsg(Long mid, String versionName, int versionCode,
            String httpUrl, String fileName, String remake, String localUrl,
            long fileSize, int dowloadState) {
        this.mid = mid;
        this.versionName = versionName;
        this.versionCode = versionCode;
        this.httpUrl = httpUrl;
        this.fileName = fileName;
        this.remake = remake;
        this.localUrl = localUrl;
        this.fileSize = fileSize;
        this.dowloadState = dowloadState;
    }

    @Generated(hash = 885924338)
    public AppVersionMsg() {
    }

    public Long getMid() {
        return this.mid;
    }

    public void setMid(Long mid) {
        this.mid = mid;
    }

    public String getVersionName() {
        return this.versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getVersionCode() {
        return this.versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getHttpUrl() {
        return this.httpUrl;
    }

    public void setHttpUrl(String httpUrl) {
        this.httpUrl = httpUrl;
    }

    public String getLocalUrl() {
        return this.localUrl;
    }

    public void setLocalUrl(String localUrl) {
        this.localUrl = localUrl;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getRemake() {
        return this.remake;
    }

    public void setRemake(String remake) {
        this.remake = remake;
    }

    public long getFileSize() {
        return this.fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public int getDowloadState() {
        return this.dowloadState;
    }

    public void setDowloadState(int dowloadState) {
        this.dowloadState = dowloadState;
    }

    @Override
    public String toString() {
        return "AppVersionMsg{" +
                "mid=" + mid +
                ", versionName='" + versionName + '\'' +
                ", versionCode=" + versionCode +
                ", httpUrl='" + httpUrl + '\'' +
                ", fileName='" + fileName + '\'' +
                ", remake='" + remake + '\'' +
                ", localUrl='" + localUrl + '\'' +
                ", fileSize=" + fileSize +
                ", dowloadState=" + dowloadState +
                '}';
    }
}
