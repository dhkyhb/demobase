package com.wangdh.utilslibrary.exception;

/**
 * @author wangdh
 * @time 2019/5/15 16:43
 * @describe
 */
public class AppException extends Throwable{

    public AppException(String errorCode, String reason){

    }
    public AppException(Throwable e){

    }
    void init(){
        new Exception();
    }
}
