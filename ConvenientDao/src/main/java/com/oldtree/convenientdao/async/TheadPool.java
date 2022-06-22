package com.oldtree.convenientdao.async;

import java.util.concurrent.*;

/**
 * 详细介绍类的情况.
 *
 * @ClassName TheadPool
 * @Author oldTree
 * @Date 2022/6/20
 * @Version 1.0
 */
public class TheadPool {
    //任务队列
    private ArrayBlockingQueue<Runnable> arrayBlockingQueue;
    //线程池对象
    private ThreadPoolExecutor threadPoolExecutor;
    //饱和策略
    private RejectedExecutionHandler rejectedExecutionHandler;

    private static TheadPool pool;
    public static TheadPool getInstance() {
        if(null==pool){
            synchronized (TheadPool.class){
                if(null==pool){
                    pool = new TheadPool();
                }
            }
        }
        return pool;
    }

    private TheadPool() {
        arrayBlockingQueue = new ArrayBlockingQueue<Runnable>(3);
        rejectedExecutionHandler = (r,executor)->{
            Thread thread = new Thread(r);
            thread.start();
        };
        threadPoolExecutor = new ThreadPoolExecutor(2,3,1, TimeUnit.MINUTES, arrayBlockingQueue, rejectedExecutionHandler);
    }
    public ThreadPoolExecutor getThreadPoolExecutor() {
        return threadPoolExecutor;
    }

}

