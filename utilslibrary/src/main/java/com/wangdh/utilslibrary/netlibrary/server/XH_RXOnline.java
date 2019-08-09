package com.wangdh.utilslibrary.netlibrary.server;


import com.wangdh.utilslibrary.exception.AppException;
import com.wangdh.utilslibrary.netlibrary.clien.OnlineConfig;
import com.wangdh.utilslibrary.netlibrary.clien.OnlineListener;
import com.wangdh.utilslibrary.netlibrary.clien.OnlineObserver;
import com.wangdh.utilslibrary.netlibrary.clien.StandardRXOnline;
import com.wangdh.utilslibrary.utils.logger.TLog;

import io.reactivex.Observable;

/**
 * @author wangdh
 * @time 2019/5/15 16:13
 * @describe
 * 1.本类 主要是对 返回数据进行校验后传递给回调函数、
 *
 */
public class XH_RXOnline extends StandardRXOnline {
    @Override
    public void initOnlineConfig() {
        super.initOnlineConfig();
        onlineConfig.url = "http://v.juhe.cn/";
    }

    public <T> void connectFuc(Observable<? extends BaseResponse<T>> obs, OnlineListener<BaseResponse<T>> listener) {
        OnlineObserver<BaseResponse<T>> disposableObserver = new OnlineObserver<BaseResponse<T>>() {
            @Override
            protected void onStart() {
                super.onStart();
                TLog.e("开始：" + Thread.currentThread().getName());
            }

            @Override
            public void onNext(BaseResponse<T> bean) {
//                TLog.e("返回：" + bean.toString());
                TLog.e("返回：" + Thread.currentThread().getName());
                try {
                    if (this.listener != null) {
                        check(bean);
                        listener.succeed(bean, this.context);
                    }
                } catch (AppException e) {

                }
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                TLog.e("错误" + e.getMessage());
                TLog.e("错误：" + Thread.currentThread().getName());
                if (this.listener != null) {
                    AppException appException = new AppException(e);
                    listener.fail("def", appException);
                }
            }

            @Override
            public void onComplete() {
                TLog.e("停止：" + Thread.currentThread().getName());
                TLog.e("停止");
            }

        };
        disposableObserver.setContext(null);
        disposableObserver.setListener(listener);

        this.connect(obs, disposableObserver);
    }

    /**
     * 校验
     *
     * @param bean
     * @throws AppException
     */
    public void check(BaseResponse bean) throws AppException {
        if (bean == null) {
            throw new AppException("1", "数据不完整");
        }

        if (bean.getError_code() != 0) {
            String reason = bean.getReason();
            throw new AppException("1", reason);
        }
        //......
    }
}
