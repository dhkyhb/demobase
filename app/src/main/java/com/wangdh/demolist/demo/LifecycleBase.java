package com.wangdh.demolist.demo;

import com.wangdh.utilslibrary.exception.AppErrorCode;
import com.wangdh.utilslibrary.exception.AppException;
import com.wangdh.utilslibrary.utils.TLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangdh on 2017/4/17.
 * name：
 * 描述：任何想拥有生命周期的 类 都继承 此类
 * LifecycleMBeanBase 对生命周期进行管理
 */

public abstract class LifecycleBase implements Lifecycle {
    protected String TAG = this.getClass().getSimpleName();

    private LifecycleSupport lifecycle;
    private volatile LifecycleState state;
    private List<Lifecycle> subLifecycle;

    public LifecycleBase() {
        this.lifecycle = new LifecycleSupport(this);
        this.subLifecycle = new ArrayList<>();
        this.state = LifecycleState.NEW;

        addLifecycleListener(new LifecycleListener() {
            @Override
            public void lifecycleEvent(LifecycleEvent paramLifecycleEvent) {
//                TLog.e(">>>>>>>>>>>>>");
//                TLog.e("监听到了" + paramLifecycleEvent.getType());
//                TLog.e("监听到了" + paramLifecycleEvent.getLifecycle().getState());
//                TLog.e("<<<<<<<<<<<<<");

                for (Lifecycle lif : subLifecycle) {
                    //目前就处理销毁
                    if (paramLifecycleEvent.getLifecycle().getState().equals(LifecycleState.DESTROYING)) {
                        try {
                            lif.destroy();
                        } catch (AppException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        });
    }

    public void addSubLifecycle(Lifecycle _Lifecycle) {
        this.subLifecycle.add(_Lifecycle);
    }

    public void removeSubLifecycle(Lifecycle _Lifecycle) {
        this.subLifecycle.remove(_Lifecycle);
    }

    @Override
    public void addLifecycleListener(LifecycleListener listener) {
        this.lifecycle.addLifecycleListener(listener);
    }

    @Override
    public LifecycleListener[] findLifecycleListeners() {
        return this.lifecycle.findLifecycleListeners();
    }

    @Override
    public void removeLifecycleListener(LifecycleListener listener) {
        this.lifecycle.removeLifecycleListener(listener);
    }

    protected void fireLifecycleEvent(String type, Object data) {
        this.lifecycle.fireLifecycleEvent(type, data);
    }

    @Override
    public final synchronized void init() throws AppException {
        if (!(this.state.equals(LifecycleState.NEW))) {
            throw new AppException(AppErrorCode.LIFECYCLE_STATE);
        }
        try {
            setStateInternal(LifecycleState.INITIALIZING, null, false);
            initInternal();
            setStateInternal(LifecycleState.INITIALIZED, null, false);
        } catch (AppException e) {
            setStateInternal(LifecycleState.FAILED, null, false);
            TLog.e(this.getClass().getSimpleName() + "init初始化错误");
            throw e;
        } catch (Exception e) {
            setStateInternal(LifecycleState.FAILED, null, false);
            e.printStackTrace();
            TLog.e("init初始化错误");
            throw new AppException("");
        }
    }

    protected abstract void initInternal() throws AppException;

    @Override
    public final synchronized void start() throws AppException {
        if (this.state.equals(LifecycleState.NEW)) {
            init();
        }
        try {
            setStateInternal(LifecycleState.STARTING_PREP, null, false);
            startInternal();
            setStateInternal(LifecycleState.STARTED, null, false);

        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            setStateInternal(LifecycleState.FAILED, null, false);
            e.printStackTrace();
            throw new AppException("");
        }

    }

    protected abstract void startInternal() throws AppException;

    @Override
    public final synchronized void stop() throws AppException {
        try {
            setStateInternal(LifecycleState.STOPPING_PREP, null, false);
            setStateInternal(LifecycleState.STOPPING, null, false);
            stopInternal();
            setStateInternal(LifecycleState.STOPPED, null, false);
        } catch (Throwable t) {
            setStateInternal(LifecycleState.FAILED, null, false);
            throw new AppException("lifecycleBase.initFail" + t.getMessage());
        }
    }

    protected abstract void stopInternal() throws AppException;

    @Override
    public final synchronized void destroy() throws AppException {
        TLog.e(this.getClass().getName());
        TLog.e("destroy " + this.state);
        if (LifecycleState.FAILED.equals(this.state)) {
            try {
                stop();
            } catch (AppException e) {
                TLog.e("lifecycleBase.destroyStopFail " + e.getErrorMsg());
            }
        }

        if ((LifecycleState.DESTROYING.equals(this.state)) || (LifecycleState.DESTROYED.equals(this.state))) {
            TLog.e(new AppException("lifecycleBase.已经摧毁了").getErrorMsg());
            return;
        }

        try {
            setStateInternal(LifecycleState.DESTROYING, null, false);
            destroyInternal();
            setStateInternal(LifecycleState.DESTROYED, null, false);
        } catch (Throwable t) {
            setStateInternal(LifecycleState.FAILED, null, false);
            throw new AppException("lifecycleBase.destroyFail " + t.getMessage());
        }
    }

    protected abstract void destroyInternal() throws AppException;

    @Override
    public LifecycleState getState() {
        return this.state;
    }

    @Override
    public String getStateName() {
        return getState().toString();
    }

    protected synchronized void setState(LifecycleState state) throws AppException {
        setStateInternal(state, null, true);
    }

    protected synchronized void setState(LifecycleState state, Object data) throws AppException {
        setStateInternal(state, data, true);
    }

    private synchronized void setStateInternal(LifecycleState state, Object data, boolean check)
            throws AppException {
        if (check) {
            if (state == null) {
                invalidTransition("null");
                return;
            }

            if ((state != LifecycleState.FAILED)
                    && (((this.state != LifecycleState.STARTING_PREP) || (state != LifecycleState.STARTING)))
                    && (((this.state != LifecycleState.STOPPING_PREP) || (state != LifecycleState.STOPPING)))
                    && (((this.state != LifecycleState.FAILED) || (state != LifecycleState.STOPPING)))) {
                invalidTransition(state.name());
            }
        }

        this.state = state;
        String lifecycleEvent = state.getLifecycleEvent();
        if (lifecycleEvent != null) {
            fireLifecycleEvent(lifecycleEvent, data);
        }
    }

    private void invalidTransition(String type) throws AppException {
        String msg = "lifecycleBase.invalidTransition" + ":" + type + "Now: " + this.state;
        throw new AppException(msg);
    }
}