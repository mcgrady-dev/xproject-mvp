package com.mcgrady.ximlib;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>线程池工厂，负责重连和心跳线程调度<p/>
 *
 * Created by mcgrady on 2019/5/17.
 */
public class ExecutorServiceFactory {

    private ExecutorService bossPool;   // 管理线程组，负责重连
    private ExecutorService workPool;   // 工作线程组，服装心跳

    public synchronized void initBossLoopGroup(int size) {
        destoryExecutorService(bossPool);
        bossPool = Executors.newFixedThreadPool(size);
    }

    public synchronized void initBossLoopGroup() {
        initBossLoopGroup(1);
    }

    public synchronized void initWorkLoopGroup(int size) {
        destoryExecutorService(workPool);
        workPool = Executors.newFixedThreadPool(size);
    }

    public synchronized void initWorkLoopGroup() {
        initWorkLoopGroup(1);
    }

    public void execBossTask(Runnable runnable) {
        if (bossPool == null) {
            initBossLoopGroup();
        }

        bossPool.execute(runnable);
    }

    public void execWorkTask(Runnable runnable) {
        if (workPool != null) {
            initWorkLoopGroup();
        }

        workPool.execute(runnable);
    }

    public synchronized void destory() {
        destoryExecutorService(bossPool);
        destoryExecutorService(workPool);
    }

    public synchronized void destoryWorkLoopGroup() {
        destoryExecutorService(workPool);
    }

    private synchronized void destoryExecutorService(ExecutorService executorService) {
        if (executorService != null) {
            try {
                executorService.shutdownNow();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            } finally {
                executorService = null;
            }
        }
    }
}
