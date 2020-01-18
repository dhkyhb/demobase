package com.wangdh.utilslibrary.utils;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 */
public class ThreadSole {

    /**
     * 1.核心线程 比非核心线程的性能要好
     * 2.提交进来的任务首先会被核心线程处理，如果这个时候还有任务提交，那么就会把提交的任务放到 workQueue中，如果workQueue放满了，就会开启非核心线程去执行，如果非核心线程也满了 就交给拒绝策略去处理
     *
     * @param corePoolSize    核心线程数量
     * @param maximumPoolSize 最大线程数量； （核心线程数量+非核心线程数量）
     * @param keepAliveTime   非核心线程的超时时长，当系统中非核心线程闲置时间超过keepAliveTime之后，则会被回收。如果ThreadPoolExecutor的allowCoreThreadTimeOut属性设置为true，则该参数也表示核心线程的超时时长。
     * @param unit            单位 秒
     * @param workQueue       该队列主要用来存储已经被提交但是尚未执行的任务。ArrayBlockingQueue由于其底层基于数组，并且在创建时指定存储的大小，在完成后就会立即在内存分配固定大小容量的数组元素，因此其存储通常有限，故其是一个“有界“的阻塞队列；而LinkedBlockingQueue可以由用户指定最大存储容量，也可以无需指定，如果不指定则最大存储容量将是Integer.MAX_VALUE，即可以看作是一个“无界”的阻塞队列，由于其节点的创建都是动态创建，并且在节点出队列后可以被GC所回收，因此其具有灵活的伸缩性。但是由于ArrayBlockingQueue的有界性，因此其能够更好的对于性能进行预测，而LinkedBlockingQueue由于没有限制大小，当任务非常多的时候，不停地向队列中存储，就有可能导致内存溢出的情况发生。
     * @param handler         1.RejectedExecutionHandler拒绝策略 拒绝行为 2.AbortPolicy  抛出RejectedExecutionException 3.DiscardPolicy什么也不做，直接忽略4.DiscardOldestPolicy  丢弃执行队列中最老的任务，尝试为当前提交的任务腾出位置5.CallerRunsPolicy 直接由提交任务者执行这个任务
     */
    public static ThreadPoolExecutor createThread(int corePoolSize,
                                                  int maximumPoolSize,
                                                  long keepAliveTime,
                                                  TimeUnit unit,
                                                  BlockingQueue<Runnable> workQueue,
                                                  RejectedExecutionHandler handler) {
        ThreadPoolExecutor thread = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
        return thread;
    }

    private ThreadPoolExecutor thread;

    ThreadSole() {

        thread = createThread(1, 1, 5, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10), new ThreadPoolExecutor.DiscardPolicy());
    }

    /**
     * 获得执行的线程数量，不包含等待队列中的线程
     *
     * @return
     */
    public int getActiveCount() {
        System.out.println("activeCount:" + thread.getActiveCount());
        return thread.getActiveCount();
    }

    public void get() {
//        thread.submit();
    }

}
